package com.studyboy.sudoku.listdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.studyboy.shudu2.R;

import java.util.List;

/**
 * 自定义适配器
 */
public class MyAdapter extends ArrayAdapter<MyScore> {

    private int resourceId;

    /**
     * 重写构造器
     * @param context
     * @param textViewResourceId
     * @param objects
     */
    public MyAdapter(Context context, int textViewResourceId, List<MyScore> objects){

        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    /**
     * 自定义 item 资源的解析方式
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent ){
        MyScore myScore =getItem(position);

        // 使用LayoutInfater为子项加载传入的布局
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView list_Rank=(TextView) view.findViewById(R.id.list_rank);
        TextView list_Time=(TextView)  view.findViewById(R.id.list_time);
        list_Rank.setText(myScore.getRank()+" ");
        setTimeText(list_Time,myScore.getTime());
//        list_Time.setText(myScore.getTime()/60+"分 "+myScore.getTime()%60+"秒");
        return view;
    }

    /**
     * 时间记录显示设置
     * @param textView
     * @param time
     */
    public void setTimeText(TextView textView,int time){
         int hh = time/3600;
         int mm = time %3600 /60;
         int ss = time %60;
         String str = null;
         if(hh > 0){
             str = String.format("%d小时 %d分 %d秒",hh,mm,ss);
         }
         else if(mm > 0){
             str = String.format(" %d分 %d秒",mm,ss);
         }
         else{
             str = String.format(" %d秒",ss);
         }
        textView.setText(str);
    }

}
