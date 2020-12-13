package yuan.cam.a.service;

//@FeignClient(value = "yuan-cam-b", fallback = HelloServiceImpl.class)
public interface HelloService {

//    @GetMapping(value = "/hello/hello")
//    ResponseEntity<String> hello();

    String sayHelloRedis(String name);

    String hello();

}