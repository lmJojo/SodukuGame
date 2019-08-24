package com.studyboy.shudu2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.studyboy.shudu2.R;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    private Button btn_Start,btn_Select,btn_History,btn_Help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Start  = (Button) findViewById(R.id.btn_start);
        btn_Select = (Button) findViewById(R.id.btn_select);
        btn_History = (Button) findViewById(R.id.btn_history);
        btn_Help = (Button) findViewById(R.id.btn_help);
        btn_Start.setOnClickListener(this);
        btn_Select.setOnClickListener(this);
        btn_History.setOnClickListener(this);
        btn_Help.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_start:
                // 开始游戏
                Intent startIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(startIntent);
                break;
            case R.id.btn_select:
                // 难度选择
                Intent selectIntent = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(selectIntent);
                break;
            case R.id.btn_history:
                // 查看历史记录
                Intent intent = new Intent(MainActivity.this, BestHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_help:
                // 查看帮助
                Intent helpIntent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                break;
        }
    }

}
