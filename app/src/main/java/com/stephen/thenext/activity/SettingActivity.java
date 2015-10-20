package com.stephen.thenext.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.stephen.thenext.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Stephen on 2015/9/30.
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    private int[] res = new int[]{R.id.setting_btn_a, R.id.setting_btn_b,
            R.id.setting_btn_c, R.id.setting_btn_d, R.id.setting_btn_e, R.id.setting_btn_f};
    private List<ImageView> imageViews = new ArrayList<>();
    private Button titleBtn;
    private Button cancleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_layout);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initViews() {
        titleBtn = (Button) findViewById(R.id.setting_title_btn);
        cancleBtn = (Button) findViewById(R.id.setting_cancel_btn);
        cancleBtn.setOnClickListener(this);

        for (int i = 0; i < res.length; i++) {
            final ImageView imageView = (ImageView) findViewById(res[i]);
            imageView.setOnClickListener(this);
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationY", -1000, 0);
            animator.setDuration(600);
            animator.setStartDelay(25 * i);
            animator.setInterpolator(new AnticipateOvershootInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    imageView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    titleBtn.setVisibility(View.VISIBLE);
                    cancleBtn.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();
            imageViews.add(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_cancel_btn:
                startAnimator(0);
                break;
            case R.id.setting_btn_c:
                startAnimator(15);
                break;
            case R.id.setting_btn_a:
                startAnimator(30);
                break;
            case R.id.setting_btn_b:
                startAnimator(45);
                break;
            case R.id.setting_btn_f:
                startAnimator(60);
                break;
            case R.id.setting_btn_d:
                startAnimator(90);
                break;
            case R.id.setting_btn_e:
                startAnimator(120);
                break;
        }
    }

    private void startAnimator(final int result) {
        for (int i = res.length; i > 0; i--) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViews.get(i - 1), "translationY", 0, -1000);
            animator.setDuration(600);
            animator.setStartDelay(25 * i);
            animator.setInterpolator(new AnticipateInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    titleBtn.setVisibility(View.INVISIBLE);
                    cancleBtn.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Intent intent = new Intent();
                    intent.putExtra("com.stephen.thenext.result", result * 1000 * 60);
//                    intent.putExtra("com.stephen.thenext.result", result * 1000);
                    setResult(888, intent);
                    finish();
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return super.onKeyDown(keyCode, event);
    }
}
