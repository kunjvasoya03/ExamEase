package com.example.examease2;

public class RankModel
{
    int score,rank;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public RankModel(int score, int rank) {
        this.score = score;
        this.rank = rank;
    }
}
