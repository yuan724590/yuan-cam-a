package yuan.cam.a.service;

public interface HelloService {

    String sayHelloRedis(String name);

    /**
     * 通过API调用对方的接口
     */
    String hello();
}