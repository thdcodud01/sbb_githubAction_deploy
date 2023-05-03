package com.example.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/plus")
    @ResponseBody
    public int showPlus(int a, int b) {
        return a + b;
    }
    @GetMapping("/minus")
    @ResponseBody
    public int showMinus(int a, int b) {
        return a - b;
    }
    int a = 0;
    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease() {
        return a++;
    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showMbti(@PathVariable String name) {
        return switch (name) {
            case "scy" -> {
                char i = 'I';
                yield i + "SFP";
            }
            case "kde" -> "INFP";
            case "sjh", "his" -> "ESTJ";
            default -> "none";
        };
    }

}
