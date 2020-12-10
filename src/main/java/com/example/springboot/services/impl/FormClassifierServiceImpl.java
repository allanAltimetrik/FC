package com.example.springboot.services.impl;

import com.example.springboot.helper.ModalUtil;
import com.example.springboot.helper.ZipUtil;
import com.example.springboot.services.FormClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormClassifierServiceImpl implements FormClassifierService {
    private final ModalUtil modalUtil;
    private final ZipUtil zipUtil;

    @Autowired
    public FormClassifierServiceImpl(ModalUtil modalUtil, ZipUtil zipUtil){
        this.modalUtil = modalUtil;
        this.zipUtil = zipUtil;
    }
    @Override
    public String getModalString(){
        return modalUtil.generateRandomString();
    }
    // Add all service Implementations here like above
}
