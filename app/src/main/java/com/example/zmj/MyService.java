package com.example.zmj;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyService extends Service {


    private static final String TAG = "MyServise";
    int firstTime;
    final int openSubtitle = 1;
    final int starMessage = 2;
    final int stopMessage = 3;
    final int refreshedit = 4;
    final int refreshSubtitle = 5;
    final int movefocus = 65;

    private AudioManager audioManager;
    private String url;
    private String name;
    private String subtitle;
    int last = 0;

    private Date date = new Date();
    private long start;

    private Map<String, String> wordList;
    private ArrayList<srt> srtList;
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private AssetManager am;
    public static Handler handler;


    private final int openFloat = 10203;
    private final int deliverUrl = 10204;
    private final int deliverName = 10205;

    View popop;//
    View subtitleWindow;
    TextView subtitleText;
    TextView hint1;
    TextView hint2;
    EditText skiptotime;
    Button addsec;
    Button subsec;
    Button shield;
    TextView bottom1;



    WindowManager windowManager;

    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
            PixelFormat.TRANSPARENT);

    WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
            PixelFormat.TRANSPARENT);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        showIndicateWindow();
        initWordList();

        return super.onStartCommand(intent, flags, startId);
    }



    private void initGadget() {

        TextView t = popop.findViewById(R.id.AnimentName);
        t.setText("请打开\n" + name);
        t.setMovementMethod(LinkMovementMethod.getInstance());
        t.setOnTouchListener(new FloatingListener());

        subtitleText = subtitleWindow.findViewById(R.id.subtitle);
        subtitleText.setMovementMethod(LinkMovementMethod.getInstance());
        subtitleText.setOnTouchListener(new FloatingListener2());

        skiptotime = subtitleWindow.findViewById(R.id.skiptotime);
        addsec = subtitleWindow.findViewById(R.id.addsecond);
        subsec = subtitleWindow.findViewById(R.id.subsecond);
        shield = subtitleWindow.findViewById(R.id.shield);
        bottom1 = subtitleWindow.findViewById(R.id.bottom1);
        hint1 = subtitleWindow.findViewById(R.id.hint1);
        hint2 = subtitleWindow.findViewById(R.id.hint2);
        hint1.setVisibility(View.GONE);


        addsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start -=500;
            }
        });
        subsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start+=500;
            }
        });

        skiptotime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (skiptotime.hasFocus())skiptotime.setText("");
            }
        });

        skiptotime.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_DONE){
                    String time = skiptotime.getText().toString();
                    //输入的时间是多少毫秒
                    long mile = 0;
                    try {
                        if (isdigit(time)){
                            if (time.length()<3)
                                mile = Long.parseLong(time)*1000;
                            else mile = Long.parseLong(time.substring(0,time.length()-2))*60000+Long.parseLong(time.substring(time.length()-2,time.length()))*1000;
                        }else {
                            String[] HourMinute = time.split(":");
                            mile = Long.parseLong(HourMinute[0])*60000+Long.parseLong(HourMinute[0])*1000;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    start -=((mile)-(stopmile-start));

                }

                return false;
            }
        });

        shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottom1.getVisibility() == View.VISIBLE){
                    bottom1.setVisibility(View.GONE);
                }else {
                    bottom1.setVisibility(View.VISIBLE);
                }
            }
        });




        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!audioManager.isMusicActive()) {
                    try {
                        Thread.sleep(60);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                handler.sendEmptyMessage(openSubtitle);
            }
        }).start();

    }




    private void initWordList(){
        wordList = new HashMap<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String line = new String();
                try {
                    am = getAssets();
                    inputStream = am.open("word.txt");
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((line = bufferedReader.readLine()) != null) {
                        String word[] = line.split("-");
                        wordList.put(word[0], word[1]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //打开
    private void showIndicateWindow(){

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        popop = layoutInflater.inflate(R.layout.floating_window, null);
        subtitleWindow = layoutInflater.inflate(R.layout.subtitle, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.CENTER | Gravity.RIGHT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowManager.addView(popop, layoutParams);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams2.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams2.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        layoutParams2.format = PixelFormat.RGBA_8888;
        layoutParams2.gravity = Gravity.CENTER | Gravity.RIGHT;
        layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;


        windowManager.addView(subtitleWindow, layoutParams2);

        subtitleWindow.setVisibility(View.INVISIBLE);
        popop.setVisibility(View.INVISIBLE);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case openSubtitle://打开字幕播放窗口
                        popop.setVisibility(View.INVISIBLE);
                        subtitleWindow.setVisibility(View.VISIBLE);
                        windowManager.updateViewLayout(subtitleWindow, layoutParams2);
                        makeSrtlist();
                        showSubtitleText();
                        Toast.makeText(MyService.this,"稍等片刻，首行字幕开始于"+firstTime/60000+"分"+(firstTime%60000)/1000+"秒",Toast.LENGTH_LONG).show();
                        break;

                    case openFloat:
                        popop.setVisibility(View.VISIBLE);
                        subtitleWindow.setVisibility(View.INVISIBLE);
                        initGadget();

                    case starMessage:
                        handler.sendEmptyMessage(movefocus);
                        layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                        windowManager.updateViewLayout(subtitleWindow,layoutParams2);
                        hint1.setVisibility(View.GONE);
                        hint2.setVisibility(View.VISIBLE);
                        addsec.setVisibility(View.GONE);
                        subsec.setVisibility(View.GONE);
//                        skiptotime.setVisibility(View.GONE);
                        shield.setVisibility(View.GONE);
                        if (Build.VERSION.SDK_INT>15)subtitleText.setBackground(subtitleText.getResources().getDrawable(R.color.colorTransparent));
                        break;

                    case stopMessage:
                        hint1.setVisibility(View.VISIBLE);
                        hint2.setVisibility(View.GONE);
                        addsec.setVisibility(View.VISIBLE);
                        subsec.setVisibility(View.VISIBLE);
                        skiptotime.setVisibility(View.VISIBLE);
                        shield.setVisibility(View.VISIBLE);
                        skiptotime.setTextColor(Color.RED);
                        layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                        windowManager.updateViewLayout(subtitleWindow,layoutParams2);
                        if (Build.VERSION.SDK_INT>15)subtitleText.setBackground(subtitleText.getResources().getDrawable(R.color.colorWHITE2));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    last ++;
                                    int a = last;
                                        Thread.sleep(8000);

                                        if (last == a)
                                        handler.sendEmptyMessage(movefocus);

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        break;

                    case refreshedit:
                        skiptotime.setText((String)msg.obj);
//                        windowManager.updateViewLayout(subtitleWindow,layoutParams2);
                        break;

                    case movefocus:
                        hint2.setVisibility(View.VISIBLE);
                        skiptotime.setTextColor(Color.BLACK);
                        hint1.setVisibility(View.GONE);
                        subtitleWindow.clearFocus();
                        layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                        windowManager.updateViewLayout(subtitleWindow,layoutParams2);
                        break;

                    case refreshSubtitle:
                        SpannableStringBuilder spannableStringBuilder = ChangeToSpanableString.change((String)msg.obj,MyService.this,wordList);
                        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(subtitleText.getResources().getColor(R.color.colorWHITE2));
                        spannableStringBuilder.setSpan(backgroundColorSpan,0,spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(subtitleText.getResources().getColor(R.color.colorBLACK));
                        spannableStringBuilder.setSpan(foregroundColorSpan,0,spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        subtitleText.setText(spannableStringBuilder);
                        break;

                    case deliverUrl:
                        url = (String)msg.obj;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                subtitle = http.getInfo(url);
                            }
                        }).start();
                        break;

                    case deliverName:
                        name = (String)msg.obj;
                        break;
                }
            }
        };
    }


    public void makeSrtlist() {
        srtList = new ArrayList<>();

        String[] sentences = subtitle.split("\n");
        firstTime  = 99999999;
        for (int i = 0; i < sentences.length; i++) {
            if (i % 4 == 1) {
                try {
                    String timeTotime = sentences[i];
                    int begin_hour = Integer.parseInt(timeTotime.substring(0, 2));
                    int begin_mintue = Integer.parseInt(timeTotime.substring(3, 5));
                    int begin_scend = Integer.parseInt(timeTotime.substring(6, 8));
                    int begin_milli = Integer.parseInt(timeTotime.substring(9, 12));
                    //--------------------------------------------------------
                    int beginTime = (begin_hour * 3600 + begin_mintue * 60 + begin_scend)
                            * 1000 + begin_milli;
                    int end_hour = Integer.parseInt(timeTotime.substring(17, 19));
                    int end_mintue = Integer.parseInt(timeTotime.substring(20, 22));
                    int end_scend = Integer.parseInt(timeTotime.substring(23, 25));
                    int end_milli = Integer.parseInt(timeTotime.substring(26, 29));
                    //--------------------------------------------
                    int endTime = (end_hour * 3600 + end_mintue * 60 + end_scend)
                            * 1000 + end_milli;

                    if (sentences[i+1].length()>1)
                    srtList.add(new srt(sentences[i + 1], beginTime, endTime));

                    if (beginTime<firstTime&&sentences[i + 1].length()>3){ //最早记录开始时间
                        firstTime = beginTime;
                    }
                } catch (Exception e) {
//                    Toast.makeText(MyService.this,String.valueOf(i),Toast.LENGTH_SHORT).show();
                    continue;
                }
            }
        }

    }

    private boolean canwrite = true;
    long stopmile;
    //显示歌词
    private void showSubtitleText(){

        Log.e(TAG, "showSubtitle");
        new Thread(new Runnable(){
            @Override
            public void run(){
                start = System.currentTimeMillis();
                while (true){
                    long passTime=System.currentTimeMillis()-start;//已经看过多长时间了，（毫秒）
                    for (int i1 = 0; i1 < srtList.size(); i1++) {
                        if ((passTime>srtList.get(i1).getStartTime())&&(passTime<srtList.get(i1).getEndTime())){
                            if (canwrite){
                                Message message = handler.obtainMessage();
                                message.what = refreshSubtitle;
                                message.obj=srtList.get(i1).getBody();
                                handler.sendMessage(message);
                            }
                            break;
                        }


                        if (i1==srtList.size()-1){//如果没有对应的时间的字幕，则清空
                            if (canwrite){
                                Message message = handler.obtainMessage();
                                message.what = refreshSubtitle;
                                message.obj="";
                                handler.sendMessage(message);
                            }
                        }
                    }

                    if (canwrite){//刷新编辑框
                        Message message = handler.obtainMessage();
                        message.what = refreshedit;
                        String sec = "";
                        if ((passTime/1000)%60<10){
                            sec = "0" + (passTime/1000)%60;
                        }

                        else {
                            sec = "" + (passTime/1000)%60;
                        }
                        message.obj=""+(passTime/1000)/60 +":"+ sec;
                        handler.sendMessage(message);
                    }

                    if(!audioManager.isMusicActive()){//暂停时执行以下操作；
                        if(canwrite == true){
                            canwrite = false;
                            stopmile = System.currentTimeMillis();
                            handler.sendEmptyMessage(stopMessage);
                        }

                    }else {
                        if (canwrite == false){//开始时执行以下操作
                            canwrite = true;
                            start+=System.currentTimeMillis()-stopmile+60;
                            handler.sendEmptyMessage(starMessage);
                        }
                    }
                    try {
                        Thread.sleep(60);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    int i = 0;
    private int mStopTouchY;

    private class FloatingListener implements View.OnTouchListener {

        private int mTouchStartX, mTouchStartY, mTouchCurrentX, mTouchCurrentY;
        //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
        private int mStartX, mStartY, mStopX, mStopY;
        private boolean isMove = true;//判断悬浮窗是否移动

        @Override
        public boolean onTouch(View arg0, MotionEvent event ){
            int action = event.getAction();
            switch (action){
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


    private class FloatingListener2 implements View.OnTouchListener {

        private int mTouchStartX, mTouchStartY, mTouchCurrentX, mTouchCurrentY;
        //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
        private int mStartX, mStartY, mStopX, mStopY;
        private boolean isMove = true;//判断悬浮窗是否移动

        @Override
        public boolean onTouch(View arg0, MotionEvent event){
            int action = event.getAction();
            switch (action){
                case MotionEvent.ACTION_DOWN:
                    isMove = false;
                    mTouchStartY = (int) event.getRawY();
                    mStartY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mTouchCurrentY = (int) event.getRawY();
                    layoutParams2.y += mTouchCurrentY - mTouchStartY;
                    windowManager.updateViewLayout(subtitleWindow, layoutParams2);
                    i++;
                    mTouchStartY = mTouchCurrentY;
                    break;
                case MotionEvent.ACTION_UP:
                    mStopY = (int) event.getY();
                    mStopTouchY = (int) event.getRawY();
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
    public void onDestroy(){
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean isdigit(String str) {
        for (int i1 = 0; i1 < str.length(); i1++) {
            if (!Character.isDigit(str.charAt(i1))){
                return false;
            }
        }
        return true;
    }

}
