package com.stephen.thenext.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.stephen.thenext.R;
import com.stephen.thenext.fragment.ListFragment;
import com.stephen.thenext.fragment.RotateFragment;
import com.stephen.thenext.polly.Bean;
import com.stephen.thenext.utils.ToastUtils;
import com.stephen.thenext.view.InfoDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.ConversationActivity;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.umeng.message.PushAgent;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import junit.framework.Test;

import java.text.SimpleDateFormat;

import cn.waps.AppConnect;
import de.greenrobot.event.EventBus;

public class MainActivity extends FragmentActivity implements
        View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {


    private Button settingbtn;
    private Button infobtn;
    private Button playbtn;
    private Button leftbtn;
    private Button rightbtn;
    private Button loopbtn;
    private Button listbtn;
    private TextView currentMusicName;
    private TextView currentMusicDur;
    private TextView totalMusicDur;
    private SeekBar seekBar;
    private static RotateFragment rotateFragment;
    private ListFragment listFragment;

    private static final String APP_ID = "294ee87c3a15e377423307a747fe9476";
    private static final String APP_PID = "default";

    //WX & CIRCLE
<<<<<<< HEAD
    String wXappID = "wx7615106e66b2f5fe";
    String wXappSecret = "2e389c0ac8b4f4046319ad72e5a02df7";
=======
    String wXappID = "wxac0158702adba3b5";
    String wXappSecret = "e42a88e5b12b1b534a1e81d1c44937e4";

>>>>>>> 682f331543a56f1efda647b7ee3bb94392c41a96
    //QQ
    String qQappId = "100424468";
    String qQappSecret = "c7394704798a158208a74ab60104f0ba";
    //Q-Zone
    String qZoneId = "100424468";
    String qZoneSecret = "c7394704798a158208a74ab60104f0ba";

    private boolean isRotatoFragShowing = true;

    private MediaPlayer mediaPlayer;
    private boolean isMediaPlaying = false;
    private int mediaCurPos;
    private int mediaTotPos;
    SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    private FeedbackAgent fb;

    private InfoDialog dialog;

    UMSocialService mController;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            currentMusicDur.setText(format.format(mediaCurPos));
            totalMusicDur.setText(format.format(mediaTotPos));
            seekBar.setMax(mediaTotPos);
            seekBar.setProgress(mediaCurPos);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UM analytics
        MobclickAgent.updateOnlineConfig(this);
        //UM feedback
        setUpUmengFeedback();
        //WP ads
        AppConnect.getInstance(APP_ID, APP_PID, this);
        LinearLayout adlayout = (LinearLayout) findViewById(R.id.AdLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, adlayout);
        AppConnect.getInstance(this).setAdBackColor(Color.argb(50, 120, 240, 120));
        AppConnect.getInstance(this).setAdForeColor(Color.BLACK);
        LinearLayout miniLayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);
        AppConnect.getInstance(this).showMiniAd(this, miniLayout, 10);

        initFragments();
        initViews();
        SharedPreferences preferences = this.getSharedPreferences("save", Context.MODE_PRIVATE);
        ListFragment.currentPos = preferences.getInt(ListFragment.CURRENTPOS, 0);

        EventBus.getDefault().register(this);
        mediaPlayer = MediaPlayer.create(MainActivity.this, ListFragment.res[ListFragment.currentPos]);
        //context decide the mediaplayer life
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
        mHandler.post(mRunnable);//seekbar start to work;

        initShare();
    }

    private void initShare() {
        // 首先在您的Activity中添加如下成员变量
        mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
        // 设置分享内容
        mController.setShareContent("咦……你干啥切嘞？咋才来呢？");

        // 设置分享图片, 参数2为图片的url地址
//        mController.setShareMedia(new UMImage(MainActivity.this,
//                "www.baidu.com"));
        // 设置分享图片，参数2为本地图片的资源引用
        //mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
        // 设置分享图片，参数2为本地图片的路径(绝对路径)
        //mController.setShareMedia(new UMImage(getActivity(),
        //BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

        // 设置分享音乐
        //UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
        //uMusic.setAuthor("GuGu");
        //uMusic.setTitle("天籁之音");
        // 设置音乐缩略图
        //uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
        //mController.setShareMedia(uMusic);

        // 设置分享视频
        //UMVideo umVideo = new UMVideo(
        //          "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
        // 设置视频缩略图
        //umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
        //umVideo.setTitle("友盟社会化分享!");
        //mController.setShareMedia(umVideo);

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(MainActivity.this, wXappID, wXappSecret);
        wxHandler.setTitle("this is a test!");
        wxHandler.addToSocialSDK();

        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(MainActivity.this, wXappID, wXappSecret);
        wxCircleHandler.setTitle("岳云鹏相声");
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(MainActivity.this, qQappId,
                qQappSecret);
        qqSsoHandler.addToSocialSDK();
        qqSsoHandler.setTitle("岳云鹏相声");

        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(MainActivity.this, qZoneId,
                qZoneSecret);
        qZoneSsoHandler.addToSocialSDK();

        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        //设置腾讯微博SSO handler
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());


        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent
                .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-微信。http://www.umeng.com/social");
        weixinContent.setTitle("岳云鹏相声");
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia
                .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-朋友圈。http://www.umeng.com/social");
        circleMedia.setTitle("岳云鹏相声");
//        circleMedia.setShareMedia(urlImage);
        // circleMedia.setShareMedia(uMusic);
        // circleMedia.setShareMedia(video);
//        circleMedia.setTargetUrl("http://www.umeng.com/social");
//        mController.setShareMedia(circleMedia           );

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("save", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(ListFragment.CURRENTPOS, ListFragment.currentPos).apply();
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConnect.getInstance(this).close();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mController.getConfig().cleanListeners();
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
        loopbtn = (Button) findViewById(R.id.sharebtn);
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
                updatePlayBtn();
                updateTitle();
                break;
            case R.id.settingbtn:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, 888);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.right_btn:
                playNextMusic();
                updateTitle();
                break;
            case R.id.left_btn:
                playPreviousMusic();
                updateTitle();
                break;
            case R.id.infobtn:
                showInfoDialog();
                break;
            case R.id.sharebtn:

                mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
//                mController.openShare(MainActivity.this, new SocializeListeners.SnsPostListener() {
//                    @Override
//                    public void onStart() {
//                        ToastUtils.showShortToast(MainActivity.this, "onStart");
//                    }
//
//                    @Override
//                    public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
//                        ToastUtils.showShortToast(MainActivity.this, "onComplete");
//                    }
//                });
                mController.openShare(MainActivity.this, false);
                break;
            default:
                break;
        }
    }

    private void showInfoDialog() {
        View view = getLayoutInflater().inflate(R.layout.infodialog_layout, null);
        int dialogWidth = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.7);
        int dialogHeight = (int) (getWindowManager().getDefaultDisplay().getHeight() * 0.3);
        dialog = new InfoDialog(MainActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.positiveButton:
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, ConversationActivity.class);
                        String id = new FeedbackAgent(MainActivity.this).getDefaultConversation().getId();
                        intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID, id);
                        startActivity(intent);
                        break;
                    case R.id.negativeButton:
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        }, dialogWidth, dialogHeight, view, R.style.dialog);

        dialog.show();
    }

    private void playPreviousMusic() {
        if (isMediaPlaying) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            int index = ListFragment.currentPos - 1;
            if (index < 0) {
                index = ListFragment.res.length - 1;
            }
            listFragment.refreshCheckedItem(index);
            mediaPlayer = MediaPlayer.create(MainActivity.this, ListFragment.res[ListFragment.currentPos]);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        }
    }

    private void playNextMusic() {
        if (isMediaPlaying) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            int index = ListFragment.currentPos + 1;
            if (index > ListFragment.res.length - 1) {
                index = 0;
            }
            listFragment.refreshCheckedItem(index);
            mediaPlayer = MediaPlayer.create(MainActivity.this, ListFragment.res[ListFragment.currentPos]);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mediaPlayer.pause();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mediaPlayer.start();
    }

    private void switchFragment() {

        if (isRotatoFragShowing) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fragment_translate_in, R.anim.fragment_translate_out)
                    .show(listFragment).hide(rotateFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fragment_translate_in, R.anim.fragment_translate_out)
                    .show(rotateFragment).hide(listFragment).commit();
        }
        isRotatoFragShowing = !isRotatoFragShowing;
    }

    private void updateTitle() {
        if (isMediaPlaying) {
            currentMusicName.setText("正在播放：" + listFragment.playlists.get(ListFragment.currentPos));
        } else {
            currentMusicName.setText("岳云鹏相声");
        }
    }

    private void updatePlayBtn() {
        if (isMediaPlaying) {
            mediaPlayer.pause();
            rotateFragment.stopRotate();
            playbtn.setBackgroundResource(R.drawable.playbtn_xml);
        } else {
            playbtn.setBackgroundResource(R.drawable.pausebtn_xml);
            mediaPlayer.start();
            rotateFragment.startRotate();
        }
        isMediaPlaying = !isMediaPlaying;
    }

    private void onEventMainThread(Bean bean) {
        updateTitle();
        playbtn.setBackgroundResource(R.drawable.pausebtn_xml);
        rotateFragment.startRotate();
        if (isMediaPlaying) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(MainActivity.this, ListFragment.res[ListFragment.currentPos]);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(this);
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        playNextMusic();
        updateTitle();
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mediaCurPos = mediaPlayer.getCurrentPosition();
            mediaTotPos = mediaPlayer.getDuration();
            mHandler.sendMessage(mHandler.obtainMessage());
            mHandler.postDelayed(mRunnable, 100);
        }
    };

    private void setUpUmengFeedback() {
        fb = new FeedbackAgent(this);
        fb.sync();
        fb.openAudioFeedback();
        fb.openFeedbackPush();
        PushAgent.getInstance(this).enable();
        new Thread(new Runnable() {
            @Override
            public void run() {
                fb.updateUserInfo();
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int time = data.getIntExtra("com.stephen.thenext.result", 0);
        if (time == 0) {
            mHandler.removeCallbacks(runnable);
        } else {
            mHandler.postDelayed(runnable, time);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isMediaPlaying) {
                mediaPlayer.pause();
                rotateFragment.stopRotate();
                playbtn.setBackgroundResource(R.drawable.playbtn_xml);
                currentMusicName.setText("岳云鹏相声");
                isMediaPlaying = !isMediaPlaying;
            }
        }
    };
}
