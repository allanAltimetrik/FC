package com.example.springboot.model;

public class Report {
    private String type;
    private String complexity;
    public int extractedWordsCount;
    public Report(String type, String complexity, int extractedWordsCount) {
        this.type = type;
        this.complexity = complexity;
        this.extractedWordsCount = extractedWordsCount;
    }
    public String getType(){
        return this.type;
    }
    public String getComplexity(){
        return this.complexity;
    }
    public int getExtractedWordsCount() { return this.extractedWordsCount; }
}