package yuan.cam.a.util;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Component;
import yuan.cam.a.ContentConst;

@Component
public class RabbitProducer {

    public void rabbitFanoutExchange(String message) {
        Connection connection = null;
        Channel channel = null;
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();

            //exchange的类型包括:direct, topic, headers and fanout,我们本例子主要关注的是fanout
            //fanout类型是指向所有的队列发送消息
            //以下是创建一个fanout类型的exchange,取名logs
            channel.exchangeDeclare(ContentConst.Exchange_MQ, BuiltinExchangeType.FANOUT);

            //1.在上个"hello world"例子中,我们用的是channel.basicPublish("", "hello", null, message.getBytes());
            //这里用了默认的exchanges,一个空字符串 "",在basicPublish这个方法中,第一个参数即是exchange的名称
            //2.准备向我们命名的exchange发送消息啦
            channel.basicPublish(ContentConst.Exchange_MQ, "", null, message.getBytes());
        }catch (Exception e){
            System.out.println(e);
        }finally {
            try{
                if(channel != null){
                    channel.close();
                }
                if(connection != null){
                    connection.close();
                }
            }catch (Exception e){

            }
        }
    }
}
