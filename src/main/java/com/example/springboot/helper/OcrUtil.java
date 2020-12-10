package com.example.springboot.helper;

import net.sourceforge.tess4j.Tesseract;

import java.io.File;

public class OcrUtil {

    public static String extractTextFromImage(String File){

        File Image = new File(File);
        String ExtractedText = null;

        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/java/com/example/springboot/resources/trainedData");
            tesseract.setLanguage("eng");
            tesseract.setPageSegMode(1);
            tesseract.setOcrEngineMode(1);

            ExtractedText = tesseract.doOCR(Image);
            System.out.println(ExtractedText);
        }
        catch (Exception e){
            System.out.println("Exception - " + e);
        }

        return ExtractedText;

    }

}