package com.example.zmj;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class http {
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
            return "bad connection";
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
}
