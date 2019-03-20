package com.studentapp.main.login.model;

import com.google.firebase.firestore.DocumentReference;

public class Schools {

    String id;
    String pincode;
    String schoolName;
    String cityNameRef;
    DocumentReference cities;
    Cities city;

    public Schools(String id, String pincode, String schoolName, Cities city) {
        this.id = id;
        this.pincode = pincode;
        this.schoolName = schoolName;
        this.city = city;
    }

    public Schools() {

    }

    public String getCityNameRef() {
        return cityNameRef;
    }

    public void setCityNameRef(String cityNameRef) {
        this.cityNameRef = cityNameRef;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public DocumentReference getCities() {
        return cities;
    }

    public void setCities(DocumentReference cities) {
        this.cities = cities;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
