package com.stephen.thenext.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.stephen.thenext.R;
import com.stephen.thenext.fragment.ListFragment;
import com.stephen.thenext.fragment.RotateFragment;

import cn.waps.AppConnect;

public class MainActivity extends FragmentActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "Stephen";

    private Button settingbtn, infobtn, playbtn, leftbtn, rightbtn, loopbtn, listbtn;
    private TextView currentMusicName, currentMusicDur, totalMusicDur;
    private SeekBar seekBar;
    private RotateFragment rotateFragment;
    private ListFragment listFragment;

    private static final String APP_ID = "294ee87c3a15e377423307a747fe9476";
    private static final String APP_PID = "default";
    private boolean isRotatoFragShowing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppConnect.getInstance(APP_ID, APP_PID, this);
        LinearLayout adlayout = (LinearLayout) findViewById(R.id.AdLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, adlayout);
        AppConnect.getInstance(this).setAdBackColor(Color.argb(50, 120, 240, 120));
        AppConnect.getInstance(this).setAdForeColor(Color.BLACK);
        LinearLayout miniLayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);
        AppConnect.getInstance(this).showMiniAd(this, miniLayout, 10);

        initFragments();
        initViews();
        SharedPreferences preferences = this.getSharedPreferences("save", MODE_PRIVATE);
        int test = preferences.getInt(listFragment.CURRENTPOS, 500);
        listFragment.currentPos = test;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = this.getSharedPreferences("save", MODE_PRIVATE);
        sharedPreferences.edit().putInt(listFragment.CURRENTPOS, listFragment.currentPos).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConnect.getInstance(this).close();
    }


    private void initFragments() {
        rotateFragment = new RotateFragment();
        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_ll, rotateFragment)
                .add(R.id.fragment_ll, listFragment)
                .hide(listFragment)
                .show(rotateFragment)
                .commit();
    }

    private void initViews() {
        settingbtn = (Button) findViewById(R.id.settingbtn);
        infobtn = (Button) findViewById(R.id.infobtn);
        playbtn = (Button) findViewById(R.id.play_pause_btn);
        leftbtn = (Button) findViewById(R.id.left_btn);
        rightbtn = (Button) findViewById(R.id.right_btn);
        loopbtn = (Button) findViewById(R.id.loopbtn);
        listbtn = (Button) findViewById(R.id.list_btn);
        currentMusicName = (TextView) findViewById(R.id.currentPlay);
        currentMusicDur = (TextView) findViewById(R.id.currentTime_tv);
        totalMusicDur = (TextView) findViewById(R.id.totalTime_tv);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);
        settingbtn.setOnClickListener(this);
        infobtn.setOnClickListener(this);
        playbtn.setOnClickListener(this);
        leftbtn.setOnClickListener(this);
        rightbtn.setOnClickListener(this);
        loopbtn.setOnClickListener(this);
        listbtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.list_btn:
                switchFragment();
                break;
            case R.id.play_pause_btn:
                rotateFragment.startRotate();
                break;
            case R.id.settingbtn:
                rotateFragment.stopRotate();
                break;
            case R.id.right_btn:
                listFragment.refreshCheckedItem(listFragment.currentPos + 1);
                refreshTextViews();
                break;
            case R.id.left_btn:
                listFragment.refreshCheckedItem(listFragment.currentPos - 1);
                refreshTextViews();
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void switchFragment() {

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fragment_alpha_in, R.anim.fragment_alpha_out);
        if (isRotatoFragShowing) {
            getSupportFragmentManager().beginTransaction().show(listFragment).hide(rotateFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(rotateFragment).hide(listFragment).commit();
        }
        isRotatoFragShowing = !isRotatoFragShowing;
    }

    private void refreshTextViews() {
        currentMusicName.setText(listFragment.beanLists.get(listFragment.currentPos).getName());
    }

}
