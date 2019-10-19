package com.studyboy.sudoku.listdata;

public class Util {

    public static  final int SIMPLE = 6 ;
    public static  final int COMMON = 5 ;
    public static final int DIFFICULT = 4;

    public static String ScoreFormat(int time){
        int hh = time/3600;
        int mm = time %3600 /60;
        int ss = time %60;
        String str = null;
        if(hh > 0){
            str = String.format("%d小时 %d分 %d秒",hh,mm,ss);
        }
        else if(mm > 0){
            str = String.format(" %d分 %d秒",mm,ss);
        }
        else{
            str = String.format(" %d秒",ss);
        }
        return str;
    }
}
