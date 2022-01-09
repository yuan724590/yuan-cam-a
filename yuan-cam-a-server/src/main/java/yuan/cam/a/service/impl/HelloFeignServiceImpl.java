package yuan.cam.a.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import yuan.cam.a.service.HelloFeignService;


@Component
public class HelloFeignServiceImpl implements HelloFeignService {

    @Override
    public ResponseEntity<String> hello1() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}