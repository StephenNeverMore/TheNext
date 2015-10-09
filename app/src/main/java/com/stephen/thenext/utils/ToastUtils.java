package com.stephen.thenext.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by Stephen on 2015/9/30.
 */
public class ToastUtils {

    private static Toast mToast;
    private static Handler handler = new Handler();
    private static Runnable r = new Runnable() {
        @Override
        public void run() {
            mToast.cancel();
            mToast = null;
        }
    };

    public static void showShortToast(Context context, String string) {
        handler.removeCallbacks(r);
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showLongToast(Context context, String string) {
        handler.removeCallbacks(r);
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, string, Toast.LENGTH_LONG);
        mToast.show();
    }

}
