package com.studyboy.sudoku.listdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * 创建数据库
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

//    private static final int version=1;
//    private String name;
    private Context mContext;
    public MyDatabaseHelper(Context context, String name , SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
       db.execSQL("create table if not exists MyScoreTable(level text,time integer )"); // 建表 MyScore
        Toast.makeText(mContext,"6666666创建成功",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
