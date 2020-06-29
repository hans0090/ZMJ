    package com.example.zmj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.slice.Slice;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

    public class MainActivity extends AppCompatActivity {

        final String url = "106.13.35.183/";
        private BannerView bannerView;
        ListView SearchList;
        SearchView searchView;
        private ArrayList<String> anims;
        ArrayAdapter<String> adapter;
        RecyclerView recyclerView;
        @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setview();
//
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection connection = null;
                    BufferedReader bufferedReader = null;

                    try {
                        URL url = new URL("http://106.13.35.183/banner.txt");
                        connection = (HttpURLConnection)url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);

                        InputStream is = connection.getInputStream();

                        bufferedReader = new BufferedReader(new InputStreamReader(is));

                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine())!=null){
                            sb.append(line);

                            Log.e("fadf",line);
                        }
                        Log.e("fadf",sb.toString());



                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("fadsf","---------------");
                    }
                }
            }).start();
    }



    private void setview(){
        //初始化搜索框
        searchView=(SearchView)findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当搜索提交的方法
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();//收起软键盘
                searchView.onActionViewCollapsed();//关闭搜索框
                return true;
            }
            //当搜索框被点击的方法
            @Override
            public boolean onQueryTextChange(String s) {
                SearchList.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(s)){
                    adapter.getFilter().filter(s.toString());
//                    SearchList.setFilterText(s);
                }else {
                    adapter.getFilter().filter("");
                    Log.i("Nomad", "onQueryTextChange Empty String");
                    SearchList.clearTextFilter();
                    SearchList.setVisibility(View.GONE);
                }
                return false;
            }
        });

        initAnmis();//把所有字幕名称添加到ArrayList中用于搜索和主界面的初始化

        //初始化搜索列表
        adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.search_item,anims);
        SearchList = (ListView)findViewById(R.id.search_list);
        SearchList.setAdapter(adapter);
        SearchList.setTextFilterEnabled(true);
        SearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,anims.get(i),Toast.LENGTH_SHORT).show();
                SearchList.setVisibility(View.GONE);
                searchView.clearFocus();//收起软键盘
                searchView.onActionViewCollapsed();//关闭搜索框;
            }
        });

        //初始化主界面列表
        recyclerView = (RecyclerView)findViewById(R.id.allAniment);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL);
        final AllAnimentAdapter AnimentAdapter = new AllAnimentAdapter(anims);
        AnimentAdapter.notifyDataSetChanged();
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);//设置方向

//接口回调，使AnimentAdapter的方法可以调用主界面的方法，用于改变recycleView中被选中字幕的颜色
        AnimentAdapter.setGetListener(new AllAnimentAdapter.GetListener() {

            @Override
            public void onClick(int position) {
//                把点击的下标回传给适配器 确定下标
                AnimentAdapter.selectedPosition = position;
                AnimentAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(AnimentAdapter);
        recyclerView.addItemDecoration(new ChatBorder(25));//设置边距
        recyclerView.setLayoutManager(layoutManager);



        //////////////////////////////////////////////////
        bannerView = findViewById(R.id.vp_view_pager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bannerView.setShowLeftAndRightPage(17, true, new GalleryTransformer());
        } else {
            bannerView.setShowLeftAndRightPage(15);
        }

        bannerView.setEntries(getImageBannerEntries());
        bannerView.selectCenterPage(2);
        bannerView.setOnPageClickListener(new BannerView.OnPageClickListener() {
            @Override
            public void onPageClick(BannerEntry entry, int index) {
                Intent intent = new Intent(MainActivity.this,Web.class);
                index++;
                intent.putExtra("url","http://"+url+"banner"+index+"/"+index+".html");
                startActivity(intent);
            }
        });
    }

        //把所有字幕名称添加到ArrayList中用于搜索和主界面的初始化
        private void initAnmis(){
        anims = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            anims.add("afsd八方达卡家乐福");
            anims.add("fad");
            anims.add("发现距离");
            anims.add("发电房");
            anims.add("发空间里看见");
            anims.add("乐扣乐扣");
            anims.add("破哦从");
            anims.add("发到付借款");
        }
    }

        @NonNull
        private List<ImageBannerEntry> getImageBannerEntries() {
            List<ImageBannerEntry> items = new ArrayList<>();
            items.add(new ImageBannerEntry("need", "1", "http://" + url+"banner1/1.png"));
            items.add(new ImageBannerEntry("need2", "2", "http://" + url+"banner2/2.png"));
            items.add(new ImageBannerEntry("need3", "3", "http://" + url+"banner3/3.png"));
            return items;
        }
}
