package com.example.zmj;
/**create by hansong in 2020-6-30
 * 启动引导界面，用于预先从服务器加载数据，用于绘制主界面ui*/

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GuideACtivity extends AppCompatActivity {
    private String banner;
    private String directory;
    final String TAG = "GUIDE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_activity);
        ////////////从网络上解析数据，用于主界面的绘制/////////////
        /**本线程用于从服务器解析banner的数据*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                banner = getInfo("http://106.13.35.183/banner.txt");
                Log.e(TAG, "onCreate: "+directory);
            }
        }).start();


        /**本线程用于从服务器解析所有字幕的目录的数据*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                directory = getInfo("http://106.13.35.183/directory.txt");
            }
        }).start();

        /**本线程用于在2.5秒后启动主线程,并把目录和banner的标题传到主界面*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(GuideACtivity.this,MainActivity.class);
                intent.putExtra("banner",banner);
                intent.putExtra("directory",directory);
                Log.e(TAG, directory);

                startActivity(intent);
            }
        }).start();

    }





    //本方法获取一个字符串类型的url网址，以一个字符串的形式返回网址里面存储的字符。
    public static String getInfo(String url){

        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL Url = new URL(url);
            connection = (HttpURLConnection) Url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            //下面对获取到的输入流进行读取
            reader = new BufferedReader(new InputStreamReader(in));
            String line = " \n \n ";
            while ((line = reader.readLine()) != null) {
                response.append(line+"\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if (connection!=null){
                connection.disconnect();
            }
            connection.disconnect();
        }

        return response.toString();
    }

    @Override
    protected void onStop() {
        //当本活动不可见的时候,将其结束
        super.onStop();
        finish();
    }


}
