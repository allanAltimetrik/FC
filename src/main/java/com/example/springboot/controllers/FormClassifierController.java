package com.example.springboot.controllers;

import com.example.springboot.services.FormClassifierService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "Form classifier", description = "Api Endpoints for Form Classifier")
public class FormClassifierController {
    private final FormClassifierService formClassifierService;

    @Autowired
    public FormClassifierController(FormClassifierService formClassifierService) {
        this.formClassifierService = formClassifierService;
    }

    @RequestMapping(value = "/classifyInputFile", method = RequestMethod.POST)
    public HashMap<String, Object> processInputFile(@RequestParam("file") MultipartFile file) {
        return formClassifierService.processInputFile(file);
    }

    @RequestMapping(value = "/processSampleFile", method = RequestMethod.POST)
    public List<String> processSampleFile(@RequestParam("file") MultipartFile file,
                                          @RequestParam("type") String type,
                                          @RequestParam(name = "bias", required = false) String bias) {
        return formClassifierService.processSampleFile(file, type, bias);
    }

    @RequestMapping(value = "/getKeywords", method = RequestMethod.GET)
    public Map<String,String> getKeywords() {
        return formClassifierService.getKeywords();
    }

    @RequestMapping(value = "/getKeywords/{key}", method = RequestMethod.GET)
    public String[] getKeywords(@PathVariable String key) {
        return formClassifierService.getKeywords(key);
    }

    @RequestMapping(value = "/processedTextFromFile", method = RequestMethod.POST)
    public ResponseEntity getProcessedTextFromFile(@RequestParam("file") MultipartFile file) {
        String processedText = formClassifierService.getProcessedTextFromFile(file);
        return new ResponseEntity(processedText, HttpStatus.CREATED);
    }
}