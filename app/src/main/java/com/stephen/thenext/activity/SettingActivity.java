package com.stephen.thenext.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

import com.stephen.thenext.R;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by Stephen on 2015/9/30.
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    private int[] res = new int[]{R.id.setting_cancel_btn, R.id.setting_btn_a, R.id.setting_btn_b,
            R.id.setting_btn_c, R.id.setting_btn_d, R.id.setting_btn_e, R.id.setting_btn_f};
    private List<Button> buttons = new ArrayList<>();

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

        for (int i = 0; i < res.length; i++) {
            final Button button = (Button) findViewById(res[i]);
            button.setOnClickListener(this);
            ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationY", -1000, 0);
            animator.setDuration(600);
            animator.setStartDelay(20 * i);
            animator.setInterpolator(new AnticipateOvershootInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    button.setVisibility(View.VISIBLE);
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
            buttons.add(button);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_cancel_btn:
                startAnimator(0);
                break;
            case R.id.setting_btn_a:
                startAnimator(0);
                break;
            case R.id.setting_btn_b:
                startAnimator(0);
                break;
            case R.id.setting_btn_c:
                startAnimator(0);
                break;
            case R.id.setting_btn_d:
                startAnimator(0);
                break;
            case R.id.setting_btn_e:
                startAnimator(0);
                break;
            case R.id.setting_btn_f:
                startAnimator(0);
                break;
        }
    }

    private void startAnimator(final int result) {
        for (int i = res.length; i > 0; i--) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(buttons.get(i - 1), "translationY", 0, -1000);
            animator.setDuration(600);
            animator.setStartDelay(20 * i);
            animator.setInterpolator(new AnticipateInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Intent intent = new Intent();
                    intent.putExtra("result", result);
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

}
