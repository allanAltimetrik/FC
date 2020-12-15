package com.example.springboot.controllers;

import com.example.springboot.services.FormClassifierService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

@RestController
@Api(tags = "Form classifier", description = "Api Endpoints for Form Classifier")
public class FormClassifierController {
    private final FormClassifierService formClassifierService;

    @Autowired
    public FormClassifierController(FormClassifierService formClassifierService) {
        this.formClassifierService = formClassifierService;
    }

    @RequestMapping(value = "/processInputFile", method = RequestMethod.POST)
    public HashMap<String, String> processInputFile(@RequestParam("file") MultipartFile file) {
        return formClassifierService.processInputFile(file);
    }

    @RequestMapping(value = "/processSampleFile", method = RequestMethod.POST)
    public List<String> processSampleFile(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("type") String type,
                                                             @RequestParam(name = "bias", required = false) String bias) {
        return formClassifierService.processSampleFile(file, type, bias);
    }
}
