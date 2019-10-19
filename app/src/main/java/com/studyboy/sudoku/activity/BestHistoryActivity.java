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


import com.studyboy.shudu2.R;
import com.studyboy.sudoku.listdata.MyAdapter;
import com.studyboy.sudoku.listdata.MyDatabaseHelper;
import com.studyboy.sudoku.listdata.MyScore;
import com.studyboy.sudoku.listdata.Util;

import org.litepal.LitePal;

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

    /** 游戏难度 */
    private int gameLevel = Util.SIMPLE;

    private static final String TAG = "BestHistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_history_layout);
        // 单选按钮
        radio_select = (RadioGroup) findViewById(R.id.Radio_select);
        radio_select.setOnCheckedChangeListener(new MyRadioListener());

        listView = ( ListView )findViewById(R.id.list_view);
        getDBdata( );
    }

    /**
     * 难度记录选择
     */
    class MyRadioListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group,int checkedId){
            switch(checkedId){
                case R.id.Btn_simple:
                    gameLevel = Util.SIMPLE;
                    getDBdata( );
                    break;
                case  R.id.Btn_common:
                    gameLevel = Util.COMMON;
                    getDBdata( );
                    break;

                case R.id.Btn_difficult:
                    gameLevel = Util.DIFFICULT;
                    getDBdata( );
                    break;
                    //  " '%"+gameLevel+"%' "
            }
        }
    }

    /**
     * 根据难度在数据库中检索数据,并显示listView
     */
    public void getDBdata(){
        scorelist.clear();
        scorelist = LitePal.select("time").where("level = ?", gameLevel+"")
                .order("time asc").limit(10).find(MyScore.class);

        showListview();
    }

    /**
     * 传入适配器，显示listview
     */
    public void showListview( ){

        // listview 列表
        adapter = new MyAdapter(BestHistoryActivity.this,R.layout.score_listview_item ,scorelist);
        listView.setAdapter(adapter);
        // 长按
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentPositin = position;
                showDeleteDialog();
                return false;
            }
        });
    }
    private int currentPositin = 0;
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
                        getDBdata();
                    }
                }).show();
    }

    public void deleteDBHistory(){

        scorelist.get( currentPositin ).delete();

    }


}