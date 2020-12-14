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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
    public List<String>  processSampleFile(MultipartFile multipartFile, String type, String bias) {
        List<String> keywords = new ArrayList<String>();
        try{
            String directoryName = "src/main/java/com/example/springboot/resources/sampleFromUser/" + System.currentTimeMillis();
            File directory = new File(directoryName);
            if (! directory.exists()){
                directory.mkdir();
            }
            String pathName = directoryName + "/" + multipartFile.getOriginalFilename();
            InputStream initialStream = multipartFile.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);
            File file = new File(pathName);
            file.createNewFile();
            try (OutputStream outStream = new FileOutputStream(file)) {
                outStream.write(buffer);
            }
            keywords = keywordGenerationUtil.generateKeywordsFromImage(type, pathName, bias);
        }
        catch(Exception ex) {
            System.out.println("Exception occured in processSampleFile" + ex.toString());
        }
        return keywords;
    }
}
