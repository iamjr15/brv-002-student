package com.studentapp.main.home.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class PollsModel implements Serializable {

    private String pollId;
    private String question;
    private String standardClass;
    private String division;
    private List<String> options;

    @Exclude
    private boolean isAnswered = false;

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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    @Override
    public String toString() {
        return "PollsModel{" +
                "pollId='" + pollId + '\'' +
                ", question='" + question + '\'' +
                ", standardClass='" + standardClass + '\'' +
                ", division='" + division + '\'' +
                ", options=" + options +
                ", isAnswered=" + isAnswered +
                '}';
    }
}
