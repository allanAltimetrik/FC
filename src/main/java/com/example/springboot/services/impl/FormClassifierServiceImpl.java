package com.example.springboot.services.impl;

import com.example.springboot.services.FormClassifierService;
import org.springframework.stereotype.Service;

@Service
public class FormClassifierServiceImpl implements FormClassifierService {
    @Override
    public String getModalString(){
        return "Dummy text";
    }
    // Add all service Implementations here like above
}
