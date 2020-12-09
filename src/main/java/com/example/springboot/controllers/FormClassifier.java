package com.example.springboot.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Form classifier", description = "Api Endpoints for Form Classifier")
public class FormClassifier {
    @RequestMapping(value="/modal", method = RequestMethod.GET)
    public String modalFunction(){
        return "Lets Start now!!!";
    }
}
