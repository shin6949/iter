package com.cos.instagram.util;

import org.springframework.stereotype.Service;

@Service
public class Logging {
    public String getClassName() {
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }

    public String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
