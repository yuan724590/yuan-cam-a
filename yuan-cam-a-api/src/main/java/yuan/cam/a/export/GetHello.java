package yuan.cam.a.export;

import io.swagger.annotations.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yuan.cam.a.common.Constants;
import yuan.cam.a.dto.HelloDTO;

@Api(value = "测试")
@FeignClient(value = Constants.SERVICE_NAME)
public interface GetHello {

    @GetMapping("/apollo")
    String sayHelloApollo();

    @PostMapping("/redis")
    String sayHelloRedis(@RequestBody @Validated HelloDTO.HelloRedisDTO reqDTO);

    @GetMapping("/hello1")
    String sayHello();

    @PostMapping("/Hello/A")
    void HelloA();
}
