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

        View view;
        if(convertView == null){
            // 使用LayoutInfater为子项加载传入的布局
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        } else {
            view = convertView;
        }

        TextView list_Rank=(TextView) view.findViewById(R.id.list_rank);
        TextView list_Time=(TextView)  view.findViewById(R.id.list_time);

        int myTime = getItem(position).getTime();
        list_Time.setText( Util.ScoreFormat( myTime));
        list_Rank.setText( ""+ (position+1) );

        return view;
    }

}
