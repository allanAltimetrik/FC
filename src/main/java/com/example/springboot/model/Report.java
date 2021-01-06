package com.example.springboot.model;

public class Report {
    private String type;
    private String complexity;
    public Report(String type, String complexity) {
        this.type = type;
        this.complexity = complexity;
    }
    public String getType(){
        return this.type;
    }
    public String getComplexity(){
        return this.complexity;
    }
}