package com.example.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sbb") // 내가 정의한 대로 요청을 보내면
    @ResponseBody // 요청한 대로 응답해 주는 것
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }

    @GetMapping("/")
    public String root() { // 루트 URL은 http://localhost:8080 처럼 도메인명과 포트 뒤에 아무것도 붙이지 않은 URL을 말함
        return "redirect:/question/list"; // /question/list URL로 페이지를 리다이렉트 하라는 명령어
    }
}
