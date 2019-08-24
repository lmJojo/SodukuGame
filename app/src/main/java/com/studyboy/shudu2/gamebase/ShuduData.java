package com.studyboy.shudu2.gamebase;

import java.util.Random;
/**
 * 用于根据模板产生二维数组
 */
public class ShuduData {

    private int[][] number = new int[9][9];
    private ShuduBase shudu_base = new ShuduBase();
    private Random random = new Random();

    public int[][]  generateShudu(){

        // 获取模板
        String[][] stand = shudu_base.getStand(random.nextInt(10));
        // 产生打乱顺序的1-9 序列
        int[] data = new int[] {1,2,3,4,5,6,7,8,9};
        int index = 0;
        for(int i = 0;i < 5;i++ ){
             index = random.nextInt(9);
             // data[i] 和data[index] 的值交换
             int t = data[index];
             data[index] = data[i];
             data[i] = t;
        }
        // 根据模板填充1-9
        for (int i = 0;i < 9;i++){
             for (int j = 0;j < 9;j++){
                 number[i][j] = data[getIndex(stand[i][j]) - 1];
             }
        }
        return number;
    }

    /**
     *  1-9 替换模板中的 a-i
     */
    private int getIndex(String s){
        switch (s){
            case "a":
                return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
            case "i":
                return 9;
            default:
                return 0;
        }
    }
}
