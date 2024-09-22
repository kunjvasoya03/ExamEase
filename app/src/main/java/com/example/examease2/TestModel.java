package com.example.examease2;


public class TestModel {

    String testid;
    int topscore;
    int time;

    public TestModel( String testid,int topscore, int time) {
        this.topscore = topscore;
        this.testid = testid;
        this.time = time;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public int getTopscore() {
        return topscore;
    }

    public void setTopscore(int topscore) {
        this.topscore = topscore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
