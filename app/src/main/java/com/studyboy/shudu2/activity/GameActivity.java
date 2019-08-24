package com.studyboy.shudu2.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.studyboy.shudu2.listdata.MyDatabaseHelper;
import com.studyboy.shudu2.R;
import com.studyboy.shudu2.gamebase.ShuduData;
import com.studyboy.shudu2.view.ShuDuView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    /** 数独初始完整 数组 */
    private int[][] data = new int[9][9];
    /** 数独初始提示的数组 */
    private Random random = new Random();
    /** 每行显示的个数，对应难度*/
    int showNum = 6;
    String gameDifficulty = null;
    /** 用于判断暂停还是继续 */
    private boolean IsPause = false;
    /** 数独界面 */
    private ShuDuView shuDuView = null;

    TextView text = null;
    /** 计时器 */
    Chronometer chronometer = null;

    private long recordTime = 0;
    /** 游戏时间，单位秒 */
    int gameTime = 0;


    private int buttonColor;
    private static final String TAG = "GameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        initView();
        StartGame();
        // 获取数独数组
        getNumberArray(showNum);
    }

    public void initView(){
        // 获取难度
        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
        showNum = sp.getInt("showNum",5);
        gameDifficulty = sp.getString("difficulty","common");
//        setLevel(showNum);

        text = (TextView) findViewById(R.id.view_difficulty);
        text.setText("目前难度:"+ gameDifficulty +"   ");

        // 获取数独界面id
        shuDuView = (ShuDuView) findViewById(R.id.shudu_01);

        chronometer = (Chronometer) findViewById(R.id.time);
        chronometer.setFormat("%s");

        View view = findViewById(R.id.btn_start_game);
        if (view != null) {
            view.setOnClickListener(myOnclick);
        }
        View Btn_pause = findViewById(R.id.btn_pause);
        if(Btn_pause != null){
            Btn_pause.setOnClickListener(myOnclick);
        }
        View Btn_finish = findViewById(R.id.btn_finish);
        if(Btn_finish != null){
            Btn_finish.setOnClickListener(myOnclick);
        }

        // 填入数字按键 1 到 9
        View btn_num_01 = (Button)findViewById(R.id.btn_num_1);
        if (btn_num_01 != null) {
            btn_num_01.setOnClickListener(myOnclick);
        }
        View btn_num_02 = (Button)findViewById(R.id.btn_num_2);
        btn_num_02.setOnClickListener(myOnclick);
        View btn_num_03 = (Button)findViewById(R.id.btn_num_3);
        btn_num_03.setOnClickListener(myOnclick);
        View btn_num_04 = (Button)findViewById(R.id.btn_num_4);
        btn_num_04.setOnClickListener(myOnclick);
        View btn_num_05 = (Button)findViewById(R.id.btn_num_5);
        btn_num_05.setOnClickListener(myOnclick);
        View btn_num_06 = (Button)findViewById(R.id.btn_num_6);
        btn_num_06.setOnClickListener(myOnclick);
        View btn_num_07 = (Button)findViewById(R.id.btn_num_7);
        btn_num_07.setOnClickListener(myOnclick);
        View btn_num_08 = (Button)findViewById(R.id.btn_num_8);
        btn_num_08.setOnClickListener(myOnclick);
        View btn_num_09 = (Button)findViewById(R.id.btn_num_9);
        btn_num_09.setOnClickListener(myOnclick);

        buttonColor = getResources().getColor(R.color.blue);

    }

    public View.OnClickListener myOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.btn_start_game:
                //  重新开始游戏
                    restartGame( );
                    break;
                case  R.id.btn_pause:
                    // 暂停
                    playOrPause();
                    pauseAlertDialog();
                    break;
                case R.id.btn_finish:
                    // 完成
                    finishGame();
                    break;
                case R.id.btn_num_1:
                    shuDuView.setNewNumber(1);
                    break;
                case R.id.btn_num_2:
                    shuDuView.setNewNumber(2);
                    break;
                case R.id.btn_num_3:
                    shuDuView.setNewNumber(3);
                    break;
                case R.id.btn_num_4:
                    shuDuView.setNewNumber(4);
                    break;
                case R.id.btn_num_5:
                    shuDuView.setNewNumber(5);
                    break;
                case R.id.btn_num_6:
                    shuDuView.setNewNumber(6);
                    break;
                case R.id.btn_num_7:
                    shuDuView.setNewNumber(7);
                    break;
                case R.id.btn_num_8:
                    shuDuView.setNewNumber(8);
                    break;
                case R.id.btn_num_9:
                    shuDuView.setNewNumber(9);
                    break;
            }
        }
    };

    /**
     *  开始游戏并计时
     */
    public void StartGame( )
    {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        if (shuDuView != null) {
            shuDuView.postInvalidate();
        }
    }
    /**
     *  重新开始游戏
     */
     public void  restartGame(){
        shuDuView.clear();
         StartGame();
         getNumberArray(showNum);
     }
    /**
     * 暂停 、继续
     */
    public void playOrPause() {

        // 判断是否是暂停状态
        if (!IsPause) {
            chronometer.stop();
            IsPause = true;
            recordTime = SystemClock.elapsedRealtime();

        } else {
            chronometer.setBase(chronometer.getBase() + (SystemClock.elapsedRealtime() - recordTime));
            chronometer.start();//开始计时
            IsPause = false;
        }
    }

    /**
     *  完成
      */
    public void finishGame( ) {

        String time = chronometer.getText().toString();
        int minute = Integer.parseInt(time.split(":")[0]);
        int second = Integer.parseInt(time.split(":")[1]);
        gameTime = minute * 60 + second;
        // 先暂停时间
        playOrPause( );
        if(shuDuView.checkIsRight()) {
           // 完成数独则存入时间，不完成则弹出窗口选择继续
           insertGameData();
           finishDialogShow();
        } else {
            // 没完成，重绘数独界面
            shuDuView.postInvalidate();
            unfinishDialogShow();
        }
    }

    /**
     *  产生初始数组, difficulty 为对应的难度，据此每行显示4 到6 个
     */
    public void getNumberArray(int MyDifficulty) {
        // 获取完整数独矩阵
        data = new ShuduData().generateShudu();
        int ranNum=0;
        int[][] initData = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < MyDifficulty; j++) {
                // 产生提示数字,若产生的区域已有，则重新产生,如保证一行显示6 个
               do{
                   ranNum = random.nextInt(9);
               }while( initData[i][ranNum] != 0);

               initData[i][ranNum] = data[i][ranNum];
            }
        }
        shuDuView.setNumber(initData);
    }

    /**
     *  打开数据库，插入游戏记录
     */
    public void insertGameData( ){

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this,"MyScore.db",null,1);

        SQLiteDatabase db = null;
        ContentValues values = null;

        try{
             db = dbHelper.getWritableDatabase();
             if(db != null) {
                 values = new ContentValues();
                 values.put("level", gameDifficulty);
                 values.put("time", gameTime);
                 db.insert("MyScoreTable", null, values);

                 db.close();
                 db = null;
                 values = null;
             }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(db != null){
                    db.close();
                }
                if(values != null){
                    values = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            db = null;
        }

    }
   /**
   *  设定显示的游戏难度
   */
    public void setLevel(int  difficulty){
        switch (difficulty){
            case 6:
                gameDifficulty = "simple";
                break;
            case 5:
                gameDifficulty = "common";
                break;
            case 4:
                gameDifficulty = "difiicult";
                break ;
        }
    }

    /**
     *  游戏未完成提示
     */
    public void  unfinishDialogShow(){

        ShowDialog showDialog = new ShowDialog(this,"游戏提示!!!","      游戏尚未完成, 是否继续");
        // AlertDialog 布局视图View
        View dialogView = showDialog.getContentView();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("继续挑战", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                playOrPause();
            }
        });
        dialogBuilder.setNegativeButton("残忍离开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });

        // 获取到AlertDialog 对象
        AlertDialog dialog = dialogBuilder.create();
        dialog.setView(dialogView);
        dialog.show();

        // 修改按钮的字体大小
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(25);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(buttonColor);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(25);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(buttonColor);

    }

    /**
     *  游戏完成提示
     */
    public void finishDialogShow(){

        ShowDialog showDialog = new ShowDialog(this,"恭喜完成游戏!!!","      是否开始下一局游戏");
        // AlertDialog 布局视图View
        View dialogView = showDialog.getContentView();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setPositiveButton("继续挑战", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                restartGame();
            }
        });
        builder.setNegativeButton("残忍离开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });

        // 获取到AlertDialog 对象
        AlertDialog dialog = builder.create();
        dialog.setView(dialogView);
        dialog.show();
        //修改按钮的字体大小
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(25);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(buttonColor);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(25);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(buttonColor);
    }

    /**
    *  按下暂停按钮提示
    */
    public void pauseAlertDialog(){

        ShowDialog showDialog = new ShowDialog(this,"游戏提示!!!","      暂停中");
        // AlertDialog 布局视图View
        View dialogView = showDialog.getContentView();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("继续游戏", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 playOrPause( );//关闭对话框
             }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.setView(dialogView);
        dialog.show();
        // 设置按钮字体大小
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(28);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(buttonColor);
//        setDialogStyle(dialog);

/****************************************    设置窗口字体，自定义layout ，后 setView  ******************************************************/
//        // 设置AlerDialog 窗口大小
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.width = 700;
//        params.height = 700 ;
//        dialog.getWindow().setAttributes(params);
    }

    /**
     *  获取 Dialog 显示的 contentView
     */
    public class ShowDialog {
        private Context mContext;
        String title,message;

        public ShowDialog(Context context,String title,String message){
            mContext = context;
            this.title = title;
            this.message = message;
        }
        public View getContentView(){
            // 点击的按钮选项
            boolean isPos = false;
            // AlertDialog 布局视图View
            View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_item,null);
            TextView tv_Title = (TextView) dialogView.findViewById(R.id.tv_tilte_dialog);
            TextView tv_Message = (TextView) dialogView.findViewById(R.id.tv_message_dialog);

            tv_Title.setText(title);
            tv_Message.setText(message);

            return dialogView;
        }
    }

    /**
     *  设置 Button 样式，及窗口大小
     * @param dialog
     */
    public void setDialogStyle(AlertDialog dialog){

        // 设置AlerDialog 窗口大小
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 500;
        params.height = 300 ;
        dialog.getWindow().setAttributes(params);
    }

    /** 记录按下back 键的时间 */
    private long time;
    private  Toast mToast = null;
    /**
     *  按下 back两次退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判断是否为 back 键盘码
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - time > 2000) {

                mToast = Toast.makeText(GameActivity.this,"再按一次退出游戏",Toast.LENGTH_SHORT);
//                mToast.setText("再按一次退出游戏");
//                // 设置弹出显示的时间
//                mToast.setDuration(Toast.LENGTH_SHORT);
                // 设定显示的位置
//                mToast.setGravity(17, 0, -30);
                // 最后一步，show出来
                mToast.show();

                time = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public  void finish(){
        super.finish();
        if(mToast != null){
            mToast.cancel();
            Log.d(TAG, "finish: ************************* ");
        }
    }


}
