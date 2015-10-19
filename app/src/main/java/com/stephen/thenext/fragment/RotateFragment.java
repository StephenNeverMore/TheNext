package com.stephen.thenext.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.stephen.thenext.R;


/**
 * Created by ZS on 2015-08-22.
 */
public class RotateFragment extends Fragment {

    private ImageView rotateBtn;
    private ObjectAnimator animator;
    private boolean flag = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rotate_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rotateBtn = (ImageView) getActivity().findViewById(R.id.rotatebtn);
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    rotateBtn.setImageResource(R.drawable.yue2);
                } else {
                    rotateBtn.setImageResource(R.drawable.yue1);
                }
                flag = !flag;
            }
        });
        animator = ObjectAnimator.ofFloat(rotateBtn, "rotation", 0, 360);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(3600);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
    }

    public void startRotate() {
        if (animator.isRunning()) {
            return;
        }
        animator.start();
    }

    public void stopRotate() {
        if (!animator.isRunning()) {
            return;
        }
        animator.cancel();
    }


}
