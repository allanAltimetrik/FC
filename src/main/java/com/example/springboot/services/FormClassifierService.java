package com.example.springboot.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public interface FormClassifierService {
    HashMap<String, String> processInputFile(MultipartFile file);
    List<String> processSampleFile(MultipartFile file, String type, String bias);
    Map<String,String> getKeywords();
    String[] getKeywords(String key);
    String getProcessedTextFromFile(MultipartFile file);
}