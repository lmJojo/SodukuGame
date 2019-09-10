package com.studyboy.sudoku.activity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.studyboy.shudu2.R;
import com.studyboy.sudoku.listdata.MyAdapter;
import com.studyboy.sudoku.listdata.MyDatabaseHelper;
import com.studyboy.sudoku.listdata.MyScore;

import java.util.ArrayList;
import java.util.List;

public class BestHistoryActivity extends AppCompatActivity {

    /** 单选按钮  */
    private RadioGroup radio_select;
    /** 单选按钮 */
    private RadioButton btn_simple,btn_commmon,btn_difficult;
    /** listview 列表 */
    private List<MyScore>  scorelist = new ArrayList<>();
    MyAdapter adapter = null;
    ListView listView = null;
    /** 用于暂存数据库中查找出来的历史游戏记录 */
    int[] timeArray = new int[20];
    /** 游戏难度 */
    String gameLevel = null; int hisTime = 0;

    private static final String TAG = "BestHistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_history_layout);
        // 单选按钮
        radio_select = (RadioGroup) findViewById(R.id.Radio_select);
        radio_select.setOnCheckedChangeListener(new MyRadioListener());

        listView = (ListView)findViewById(R.id.list_view);
        gameLevel = "simple";
        selectLevel( );
    }

    /**
     * 难度记录选择
     */
    class MyRadioListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group,int checkedId){
            switch(checkedId){
                case R.id.Btn_simple:
                    gameLevel = "simple";
                    selectLevel( );
                    break;
                case  R.id.Btn_common:
                    gameLevel = "common";
                    selectLevel( );
                    break;

                case R.id.Btn_difficult:
                    gameLevel = "difficult";
                    selectLevel( );
                    break;
            }
        }
    }

    /**
     * 根据难度在数据库中检索数据,并显示listView
     */
    public void selectLevel(){

        clearData();
        getDBData();
        initScoreList();
        showListview();
    }

    /**
     * 根据难度获取数据库记录数
     */
    public void getDBData(){

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this,"MyScore.db",null,1);
        dbHelper.getWritableDatabase();
        int i = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.openOrCreateDatabase("MyScore.db",MODE_PRIVATE,null);
            if(db != null){
                cursor = db.rawQuery("select time from MyScoreTable where level = '"+ gameLevel +"'" +
                        " order by time asc limit 10  ",null);
                Log.d(TAG, "getDBData: ***************************************** 数据查询");
                if (cursor != null) {
                    Log.d(TAG, "getDBData: ***************************************** 数据查询结果");
                    if(cursor.moveToFirst()) {
                        do {
                            int id = cursor.getInt(cursor.getColumnIndex("time"));
                            Log.d(TAG, "getDBData: ***************************************** 查询结果"+id);
                            timeArray[i] = id;
                            i++;
                        } while (cursor.moveToNext());
                    }
                    // 关闭cursor 和数据库
                    cursor.close();
                    cursor = null;
                }
                db.close();
                db = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null){
                    cursor.close();
                    cursor = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (db != null){
                    db.close();
                    db = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *     清空之前数组、列表的记录
     */
    public void clearData() {
        // scorelist.clear();
        for(int i = 0;i < 10;i++) {
            timeArray[i] = 0;
        }
    }
    /**
     * 初始化分数列表的时间数据
     */
    private void initScoreList(){

        scorelist.clear();
        // timeArray[i] > 0 ,i
        for(int i = 0; timeArray[i] > 0 ; i++) {
            MyScore rank_1 = new MyScore(i+1, timeArray[i]);
            scorelist.add(rank_1);
            Log.d(TAG, "initScoreList: ************************ 数组"+timeArray[i] );
        }
    }

    /**
     * 传入适配器，显示listview
     */
    public void showListview( ){
        initScoreList();
        // listview 列表
        adapter = new MyAdapter(BestHistoryActivity.this,R.layout.score_listview_item ,scorelist);
        listView.setAdapter(adapter);
        // 长按
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MyScore myScore = scorelist.get(position);
                hisTime = myScore.getTime();
                showDeleteDialog();
                return false;
            }
        });
    }

    /**
     *   显示删除提示界面
     */
    public void showDeleteDialog() {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);

        deleteDialog.setTitle("数独游戏");
        deleteDialog.setMessage("     您确定要删除该记录吗");
        deleteDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 数据库删除
                        deleteDBHistory();
                        // 重新获取数据刷新列表
                        selectLevel();
                    }
                }).show();
    }

    public void deleteDBHistory(){

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this,"MyScore.db",null,1);
        // 若没有 file.db 数据库，则创建
        SQLiteDatabase db = null;
        try{
            Log.d(TAG, "deleteDBHistory: ***********************************************  尝试删除");
            db = dbHelper.getWritableDatabase();
            if(db != null) {
                db.delete("MyScoreTable", "level = ? and  time = ?", new String[]{ gameLevel, String.valueOf(hisTime) });
                Toast.makeText(BestHistoryActivity.this, "删除成功 ", Toast.LENGTH_SHORT).show();
            }
            db.close();
            db  = null;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(db != null){
                    db.close();
                    db = null;
                }
            } catch ( Exception e){
                e.printStackTrace();
            }
        }
    }


}