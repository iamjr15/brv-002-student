package com.studentapp.model.school;

import java.io.Serializable;

import lombok.Data;

@Data
public class ModelSchool implements Serializable {
    private String schoolName = "";
    private String schoolId = "";
    private String cityName = "";
}
