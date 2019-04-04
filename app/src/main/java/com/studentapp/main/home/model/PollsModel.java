package com.studentapp.main.home.model;

import java.io.Serializable;

public class PollsModel implements Serializable {

    private String pollId;
    private String question;
    private String standardClass;
    private String division;
    private String options;

    public PollsModel() {
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStandardClass() {
        return standardClass;
    }

    public void setStandardClass(String standardClass) {
        this.standardClass = standardClass;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
