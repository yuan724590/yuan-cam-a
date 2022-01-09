package yuan.cam.a.service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import yuan.cam.a.service.impl.HelloFeignServiceImpl;

@FeignClient(value = "YUAN-CAM-B", fallback = HelloFeignServiceImpl.class)
public interface HelloFeignService {

    /**
     * 通过feign + url直接请求对应的服务
     */
    @GetMapping(value = "/hello/hello")
    ResponseEntity<String> hello1();

}