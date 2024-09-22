package com.example.examease2;

import android.graphics.Bitmap;

public class HomeModel {
    String DOC_ID,name;
    int img_id;


    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public HomeModel(String DOC_ID, String name,int img_id) {
        this.DOC_ID = DOC_ID;
        this.img_id = img_id;
        this.name = name;
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
}
