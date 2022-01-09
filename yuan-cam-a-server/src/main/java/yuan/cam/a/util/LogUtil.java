package yuan.cam.a.util;

import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import yuan.cam.a.commons.Constants;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class LogUtil {

    /**
     * 监听关键字，当配置中心的依次开头的配置发生变化时，日志级别刷新
     */
    @Value("${log.tag}")
    private String LOGGER_TAG;

    @Resource
    private LoggingSystem loggingSystem;

    /**
     * 可以指定具体的namespace，未指定时使用的是 application这个namespace
     */
    @ApolloConfig
    private Config config;

    @ApolloConfigChangeListener
    private void onChange(ConfigChangeEvent changeEvent) {
        refreshLoggingLevels();
    }

    @PostConstruct
    private void refreshLoggingLevels() {
        String qid = UUID.randomUUID().toString();
        Set<String> keyNames = config.getPropertyNames();
        for (String key : keyNames) {
            if (key.equals(LOGGER_TAG)) {
                String strLevel = config.getProperty(key, "info");
                LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
                //重置日志级别，马上生效
                loggingSystem.setLogLevel("", level);
                info(key + ":" + strLevel, qid);
            }
        }
    }

    public static void error(String txt, String qid) {
        log.error(txt);
        addLog("ERROR", txt, qid);
    }

    public static void warn(String txt, String qid) {
        log.warn(txt);
        addLog("WARN", txt, qid);
    }

    public static void info(String txt, String qid) {
        log.info(txt);
        addLog("INFO", txt, qid);
    }

    public static void debug(String txt, String qid) {
        log.debug(txt);
        addLog("DEBUG", txt, qid);
    }

    public static void addLog(String lv, String txt, String qid) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("proj", Constants.SERVICE_NAME);
            jsonObject.put("lv", lv);
            jsonObject.put("txt", txt);
            jsonObject.put("qid", qid);
            jsonObject.put("createTime", (int) (System.currentTimeMillis() / 1000));
            IndexRequest indexRequest = new IndexRequest(Constants.ES_LOG);
            indexRequest.source(jsonObject, XContentType.JSON);
            EsUtil.getESClient().index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        }
    }
}
