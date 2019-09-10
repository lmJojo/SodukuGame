package com.studyboy.sudoku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.studyboy.shudu2.R;

/**
 * 根据选取的难度返回相应的取值
 */
public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    /** sdsdsdsd  s*/
    private Button btn_Simple,btn_Common,btn_Difficult;
    private TextView tv_Showlevel;
    /** 定义缓存容器 */
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_layout);

        //  获取容器缓冲区的存储位置   私有模式
        sp = getSharedPreferences("data", MODE_PRIVATE);

        btn_Simple = (Button) findViewById(R.id.btn_simple);
        btn_Simple.setOnClickListener(this);
        btn_Common = (Button) findViewById(R.id.btn_common);
        btn_Common.setOnClickListener(this);
        btn_Difficult = (Button) findViewById(R.id.btn_difficult);
        btn_Difficult.setOnClickListener(this);

        tv_Showlevel = (TextView) findViewById(R.id.tv_showlevel);
        showDifficulty(tv_Showlevel);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_simple:
                // 以 6 、 5 、4 ，表示难度，为数独每一列提示数字的个数
                setDifficulty(6,"simple");
                showDifficulty(tv_Showlevel);
                break;
            case R.id.btn_common:
                setDifficulty(5,"common");
                showDifficulty(tv_Showlevel);
                break;
            case R.id.btn_difficult:
                setDifficulty(4,"difficult");
                showDifficulty(tv_Showlevel);
                break;

        }
    }

    /**
     *  设定难度 ，每列显示个数，难度名称
     * @param index
     * @param difficulty
     */
    public void setDifficulty(int index,String difficulty){
        // 创建缓存容器
        SharedPreferences.Editor editor = sp.edit();
        // 将当前难度存储在缓冲容器中
        editor.putInt("showNum", index);
        editor.putString("difficulty",difficulty);
        // 异步存储，使用commit()则直接写入内存
        editor.apply();
    }

    /**
     *  获取当前难度名称
     * @return
     */
    public String getDifficulty(){
        String difficulty = null;
        difficulty = sp.getString("difficulty","common");

        return difficulty;
    }

    /**
     * 显示当前难度
     * @param tv_show
     */
    public void showDifficulty(TextView tv_show){

        String str = null;
        str = getResources().getString(R.string.showLevel);
        String difficulty = getDifficulty();
        tv_show.setText(str + difficulty);
    }
}
