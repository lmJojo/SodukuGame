package com.studyboy.sudoku.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 *  自定义dialog 样式
 */
public class DialogView extends Dialog {

    private Context mContext;
    public DialogView(Context context, int style) {
        super(context, style);

        // 居中显示
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

      }
}
