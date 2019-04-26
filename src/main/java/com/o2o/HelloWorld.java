package com.o2o;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kai.yu
 * @date 2019/4/25
 **/
@RestController
public class HelloWorld {

    @GetMapping("/hello")
    public String hello() {
        return "Hello SpringBoot";
    }
}
