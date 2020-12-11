package com.example.springboot.services.impl;

import com.example.springboot.helper.ModalUtil;
import com.example.springboot.helper.OcrUtil;
import com.example.springboot.helper.ZipUtil;
import com.example.springboot.services.FormClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FormClassifierServiceImpl implements FormClassifierService {
    private final ZipUtil zipUtil;
    private final OcrUtil ocrUtil;

    @Autowired
    public FormClassifierServiceImpl(ZipUtil zipUtil, OcrUtil ocrUtil){
        this.zipUtil = zipUtil;
        this.ocrUtil = ocrUtil;
    }

    @Override
    public void processInputFile(MultipartFile file){
        zipUtil.copyFolder();
    }

    @Override
    public String processSampleFile(MultipartFile file, String type, String bias) {
        return "File Name " + file.getOriginalFilename() + " is a sample of " + type + " form " + bias + " bias";
    }
}
