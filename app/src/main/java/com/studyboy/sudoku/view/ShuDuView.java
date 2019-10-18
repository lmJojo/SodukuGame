package com.studyboy.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.util.Random;
import com.studyboy.shudu2.R;
import java.text.DecimalFormat;

public class ShuDuView extends View {

    private int[][] number = new int[9][9];  // 提示数组

    /** 初始数字和填入数字的组合版，用于最终结果判断  */
    private int[][] finalNumber = new int[9][9];

    /** 用户填入的数字 */
    private int[][] user = new int[9][9];

    private int[][] error = new int[9][9];  // 错误数组位置保存

    private  int[][] click = new int[9][9];  // 用于显示点击区域


    // 控件宽高
    private int width;
    private int height;
    // 画笔
    private Paint paint;
    // 点击屏幕的 原始触点坐标
    private float touchX = 0;
    private float touchY = 0;
    // 点击屏幕的点 在 9 * 9 方格的下标
    private int stateX = -1;
    private int stateY = -1;
    // 每个方格的 宽高
    private int step = 0;
    // 计算下标的数字格式化
    private DecimalFormat decimalFormat = new DecimalFormat("0");

    // 网格 底色 字体颜色等属性值
    private int lineBorder;
    private int textColor;
    private int blueBackground;
    private int choseBackground;
    private int errorBackground;

    private Random random=new Random();

    /** 横线 的起始坐标 (h1,ht) 终点坐标（hr,hb）*/
    float hl = 0;
    float ht = 0;
    float hr = 0;
    float hb = 0;
    /** 竖线 的起始坐标 (s1,st) 终点坐标（sr,sb）*/
    float sl = 0;
    float st = 0;
    float sr = 0;
    float sb = 0;
    /** 坐标偏移量 */
    int deviation = 0;

    public ShuDuView(Context context, AttributeSet attrs){

        super(context, attrs);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShuDuView);   // 两个参数，默认值

        lineBorder = getResources().getColor(R.color.boder_line);
        textColor = getResources().getColor(R.color.text_color);
        blueBackground = getResources().getColor(R.color.blue_background );
        choseBackground = getResources().getColor(R.color.chose_background);
        errorBackground = getResources().getColor(R.color.error_background);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setStrokeJoin(Paint.Join.ROUND);  // 圆弧
        paint.setStrokeCap(Paint.Cap.ROUND);    // 线帽圆形
        paint.setStrokeWidth(1);                // 宽度
        paint.setAntiAlias(true);               // 抗锯齿,模糊锯齿边界
    }

    /**
     *  画出数独界面，onDraw方法需要invalidateI() 或者postInvalidate()来触发
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        // 保存整个控件的高度
        if(width > height){
            deviation = (width - height) /2;
            width = height;
        }else {
            height = width;
        }

        step = width /9;
        // 画出网格
        for(int x = 0;x < 10;x++){
            if(x % 3 == 0) {
                paint.setStrokeWidth(4);
                paint.setColor(lineBorder);
                if (x == 9) {
                    // 横线起点、终点坐标
                    hl = 0; ht = height; hr = width; hb = height;
                    // 竖线起点、终点坐标
                    sl = width; st = 0;  sr = width; sb = height;
                }else if(x == 0) {
                    hl = 0; ht = 2; hr = width; hb = 2;
                    sl = 0; st = 0; sr = 0; sb = height;
                }else {
                    hl = 0; ht = step*x; hr = width; hb = step*x;
                    sl = step*x; st=0; sr = step*x; sb = height;
                }
            }else {
                paint.setStrokeWidth(2);
                paint.setColor(lineBorder);
                // 横线起点、终点坐标
                hl = 0; ht = step*x; hr = width; hb = step*x;
                // 竖线起点、终点坐标
                sl = step*x; st = 0; sr = step*x; sb = height;
            }
            // 横线起点的 x、y 坐标，终点的 x、y 坐标
            canvas.drawLine(hl+deviation, ht,hr+deviation, hb, paint);
            // 竖线
            canvas.drawLine(sl+deviation, st,sr+deviation, sb, paint);
        }

        paint.setStrokeWidth(1);

        // 设定字体大小
        paint.setTextSize(step/2);

        // 设置初始化
        for(int i = 0;i < 9;i++) {
            for(int j = 0;j < 9;j++) {

                // 显示用户自己填入数字
                if(user[i][j] != 0) {
                    paint.setColor(choseBackground);         // 橙色
                    drawIndex(i,j,canvas,paint);             // 蓝色背景
                    paint.setColor(textColor);               // 白色
                   // 起点位置默认坐标x 所对应的竖线：为文字左边界
                    canvas.drawText(String.valueOf(user[i][j]),step*(i-0.625f+1)+deviation,step*(j-0.375f+1),paint);
                }

                 // 显示错误数字的背景色及数字
                if(error[i][j] != 0) {
                    paint.setColor(errorBackground);     // 红色
                    drawIndex(i,j,canvas,paint);         // 画背景
                    paint.setColor(choseBackground);     // 橙色
                    canvas.drawText(String.valueOf(error[i][j]),step*(i-0.625f+1)+deviation,step*(j-0.375f+1),paint);
                }

                // 显示点击的区域背景色
                if(click[i][j]!=0){
                    paint.setColor(choseBackground);      // 橙色
                    drawIndex(i,j,canvas,paint);
                    click[i][j] = 0;
                }

                // 显示初始已有的提示数字
                if(number[i][j] != 0) {
                    paint.setColor(blueBackground); // 蓝色
                    drawIndex(i, j, canvas, paint);
                    paint.setColor(textColor);             // 白色
                    canvas.drawText(String.valueOf(number[i][j]), step * (i - 0.625f + 1) + deviation, step * (j - 0.375f + 1), paint);
                }
            }
        }
    }

    /**
     *  根据对应格子坐标 画出背景，及数字
     * @param x
     * @param y
     * @param canvas
     * @param paint
     */
    private void drawIndex(int x,int y,Canvas canvas,Paint paint){
        // +6 和 -6 是为了使背景 与边框保持 6 dp边距
        int left = step*x+6;
        int top = step*y+6;
        int right = step*(x+1)-6;
        int bottom = step*(y+1)-6;
        // 设定方框参数
        RectF rectF = new RectF(left,top ,right ,bottom);
        // 根据参数画出背景方框
        canvas.drawRect(rectF,paint);
    }

    /**
     *  传入初始数组
     */
    public void setNumber(int [][] initData){
        number = initData;
        // 刷新view 视图
        setOriginal();
        invalidate();
    }

    /**
     *  初始数组添加到 最终结果数组
     */
    public void setOriginal( ){
        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                if ( number[i][j] > 0) {
                    finalNumber[i][j]= number[i][j];
                }
            }
        }
    }

    /**
     * 设置新传入的 数字
     * @param clickNum
     */
    public void setNewNumber(int clickNum) {
        // 点击无效区域 ，或超出边界
        if (stateX == -1 || stateY == -1 ){
            return;
        } else {
            // 当前点击区域有效
            user[stateX][stateY] = clickNum;      // 设置用户数组为当前数字
            finalNumber[stateX][stateY] = clickNum;
        }
        click[stateX][stateY] = 0;
        stateX = stateY = -1;
        postInvalidate();  //调用onDraw（）
    }

    /**
     * 清空所有的数据
     */
    public void clear(){
        number = null;
        finalNumber = null;
        error = null;
        click = null;
        user = null;
        user = new int[9][9];
        number = new int[9][9];
        finalNumber = new int[9][9];
        error = new int[9][9];
        click = new int[9][9];
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:  // 按下
                touchX = event.getX();
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_UP:    // 松开
                float x = Math.abs(touchX - event.getX());
                float y = Math.abs(touchY - event.getY());

                if( x <= 20 && y <= 20 && touchX > deviation){
                    stateX = Integer.valueOf(decimalFormat.format(Math.floor((touchX-deviation)/step)));
                    stateY = Integer.valueOf(decimalFormat.format(Math.floor((touchY)/step)));    // Math.ceil 向上取整 ，Math.floor () 向下取整
                    touch();
                }
                break;
            case MotionEvent.ACTION_OUTSIDE: // 边界
                break;

        }
        return true;
       //  return super.onTouchEvent(event);  // 拦截该事件
    }

    /** 点击位置判定 */
    public void touch( ){

        if( stateX > 8 || stateY > 8 ){
           // 点击区域已超出边界
            stateX = stateY = -1;
            return;
        } else if (number[stateX][stateY] > 0){
             // 点击已含正确数字区域，无效
            stateX = stateY = -1;
            return;
        } else {
            // 用于点击区域提示
            click [stateX][stateY] = 1;
            // 再次点击。清掉该位置之前的数字记录
            user[stateX][stateY] = 0;
            error[stateX][stateY] = 0;
            finalNumber[stateX][stateY] = 0;
        }
        postInvalidate();
    }

   /** 检查结果 */
    public boolean checkIsRight(){
        boolean result = true;
        if( check())
            result=true;
        else {
//            postInvalidate();
            result=false;
        }
         return result;
    }

    /**
     * 检查结果是否正确
     * @return
     */
    public boolean check(){
        int node;
        boolean isRight = true;
        for(int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                node = finalNumber[i][j];
                // 是否含有空位置
                if (node == 0) {
                    isRight = false;
                }
                // 与同一行的其他数比较，检查横向上是否有相同
                for (int x = i + 1; x < 9; x++) {  //
                    if(node == finalNumber[x][j]){
                        error[x][j] = node;
                        error[i][j] = node;
                        isRight= false;
                    }
                }
                // 与同一列的其他数比较，检查纵向上是否有相同
                for(int y = j+1; y < 9; y++){
                    if(node == finalNumber[i][y]){
                        error[i][y] = node;
                        error[i][j] = node;
                        isRight = false;
                    }
                }
                // 每个 3*3 格子里是否有相同项
                int si = i/3; int sj = j/3;
                for(int x = 0;x < 3;x++){
                    for(int y = 0;y < 3;y++){

                        if((si*3+x) !=i && (sj*3+y) != j ){
                            if (node == finalNumber[si*3+x][sj*3+y]){
                                error[si*3+x][sj*3+y] = node;
                                error[i][j] = node;
                                isRight = false;
                            }
                        }
                    }
                }
            }
        }
        return isRight;
    }

    /** 测量并设置数独界面的宽度,去掉原先多占的部分 */
    @Override
    protected  void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = measureWidth(100, widthMeasureSpec);
        int heightSize = widthSize;
        setMeasuredDimension(widthSize, heightSize);
    }

    /**
     *  测量数独界面占据的宽度 ，由于宽度设为 match_parent ,默认值 defaultSize 无所谓
     */
    private int  measureWidth(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:  // 如果没有指定大小，就设置为默认大小,
                    // 固定尺寸（如100dp）--->EXACTLY
                    mySize = defaultSize;
                    break;
            case MeasureSpec.AT_MOST:  // 如果测量模式是最大取值为size , mwrap_content--->AT_MOST
                   // 我们将大小取最大值,你也可以取其他值
                    mySize = size;
                    break;
            case MeasureSpec.EXACTLY:
                    // 如果是固定的大小，那就不要去改变它 ,match_parent--->EXACTLY
                    mySize = size;
                    break;
        }
        return mySize;
    }

}
