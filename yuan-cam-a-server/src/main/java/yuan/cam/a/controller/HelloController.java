package yuan.cam.a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yuan.cam.a.dto.HelloDTO;
import yuan.cam.a.export.GetHello;
import yuan.cam.a.service.HelloService;
import yuan.cam.a.util.RabitProducer;

@RequestMapping("/hello")
@RestController
public class HelloController implements GetHello{

    @Value("${test}")
    private String test;

    @Override
    public String sayHelloApollo(){
        return "Apollo:" + test;
    }

    @Autowired
    private HelloService helloService;

    @Override
    public String sayHelloRedis(@RequestBody @Validated HelloDTO.HelloRedisDTO reqDTO){
        return helloService.sayHelloRedis(reqDTO.getName());
    }

    @Override
    public String sayHello(){
        //调用b服务方法一
//        helloService.hello();
        //调用b服务方法二
//        restTemplate.getForEntity("http://yuan-cam-b/hello/hello",String.class).getBody();
        //调用b服务方法三
        helloService.hello();
        return "Hello!!!!";
    }

    @Autowired
    private RabbitProducer rabbitProducer;

    @Override
    public void HelloA(){
        rabbitProducer.rabbitFanoutExchange("aaaaaaa");
    }
}
