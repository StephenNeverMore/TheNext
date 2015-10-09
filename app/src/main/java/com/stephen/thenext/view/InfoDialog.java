package com.stephen.thenext.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.stephen.thenext.R;


/**
 * Created by Stephen on 2015/9/30.
 */
public class InfoDialog extends Dialog {

    private View.OnClickListener mListener;
    private static int default_width = 160;
    private static int default_height = 120;

    public InfoDialog(Context context, View layout, int style) {
        this(context, null, default_width, default_height, layout, style);
    }

    public InfoDialog(Context context, View.OnClickListener listener, int mWidth, int mHeight, View layout, int style) {
        super(context, style);
        this.mListener = listener;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout);
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = mWidth;
        params.height = mHeight;
        window.setAttributes(params);
        getWindow().findViewById(R.id.negativeButton).setOnClickListener(listener);
        getWindow().findViewById(R.id.positiveButton).setOnClickListener(listener);
    }
}
