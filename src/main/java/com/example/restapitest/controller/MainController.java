package com.example.restapitest.controller;

import com.example.restapitest.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @Autowired
    MainService service;

    @GetMapping
    public String mainPage(){
        return "sendFilePage";
    }
}
