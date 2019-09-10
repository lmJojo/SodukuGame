package com.studyboy.sudoku.listdata;

public class MyScore {

    public MyScore(int rank,int time  ){
        this.rank = rank;
        this.time = time;
    }
    private int time;
    private int rank;

    public int getRank() {

        return rank;
    }

    public int getTime() {

        return time;
    }

}
