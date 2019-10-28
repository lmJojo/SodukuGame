package com.studyboy.sudoku.listdata;

import org.litepal.crud.LitePalSupport;


public class MyScore extends LitePalSupport {

    public MyScore(){

    }
    private int time;
    private int level;
//    // 用于删除数据时，避免相同数据一起删除
//    private int id;


    public int getTime() {
        return time;
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
