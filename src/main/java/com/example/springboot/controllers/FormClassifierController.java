package com.example.springboot.controllers;

import com.example.springboot.services.FormClassifierService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "Form classifier", description = "Api Endpoints for Form Classifier")
public class FormClassifierController {
    private final FormClassifierService formClassifierService;

    @Autowired
    public FormClassifierController(FormClassifierService formClassifierService){
        this.formClassifierService = formClassifierService;
    }
    @RequestMapping(value="/modal", method = RequestMethod.GET)
    public String modalFunction(){
        return formClassifierService.getModalString();
    }
    @RequestMapping(value="/processInputFile", method = RequestMethod.POST)
    public String processInputFile(@RequestParam("file") MultipartFile file){
        return "File Name " + file.getOriginalFilename();
    }
    @RequestMapping(value="/processSampleFile", method = RequestMethod.POST)
    public String processSampleFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String type){
        return "File Name " + file.getOriginalFilename() + " is a sample of " + type + " form";
    }
}
