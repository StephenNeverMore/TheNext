package com.stephen.thenext.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stephen.thenext.R;


public class WelActivity extends Activity {

    private int[] res = new int[]{R.id.peng_tv, R.id.yun_tv, R.id.xiang_tv, R.id.yue_tv, R.id.sheng_tv};
//    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wel);
        intiViews();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.scale_in, R.anim.alpha_out);
            }
        }, 2500);
    }

    private void intiViews() {
//        relativeLayout = (RelativeLayout) findViewById(R.id.wel_layout);
        for (int i = 0; i < res.length; i++) {
            final TextView textView = (TextView) findViewById(res[i]);
            ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "translationY", -2000, 0);
            animator.setDuration(800);
            animator.setStartDelay(30 * i);
            animator.setInterpolator(new AnticipateOvershootInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    textView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }
}
