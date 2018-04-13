package com.myself.myrepository.widght;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

import java.lang.reflect.Field;

/**
 * @Created by Jams
 * @Created time 2018-4-13 14:42
 * @dese todo
 * \n
 * @UpUser by Administrator
 * @UpDate 2018-4-13 14:42
 * @dese todo
 */

public class DisplayUtil {
    // 获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        if (statusBarHeight <= 0) {
            Rect frame = new Rect();
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        }
        if (statusBarHeight <= 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
