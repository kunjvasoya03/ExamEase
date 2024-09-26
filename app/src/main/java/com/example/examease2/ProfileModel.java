package com.example.examease2;

public class ProfileModel {
    String name,mail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public ProfileModel(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }
}
