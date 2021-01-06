package com.example.springboot.controllers;

import com.example.springboot.services.FormClassifierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "To Classify input File(s) from user")
    @RequestMapping(value = "/classifyInputFile", method = RequestMethod.POST)
    public HashMap<String, Object> processInputFile(@RequestParam("file") MultipartFile file) {
        return formClassifierService.processInputFile(file);
    }

    @ApiOperation(value = "To Extract keyword(s) from sample file")
    @RequestMapping(value = "/processSampleFile", method = RequestMethod.POST)
    public List<String> processSampleFile(@RequestParam("file") MultipartFile file,
                                          @RequestParam("type") String type,
                                          @RequestParam(name = "bias", required = false) String bias) {
        return formClassifierService.processSampleFile(file, type, bias);
    }

    @ApiOperation(value = "View All File Category keyword(s)")
    @RequestMapping(value = "/getKeywords", method = RequestMethod.GET)
    public Map<String,String> getKeywords() {
        return formClassifierService.getKeywords();
    }

    @ApiOperation(value = "View File Category keyword(s) based on given FileType")
    @RequestMapping(value = "/getKeywords/{fileType}", method = RequestMethod.GET)
    public String[] getKeywords(@PathVariable String fileType) {
        return formClassifierService.getKeywords(fileType);
    }

    @ApiOperation(value = "Get Processed Text from input File")
    @RequestMapping(value = "/processedTextFromFile", method = RequestMethod.POST)
    public ResponseEntity getProcessedTextFromFile(@RequestParam("file") MultipartFile file) {
        String processedText = formClassifierService.getProcessedTextFromFile(file);
        return new ResponseEntity(processedText, HttpStatus.CREATED);
    }
}