package com.studyboy.shudu2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.studyboy.shudu2.R;

public class HelpActivity extends AppCompatActivity {

    TextView tv_title,tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);

        tv_title = (TextView) findViewById(R.id.tv_help_title);
        tv_content = (TextView) findViewById(R.id.tv_help_content);
    }


}
