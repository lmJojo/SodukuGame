package com.studyboy.sudoku.gamebase;

/**
 *  10 个数独产生的模板，将1-9 替换a-i 后产生数组
 */
public class ShuduBase {

     public String[][] getStand(int index) {
         switch (index) {
             case 0:
                 return stand0;
             case 1:
                 return stand1;
             case 2:
                 return stand2;
             case 3:
                 return stand3;
             case 4:
                 return stand4;
             case 5:
                 return stand5;
             default:
                 return stand0;
         }
     }


     private String[][] stand1 = new String[][]{
             {"c","h","e","b","i","a","g","f","d",},
             {"f","b","d","g","e","c","h","a","i",},
             {"g","i","a","d","f","h","c","e","b",},
             {"h","g","i","e","d","b","a","c","f",},
             {"a","d","f","c","g","i","b","h","e",},
             {"e","c","b","h","a","f","i","d","g",},
             {"b","e","c","f","h","g","d","i","a",},
             {"i","f","g","a","c","d","e","b","h",},
             {"d","a","h","i","b","e","f","g","c",}
     };

    private String[][] stand2 = new String[][]{
            {"a","c","f","b","d","e","h","i","g",},
            {"b","g","d","h","i","a","f","c","e",},
            {"h","i","e","f","c","g","b","a","d",},
            {"d","f","b","g","e","c","a","h","i",},
            {"g","a","i","d","h","f","c","e","b",},
            {"e","h","c","i","a","b","g","d","f",},
            {"i","e","g","a","f","h","d","b","c",},
            {"c","b","h","e","g","d","i","f","a",},
            {"f","d","a","c","b","i","e","g","h",}
    };

    private String[][] stand3 = new String[][]{
            {"f","g","h","e","i","d","b","a","c",},
            {"e","b","c","f","a","g","i","h","d",},
            {"d","a","i","c","h","b","f","e","g",},
            {"i","f","d","a","g","e","c","b","h",},
            {"b","h","a","i","d","c","g","f","e",},
            {"c","e","g","b","f","h","a","d","i",},
            {"h","c","b","g","e","f","d","i","a",},
            {"a","d","f","h","c","i","e","g","b",},
            {"g","i","e","d","b","a","h","c","f",}
    };
    private String[][] stand4 = new String[][]{
            {"i","a","h","b","c","f","d","e","g",},
            {"f","d","e","g","i","h","b","c","a",},
            {"b","c","g","e","a","d","i","f","h",},
            {"g","i","f","c","b","a","h","d","e",},
            {"d","h","a","f","g","e","c","i","b",},
            {"e","b","c","h","d","i","a","g","f",},
            {"a","g","b","i","e","c","f","h","d",},
            {"h","e","i","d","f","b","g","a","c",},
            {"c","f","d","a","h","g","e","b","i",}
    };
    private String[][] stand5 = new String[][]{
            {"h","e","d","b","f","g","a","c","i",},
            {"c","i","a","d","e","h","b","g","f",},
            {"f","b","g","a","i","c","e","h","d",},
            {"a","f","i","e","g","d","h","b","c",},
            {"d","g","h","c","b","f","i","a","e",},
            {"e","c","b","h","a","i","d","f","g",},
            {"g","d","e","f","h","b","c","i","a",},
            {"b","a","f","i","c","e","g","d","h",},
            {"i","h","c","g","d","a","f","e","b",}
    };

    private String[][] stand6 = new String[][]{
            {"i","g","b","f","a","h","e","c","d",},
            {"c","a","h","d","g","e","i","b","f",},
            {"f","d","e","b","c","i","h","a","g",},
            {"b","c","a","e","d","g","f","h","i",},
            {"g","e","i","h","f","a","b","d","c",},
            {"h","f","d","c","i","b","a","g","e",},
            {"a","i","c","g","b","f","d","e","h",},
            {"d","h","f","a","e","c","g","i","b",},
            {"e","b","g","i","h","d","c","f","a",}
    };
    private String[][] stand7 = new String[][]{
            {"d","g","c","i","e","f","b","h","a",},
            {"f","e","h","a","b","c","g","i","d",},
            {"a","b","i","d","g","h","c","f","e",},
            {"e","a","g","f","d","b","i","c","h",},
            {"i","c","d","e","h","g","f","a","b",},
            {"b","h","f","c","i","a","e","d","g",},
            {"h","d","b","g","f","i","a","e","c",},
            {"c","f","e","b","a","d","h","g","i",},
            {"g","i","a","h","c","e","d","b","f",}
    };
    private String[][] stand8 = new String[][]{
            {"e","f","g","a","d","c","b","h","i",},
            {"d","i","h","b","g","f","a","e","c",},
            {"c","a","b","e","h","i","d","f","g",},
            {"i","h","f","d","b","g","c","a","e",},
            {"g","e","a","i","c","h","f","d","b",},
            {"b","d","c","f","e","a","g","i","h",},
            {"a","c","e","h","f","b","i","g","d",},
            {"h","b","i","g","a","d","e","c","f",},
            {"f","g","d","c","i","e","h","b","a",}
    };
    private String[][] stand9 = new String[][]{
            {"d","a","g","e","f","c","h","i","b",},
            {"f","b","i","g","d","h","e","a","c",},
            {"e","c","h","i","b","a","g","d","f",},
            {"h","d","f","c","a","e","b","g","i",},
            {"i","g","a","b","h","f","c","e","d",},
            {"c","e","b","d","i","g","f","h","a",},
            {"a","f","c","h","e","i","d","b","g",},
            {"g","h","d","a","c","b","i","f","e",},
            {"b","i","e","f","g","d","a","c","h",}
    };

    private String[][] stand0 = new String[][]{
            {"a","b","i","g","d","e","c","h","f",},
            {"c","h","f","b","a","i","e","d","g",},
            {"e","d","g","c","f","h","b","i","a",},
            {"d","e","b","a","h","c","g","f","i",},
            {"h","i","c","f","g","b","d","a","e",},
            {"g","f","a","i","e","d","h","c","b",},
            {"i","c","h","e","b","f","a","g","d",},
            {"b","g","d","h","i","a","f","e","c",},
            {"f","a","e","d","c","g","i","b","h",}
    };

}
