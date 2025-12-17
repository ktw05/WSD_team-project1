package org.example.wsd_final_team_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 기본 주소("/")로 들어오면 로그인 페이지로 토스!
    @GetMapping("/")
    public String home() {
        return "redirect:/member/login";
    }
}