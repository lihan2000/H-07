package com.bytedance.videoplayer;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import android.support.annotation.Nullable;
import com.bumptech.glide.Glide;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;
    private static final String TAG = "mVideo";
    private static RelativeLayout.LayoutParams originLayoutParams;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * created by lihan
         * 2020/5/6
         * seekbar(X)
         * mediacontroller
         * 触摸视频显示控制条
         *
         */
        if (savedInstanceState!=null){
            //屏幕旋转时，继续从旋转前的位置播放
            int current=savedInstanceState.getInt("process");
            videoView.seekTo(current);
        }

        setTitle("VideoView");
        videoView = (VideoView) findViewById(R.id.videoView);
        originLayoutParams = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        //进度条
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.show(10000);

        videoView.setVideoPath(getVideoPath(R.raw.bytedance));
        videoView.start();



    }


    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }


    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("process",videoView.getCurrentPosition());
    }

    /**
     * created by lihan
     * 2020/5/7
     * 屏幕旋转，横屏时全屏
     * 注册接收屏幕旋转事件 在AndroidManifest.xml文件中的configChanges中添加”orientation|screenSize”
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        Log.i(TAG,"ChangeOrientation");
        int orientation = newConfig.orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            videoView.setLayoutParams(originLayoutParams);
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            RelativeLayout.LayoutParams newLayoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            newLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            newLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            newLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            newLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            videoView.setLayoutParams(newLayoutParams);

        }
    }




}

