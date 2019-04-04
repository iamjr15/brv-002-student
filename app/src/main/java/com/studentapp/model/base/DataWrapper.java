package com.studentapp.model.base;


import java.io.Serializable;

import lombok.Data;

@Data
public class DataWrapper<T extends Serializable> implements Serializable {
    private Exception apiException;
    private T data;
    private int state;
    private String errorMsg;
}