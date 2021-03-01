package com.yoanan.musicdb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(){
        return "index";
    }
}
