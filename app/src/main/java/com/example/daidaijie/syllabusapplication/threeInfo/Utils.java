package com.example.daidaijie.syllabusapplication.threeInfo;

import android.content.Context;

/**
 * Created by 16zhchen on 2018/10/21.
 */

public class Utils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }
}
