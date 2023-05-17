package com.example.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // controller 에서는 controller 을 꼭 선언해 줘야 함
public class HelloController {
    @GetMapping("/hello") // 역할을 처리하는 annotation
    @ResponseBody
    public String hello() {
        return "Hello World";
    }
}
