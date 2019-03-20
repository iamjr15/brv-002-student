package com.studentapp.model.base;


import lombok.Data;

@Data
public class DataWrapper<T> {
    private Exception apiException;
    private T data;
    private int state;
    private String errorMsg;
}