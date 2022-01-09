package yuan.cam.a.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yuan.cam.a.service.HelloService;
import yuan.cam.b.dto.HelloDTO;
import yuan.cam.b.export.GetHello;


@Component
public class HelloServiceImpl implements HelloService {

    @Autowired
    private GetHello getHello;

    @Override
    public String sayHelloRedis(String name) {
        HelloDTO.SayHelloDTO sayHelloDTO = new HelloDTO.SayHelloDTO();
        sayHelloDTO.setName(name);
        getHello.sayHello(sayHelloDTO);
        return "成功调用b服务接口";
    }

    @Override
    public String hello() {
        getHello.sayHelloAgain();
        return "成功调用b服务接口";
    }
}