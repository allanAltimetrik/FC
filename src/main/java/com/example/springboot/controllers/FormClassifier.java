package com.example.springboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormClassifier {
    @RequestMapping("/modal")
    public String modalFunction(){
        return "Lets Start";
    }
}
