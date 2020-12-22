package com.example.springboot.services.impl;

import com.example.springboot.helper.*;
import com.example.springboot.services.FormClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class FormClassifierServiceImpl implements FormClassifierService {

    public String createFileFromMultipartFile(MultipartFile multipartFile, String directoryName)
    {
        String path = null;
        try{File directory = new File(directoryName);
            if (! directory.exists()){
                directory.mkdir();
            }
            path = directoryName + "/" + multipartFile.getOriginalFilename();
            InputStream initialStream = multipartFile.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);
            File file = new File(path);
            file.createNewFile();
            try (OutputStream outStream = new FileOutputStream(file)) {
                outStream.write(buffer);
            }
        }catch (IOException ex){ex.printStackTrace();}
        return path;
    }
    @Override
    public HashMap<String, Object> processInputFile(MultipartFile multipartFile){
        String uid = Long.toString(System.currentTimeMillis());
        String directoryToProcess = "src/main/java/com/example/springboot/resources/inputFromUser/" + uid;
        String subDirectoryToProcess = ZipUtil.moveFilesToInputFolder(multipartFile, directoryToProcess);
        String inputFormsDirectory = directoryToProcess + "/" + subDirectoryToProcess;
        HashMap<String, String> report = FileClassifierUtil.classifyForms(inputFormsDirectory);
        String outputFileName = FileClassifierUtil.storeAndReturnOutputFileName(inputFormsDirectory, report, uid);
        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("report", report);
        response.put("downloadUrl", "http://localhost:8080/download/" + outputFileName);
        return response;
    }

    @Override
    public List<String> processSampleFile(MultipartFile multipartFile, String type, String bias) {
        List<String> keywords = new ArrayList<String>();
        String  pathName = null;
        try{
            String directoryName = "src/main/java/com/example/springboot/resources/sampleFromUser/" + System.currentTimeMillis();
            pathName =createFileFromMultipartFile(multipartFile,directoryName);
            keywords = KeywordGenerationUtil.generateKeywordsFromImage(type, pathName, bias);
        }
        catch(Exception ex) {
            System.out.println("Exception occured in processSampleFile" + ex.toString());
        }
        return keywords;
    }

    @Override
    public Map<String,String> getKeywords(){
        Map<String,String> keywordsMap = new HashMap<>();
        String keywords = PropertyFileUtil.getKeysFromKeywordsFile();
        String[] keysArray = keywords.split(",");
        for(String each : keysArray)
        {
            keywordsMap.put(each.trim(), PropertyFileUtil.getValuesForKeyFromKeywordsFile(each.trim()));
        }
        return  keywordsMap;
    }

    @Override
    public String[] getKeywords(String key){
        String values =  PropertyFileUtil.getValuesForKeyFromKeywordsFile(key.trim());
        String[] valuesArray = values.split(",");
        return  valuesArray;
    }

    @Override
    public String getProcessedTextFromFile(MultipartFile inputFile){
        String processedText = null;
        String pathName = null;
        try{
            String directoryName = "src/main/java/com/example/springboot/resources/textExtraction/" + System.currentTimeMillis();
            pathName = createFileFromMultipartFile(inputFile,directoryName);
            String extractedText = OcrUtil.extractTextFromImage(pathName);
            processedText = "Processed Text: " + ExtractTextUtil.processExtractedText(extractedText).trim();
        }catch (Exception e){processedText = "Processed Text: Not able to read text from File!!!";}
        return  processedText;
    }
}