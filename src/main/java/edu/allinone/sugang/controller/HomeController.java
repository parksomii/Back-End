package edu.allinone.sugang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 기본 홈 화면
    @GetMapping({"/", "/home"})
    public String home() {
        return "home"; // home.html로 매핑
    }

    // 로그인 화면
    @GetMapping("/login")
    public String login() {
        return "login"; // login.html로 매핑
    }
}
