package com.example.zmj;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyService extends Service {

    private AudioManager audioManager;
    private String url;
    private String name;
    private String subtitle;

    View popop;
    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
            PixelFormat.TRANSPARENT);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra("url");
        name = intent.getStringExtra("name");
        subtitle = GuideACtivity.getInfo(url);
        showFloatingWindow();
        audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);


        return super.onStartCommand(intent, flags, startId);
    }



    private void showFloatingWindow(){
        windowManager= (WindowManager)getSystemService(WINDOW_SERVICE);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        popop = layoutInflater.inflate(R.layout.floating_window,null);
        TextView t = popop.findViewById(R.id.AnimentName);
        t.setText("请打开\n"+name);
        t.setMovementMethod(LinkMovementMethod.getInstance());
        t.setOnTouchListener(new FloatingListener());

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyService.this,""+audioManager.isMusicActive(),Toast.LENGTH_SHORT).show();
            }
        });
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.CENTER|Gravity.RIGHT;
        layoutParams.type=WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowManager.addView(popop,layoutParams);
    }

    int i = 0;
    private int mStopTouchY;
    private class FloatingListener implements View.OnTouchListener {

        private int mTouchStartX, mTouchStartY, mTouchCurrentX, mTouchCurrentY;
        //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
        private int mStartX, mStartY, mStopX, mStopY;
        private boolean isMove = true;//判断悬浮窗是否移动

        @Override
        public boolean onTouch(View arg0, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    isMove = false;
                    mTouchStartY = (int) event.getRawY();
                    mStartY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mTouchCurrentY = (int) event.getRawY();
                    layoutParams.y += mTouchCurrentY - mTouchStartY;
                    windowManager.updateViewLayout(popop, layoutParams);
                    i++;
                    mTouchStartY = mTouchCurrentY;
                    break;
                case MotionEvent.ACTION_UP:
                    mStopY = (int) event.getY();
                    mStopTouchY = (int) event.getRawY();
//                    Log.d(TAG, String.valueOf(i));
                    if (i > 3) {
                        isMove = true;
                    }
                    i = 0;
                    break;
            }
            return isMove;  //此处必须返回false，否则OnClickListener获取不到监听
        }

    }


    @Override
    public void onDestroy() {
        windowManager.removeView(popop);
        super.onDestroy();
    }

    public MyService() {
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
