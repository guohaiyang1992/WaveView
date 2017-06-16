package com.ghy.wave.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by guohaiyang on 2017/6/16.
 */

public class DisplayUtils {
    private static WindowManager wm;

    public static void init(Context context) {
        wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
    }


    public static int getWidth() {
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getHeight() {
        return wm.getDefaultDisplay().getHeight();
    }
}
