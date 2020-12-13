package yuan.cam.a.util;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class RedisUtil {

    //redis获取链接的并发锁
    private static ReentrantLock redisPollLock= new ReentrantLock();
    //连接redis实例的ip
    private static String REDIS_ADDRESS;
    //连接redis实例的端口
    private static int PORT;
    //多线程环境中,连接实例的最大数,如果设为-1则无上线,建议设置,否则有可能导致资源耗尽
    private static int MAX_ACTIVE;
    //在多线程环境中,连接池中最大空闲连接数,单线程环境没有实际意义
    private static int MAX_OLDE;
    //在多线程环境中,连接池中最小空闲连接数
    private static int MIN_OLDE;
    //多长时间将空闲线程进行回收,单位毫秒
    private static int METM;
    //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
    private static int SMETM;
    //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1,只有运行了此线程,MIN_OLDE METM/SMETM才会起作用
    private static int TBERM;
    //当连接池中连接不够用时,等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT;
    //超时时间,单位毫秒
    private static int TIME_OUT;
    //在借用一个jedis连接实例时，是否提前进行有效性确认操作；如果为true，则得到的jedis实例均是可用的；  
    private static boolean TEST_ON_BORROW;
    //连接池实例
    private static JedisPool jedisPool = null;

    @Value("${redis.address}")
    private String address;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.maxActive}")
    private int maxActive;

    @Value("${redis.maxOlde}")
    private int maxOlde;

    @Value("${redis.minOlde}")
    private int minOlde;

    @Value("${redis.metm}")
    private int metm;

    @Value("${redis.smetm}")
    private int smetm;

    @Value("${redis.tberm}")
    private int tberm;

    @Value("${redis.maxWait}")
    private int maxWait;

    @Value("${redis.timeOut}")
    private int timeOut;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @PostConstruct
    public void init(){
        REDIS_ADDRESS = address;
        PORT = port;
        MAX_ACTIVE = maxActive;
        MAX_OLDE = maxOlde;
        MIN_OLDE = minOlde;
        METM = metm;
        SMETM = smetm;
        TBERM = tberm;
        MAX_WAIT = maxWait;
        TIME_OUT = timeOut;
        TEST_ON_BORROW = testOnBorrow;
    }

    //初始化连接池,有好多重载的构造函数,根据自己业务实际需要来实例化JedisPoll
    private static void initPoll(String qid) {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_OLDE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            config.setMinIdle(MIN_OLDE);
            config.setSoftMinEvictableIdleTimeMillis(SMETM);
            config.setTimeBetweenEvictionRunsMillis(TBERM);
            jedisPool = new JedisPool(config, REDIS_ADDRESS, PORT, TIME_OUT);
        } catch (Exception e) {
            LogUtil.error("initial JedisPoll fail:" + Throwables.getStackTraceAsString(e), qid);
        }
        
    }

    //获取jedis连接实例
    public static Jedis getJedis(String qid) {
        Jedis jedis = null;
        redisPollLock.lock();
        try {
            if(jedisPool == null) {
                initPoll(qid);
            }
            if(jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            LogUtil.error("get jedis fail:" + Throwables.getStackTraceAsString(e), qid);
        }finally {
            redisPollLock.unlock();
        }
        return jedis;
    }

    //如果每次获取了jedis连接后不进行归还,redis不会自动回收,那么获取的最多连接数量为MAX_ACTIVE
    //超出数量则会抛出异常redis.clients.jedis.exceptions.JedisException: Could not get a resource from the pool
    public static void returnSource(Jedis jedis) {
        if(jedis != null) {
            //如果连接池已经关闭了,则返回-1,最大活跃数不会超过MAX_ACTIVE,最大空闲数不会超过MAX_OLDE
            System.out.println(jedisPool.getNumWaiters()+"链接归还前活跃数:"+jedisPool.getNumActive()+"空闲连接数:"+jedisPool.getNumIdle());
            jedis.close();
            System.out.println(jedisPool.getNumWaiters()+"链接归还后活跃数:"+jedisPool.getNumActive()+"空闲连接数:"+jedisPool.getNumIdle());
        }
    }

    //在运用正常运行时,通常是不会手动调用jedisPool.close();池内将保持最大空闲数的连接,如果设置了逐出策略
    //那么池内就会保留最小空闲连接,如果应用突然关闭,我们需要在bean销毁时将连接池销毁.
    public static void destroy(String qid){
        if(jedisPool != null) {
            try {
                jedisPool.destroy();    
            } catch (Exception e) {
                LogUtil.error("jedisPool destroy fail " + e, qid);
            }
        }    
    }
}
