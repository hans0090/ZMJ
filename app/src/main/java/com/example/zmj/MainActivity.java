    package com.example.zmj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.slice.Slice;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.ServiceConfigurationError;

    public class MainActivity extends AppCompatActivity {

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

}
