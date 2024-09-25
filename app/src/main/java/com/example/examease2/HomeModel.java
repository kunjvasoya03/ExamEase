package com.example.examease2;

import android.graphics.Bitmap;

public class HomeModel {
    String DOC_ID,name;
    int noOfTests;

    public HomeModel(String DOC_ID, String name, int noOfTests) {
        this.DOC_ID = DOC_ID;
        this.name = name;
        this.noOfTests = noOfTests;
    }

    public String getDOC_ID() {
        return DOC_ID;
    }

    public void setDOC_ID(String DOC_ID) {
        this.DOC_ID = DOC_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoOfTests() {
        return String.valueOf(noOfTests);
    }

    public void setNoOfTests(int noOfTests) {
        this.noOfTests = noOfTests;
    }
}
