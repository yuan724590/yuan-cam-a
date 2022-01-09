package yuan.cam.a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yuan.cam.a.dto.HelloDTO;
import yuan.cam.a.export.GetHello;
import yuan.cam.a.service.HelloFeignService;
import yuan.cam.a.service.HelloService;
import yuan.cam.a.util.RabbitProducer;


@RequestMapping
@RestController
public class HelloController implements GetHello {

    @Autowired
    private HelloService helloService;

    @Autowired
    private HelloFeignService helloFeignService;

    @Value("${test}")
    private String test;

    @Override
    public String sayHelloApollo() {
        return "Apollo:" + test;
    }

    @Override
    public String sayHelloRedis(@RequestBody @Validated HelloDTO.HelloRedisDTO reqDTO) {
        return helloService.sayHelloRedis(reqDTO.getName());
    }

    @Override
    public String sayHello() {
        //调用b服务方法一
        helloFeignService.hello1();
        //调用b服务方法二
//        restTemplate.getForEntity("http://yuan-cam-b/hello",String.class).getBody();
        //调用b服务方法三
        helloService.hello();
        return "Hello!!!!";
    }

    @Autowired
    private RabbitProducer rabbitProducer;

    @Override
    public void HelloA() {
        rabbitProducer.rabbitFanoutExchange("aaaaaaa");
    }
}
