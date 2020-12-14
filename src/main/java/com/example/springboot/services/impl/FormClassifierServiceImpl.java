package com.example.springboot.services.impl;

import com.example.springboot.helper.KeywordGenerationUtil;
import com.example.springboot.helper.ModalUtil;
import com.example.springboot.helper.OcrUtil;
import com.example.springboot.helper.ZipUtil;
import com.example.springboot.services.FormClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

@Service
public class FormClassifierServiceImpl implements FormClassifierService {
    private final KeywordGenerationUtil keywordGenerationUtil;
    @Autowired
    public FormClassifierServiceImpl(KeywordGenerationUtil keywordGenerationUtil){
        this.keywordGenerationUtil = keywordGenerationUtil;
    }

    @Override
    public void processInputFile(MultipartFile multipartFile){
        //IndusUtlil(multipartFile, "src/main/java/com/example/springboot/resources/input/" + System.currentTimeMillis());
    }

    @Override
    public Hashtable<String, List<String>>  processSampleFile(MultipartFile multipartFile, String type, String bias) {
        try{
            String pathName = "src/main/java/com/example/springboot/resources/" + System.currentTimeMillis() + "/" + multipartFile.getOriginalFilename();
            File file = new File("pathName");
            file.createNewFile();
            multipartFile.transferTo(file);
            return keywordGenerationUtil.generateKeywordsFromImage(type, pathName);
        }
        catch(Exception ex) {
            System.out.println("Exception occured in processSampleFile" + ex.toString());
        }
        return new Hashtable<>();
    }
}
