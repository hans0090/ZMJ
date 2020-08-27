package com.example.zmj;
/**create by hansong in 2020-6-30
 * 启动引导界面，用于预先从服务器加载数据，用于绘制主界面ui*/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class GuideACtivity extends AppCompatActivity {
    private String banner;
    private String directory;
    private Handler handler;
    private final int failConnect = 1;
    final String TAG = "GUIDE";
    int count = 0;
    private TextView GuideText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_activity);
        ////////////从网络上解析数据，用于主界面的绘制/////////////
        /**本线程用于从服务器解析banner的数据*/

        GuideText = (TextView) findViewById(R.id.guide);
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case failConnect:
                        Toast.makeText(GuideACtivity.this,"服务器连接失败，请检查网络连接，并确认是否为最新版本",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };


        /**本线程用于从服务器解析所有字幕的目录的数据*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                directory = http.getInfo(GuideACtivity.this.getString(R.string.url)+"directory.txt");
                banner = http.getInfo(GuideACtivity.this.getString(R.string.url)+"banner.txt");
                //如果服务器连接失败的话，执行如下操作
                while (directory.equals("bad connection")||banner.equals("bad connection")){
                    try {
                        Thread.sleep(300);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    count++;
                    if (count==10){
                        handler.sendEmptyMessage(failConnect);
                    }
                    if (banner.equals("bad connection"))banner = http.getInfo(GuideACtivity.this.getString(R.string.url)+"banner.txt");
                    if (directory.equals("bad connection"))directory = http.getInfo(GuideACtivity.this.getString(R.string.url)+"directory.txt");
                }
                Intent intent = new Intent(GuideACtivity.this,MainActivity.class);
                intent.putExtra("banner",banner);
                intent.putExtra("directory",directory);
                startActivity(intent);
            }
        }).start();


    }

    @Override
    protected void onStop() {
        //当本活动不可见的时候,将其结束
        super.onStop();
        finish();
    }


}
