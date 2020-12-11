package com.example.springboot.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FormClassifierService {
    void processInputFile(MultipartFile file);
    String processSampleFile(MultipartFile file, String type, String bias);
}