    package com.example.zmj;
//
    /***create in  2020,6.15 by hans*/
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.StaggeredGridLayoutManager;

    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.media.AudioManager;
    import android.net.Uri;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.Message;
    import android.preference.PreferenceManager;
    import android.provider.Settings;
    import android.text.SpannableString;
    import android.text.SpannableStringBuilder;
    import android.text.Spanned;
    import android.text.TextUtils;
    import android.text.method.LinkMovementMethod;
    import android.text.style.ClickableSpan;
    import android.util.Log;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.ListView;
    import android.widget.SearchView;
    import android.widget.TextView;
    import android.widget.Toast;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.gson.Gson;
    import com.google.gson.reflect.TypeToken;

    import java.util.ArrayList;
    import java.util.List;




    public class MainActivity extends AppCompatActivity {

        String url;
        AudioManager audioManager;
        private BannerView bannerView;
        private ListView SearchList;
        private SearchView searchView;
        private ArrayList<animent> animents;
        private String[] lines = new String[3];//用来记录banner的主标题
        private ArrayAdapter<String> adapter;//搜索列表用的
        private RecyclerView recyclerView;//选是哪部动漫的
        private AllAnimentAdapter AnimentAdapter;//选是哪部动漫的
        private RecyclerView recyclerViewSeason ;//选第几季的
        private RecyclerView recyclerViewEpisode;//选第几集的
        private BottomItemAdapter SeasonAdapter;//选第几季的
        private BottomItemAdapter EpisodeAdapter;//选第几集的
        private TextView information;//
        private TextView detail;
        private TextView contact;
        private TextView protocal;

        private AlertDialog.Builder builder;
        private FloatingActionButton floatingActionButton;
        private final int openFloat = 10203;
        private final int deliverUrl = 10204;
        private final int deliverName = 10205;

        private int animentid;//记录点击的是哪部动漫
        private int seasonid;//记录点击的是第几季
        private int episodeid;//记录点击的是第几集
        public static Intent LastIntent = null;
        private ArrayList<String> filted  = new ArrayList<>();

        @Override
        protected void onStart() {
            super.onStart();




            final SharedPreferences prefe = MainActivity.this.getSharedPreferences(KEYGUARD_SERVICE,0);

            if (!prefe.getBoolean("floating",false)){

                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("开启悬浮窗");
                builder.setMessage("软件需要悬浮窗权限才能正常运行，点击确定前往开启");
                builder.setCancelable(false);


                builder.setPositiveButton("同意",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if ( Build.VERSION.SDK_INT>=23){//申请悬浮窗权限
                            if (!Settings.canDrawOverlays(MainActivity.this)){
                                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
                            }
                        }
                        SharedPreferences.Editor editor = prefe.edit();
                        editor.putBoolean("floating",true);
                        editor.commit();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }


            if (!prefe.getBoolean("aggree",false)){

                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("服务协议和隐私政策");
                builder.setMessage(MainActivity.this.getString(R.string.intro));
                builder.setCancelable(false);


                builder.setPositiveButton("同意",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = prefe.edit();
                        editor.putBoolean("aggree",true);
                        editor.commit();
                    }
                });

                builder.setNeutralButton("查看《用户协议》与《隐私政策》", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this,agreement.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("暂不使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }




        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            Intent intent = new Intent(MainActivity.this,MyService.class);
            startService(intent);

            audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
            initAnmis();//把所有字幕名称添加到ArrayList中用于搜索和主界面的初始化
            setview();//绘制ui
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
            //当搜索框文字变化时的操作，如果没有文字，清空对话框，如果有文字，在ArrayList中展示与输入相匹配的字幕
            @Override
            public boolean onQueryTextChange(String s) {

                SearchList.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(s)){
                    //利用模式匹配算法，筛选与输入相匹配的字幕，利用filter作为容器
                    filted.clear();
                    for (int i = 0; i < animents.size(); i++) {
                        if (animents.get(i).getName().contains(s))filted.add(animents.get(i).getName());
                    }

                    adapter = new ArrayAdapter<>(MainActivity.this,R.layout.search_item,filted);
                    SearchList.setAdapter(adapter);
                }else {
                    Log.i("Nomad", "onQueryTextChange Empty String");
                    SearchList.clearTextFilter();
                    SearchList.setVisibility(View.GONE);
                }
                return false;
            }
        });


        //初始化搜索列表
        SearchList = (ListView)findViewById(R.id.search_list);
        SearchList.setAdapter(adapter);
        SearchList.setTextFilterEnabled(true);
        SearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                setupbottom(filted.get(i));
//                Toast.makeText(MainActivity.this,filted.
//                        get(i),Toast.LENGTH_SHORT).show();
                SearchList.setVisibility(View.GONE);
                searchView.clearFocus();//收起软键盘
                searchView.onActionViewCollapsed();//关闭搜索框;
            }
        });

        //初始化主界面字幕列表
        recyclerView = (RecyclerView)findViewById(R.id.allAniment);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        AnimentAdapter = new AllAnimentAdapter(animents);
        AnimentAdapter.notifyDataSetChanged();
        AnimentAdapter.selectedPosition = 10000;
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);//设置方向

//接口回调，字幕点击的方法 改变item颜色 ，记录被点击的是哪个字母
        AnimentAdapter.setGetListener(new AllAnimentAdapter.GetListener(){
            @Override
            public void onClick(int position) {

                floatingActionButton.setAlpha(0.1f);
//                把点击的下标回传给适配器 确定下标
                AnimentAdapter.selectedPosition = position;
                AnimentAdapter.notifyDataSetChanged();
                //知道点击的是哪部动漫，用动漫的名称做索引，第几季
                setupbottom(animents.get(position).getName());
                animentid =position;//记录被点击的是哪部动漫
                SearchList.setVisibility(View.VISIBLE);
                recyclerViewEpisode.setVisibility(View.INVISIBLE);//把第几集按钮隐藏
            }
        });

        recyclerView.setAdapter(AnimentAdapter);
        recyclerView.addItemDecoration(new ChatBorder(21));//设置边距
        recyclerView.setLayoutManager(layoutManager);



        //////初始化搜索框////////////////////////////
        bannerView = findViewById(R.id.vp_view_pager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bannerView.setShowLeftAndRightPage(17, true, new GalleryTransformer());
        } else {
            bannerView.setShowLeftAndRightPage(15);
        }
        bannerView.setEntries(getImageBannerEntries());
        bannerView.selectCenterPage(1);
        bannerView.setOnPageClickListener(new BannerView.OnPageClickListener() {
            @Override
            public void onPageClick(BannerEntry entry, int index) {
                Intent intent = new Intent(MainActivity.this,Web.class);
                index++;
                intent.putExtra("url",url+"banner"+index+"/"+index+".html");
                startActivity(intent);
            }
        });
//        选中动漫后，底部的两个recycleview.
            recyclerViewSeason =(RecyclerView) findViewById(R.id.selected_season);
            recyclerViewEpisode=(RecyclerView) findViewById(R.id.selected_episode);




            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
            linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
            linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);

            recyclerViewSeason.setLayoutManager(linearLayoutManager1);
            recyclerViewEpisode.setLayoutManager(linearLayoutManager2);
            recyclerViewSeason.addItemDecoration(new ChatBorder(20));
            recyclerViewEpisode.addItemDecoration(new ChatBorder(20));

            information = (TextView)findViewById(R.id.information);
            detail = (TextView)findViewById(R.id.detail);


            contact = (TextView)findViewById(R.id.contact);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("联系我们");
                    builder.setMessage("非常感谢您使用字幕菌，在使用过程中您遇到什么问题，或者有任何意见或者建议，可以联系我\n联系方式\nhs678@foxmail.com");
                    builder.setCancelable(true);

                    builder.setPositiveButton("同意",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            protocal = (TextView)findViewById(R.id.protocal);
            protocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,agreement.class);
                    startActivity(intent);
                }
            });

            floatingActionButton = (FloatingActionButton)findViewById(R.id.start);
            if (Build.VERSION.SDK_INT>22)floatingActionButton.setBackgroundTintList(getColorStateList(R.color.colorRED));
        }

        private void setupbottom(String name){//为底部的两个recycle做适配,通过名字做标识
            ArrayList<String> seasons = new ArrayList<>();
            for (int i = 0; i < animents.size(); i++) {
                if (animents.get(i).getName().equals(name)) {
                    animentid = i;
                    int season = animents.get(i).getSeason();
                    for (int i1 = 0; i1 < season; i1++) {
                        seasons.add("第" + (i1 + 1) + "季");
                    }
                }
            }

            AnimentAdapter.selectedPosition = animentid;
            AnimentAdapter.notifyDataSetChanged();

            information.setText(" "+animents.get(animentid).getName()+"  语言："+animents.get(animentid).getLanguage()+"\n 首映时间: "+animents.get(animentid).getYear()+"    类型："+animents.get(animentid).getType());
            detail.setText(animents.get(animentid).getIntro());
            SeasonAdapter = new BottomItemAdapter(seasons);



                    SeasonAdapter.setGetListener(new BottomItemAdapter.GetListener(){
                        //接口回调，Season的点击事件，
                        @Override
                        public void onClick(int position) {

                            floatingActionButton.setAlpha(0.1f);
                            //点击第几季后出现总共有几集
                            recyclerViewEpisode.setVisibility(View.VISIBLE);
                            seasonid = position;
                            String[] episodes = animents.get(animentid).getEpisode().split("-");
                            int  a =Integer.parseInt(episodes[position]);
                            ArrayList<String> episode = new ArrayList();
                            for (int i1 = 1; i1 <= a; i1++) {
                                episode.add("第"+i1+"集");
                            }
                            EpisodeAdapter = new BottomItemAdapter(episode);
                            EpisodeAdapter.setGetListener(new BottomItemAdapter.GetListener() {
                                @Override
                                public void onClick(int position) {

                                    floatingActionButton.setAlpha(1.0f);
                                    episodeid = position;
                                }
                            });
                            recyclerViewEpisode.setAdapter(EpisodeAdapter);
                        }
                    });
                recyclerViewSeason.setAdapter(SeasonAdapter);

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Opensubtitle();
                    }
                });
            }


        //把所有字幕名称添加到ArrayList中用于搜索和主界面的初始化
        private void initAnmis(){
            url = MainActivity.this.getString(R.string.url);
            Intent intent = getIntent();//从主界面取出加载的banner的 标题。
            String title = intent.getStringExtra("directory");
//            String[] animentString = title.split("\n");//每一行对应一条目录
            Gson gson = new Gson();

            animents = gson.fromJson(intent.getStringExtra("directory"),new TypeToken<ArrayList<animent>>(){}.getType());
//            animents =
//            for (int i = 0; i < animentString.length; i++) {
//                String[] animen = animentString[i].split("#");
//                animents.add(new animent(Integer.parseInt(animen[0]),animen[1],animen[2],animen[3],animen[4],Integer.parseInt(animen[5]),animen[6],animen[7],false));
//            }
        }

        @NonNull//用于给banner设置内容
        private List<ImageBannerEntry> getImageBannerEntries(){
            Gson gson = new Gson();
            /////////////////////////////////////
//            List<ImageBannerEntry> items = new ArrayList<>();

            Intent intent = getIntent();//从主界面取出加载的banner的标题。
            List<ImageBannerEntry> items = gson.fromJson(intent.getStringExtra("banner"),new TypeToken<List<ImageBannerEntry>>(){}.getType());

            return items;
        }

        //用来开启悬浮窗
        private void Opensubtitle(){

            if (audioManager.isMusicActive()){
                Toast.makeText(MainActivity.this,"请关闭后台播放的音乐或视频",Toast.LENGTH_LONG).show();
                return;
            }

            final SharedPreferences prefe = MainActivity.this.getSharedPreferences(KEYGUARD_SERVICE,0);
            if (!prefe.getBoolean("tutorial1",false)){
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("开始运行");
                builder.setMessage("如果主界面关闭，并且右侧出现一个白色悬浮提示框，证明软件开启成功。此时不要在后台开启其他音乐，直接在播放器里打开想要看的影片即可。");
                builder.setCancelable(false);
                builder.setPositiveButton("知道了，下次不用再提示了",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = prefe.edit();
                        editor.putBoolean("tutorial1",true);
                        editor.commit();
                        MyService.handler.sendEmptyMessage(openFloat);


                        //回到主界面
                        Intent intent1 = new Intent();
                        intent1.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
                        intent1.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
                        startActivity(intent1);

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }



            if ( Build.VERSION.SDK_INT>=23){//申请悬浮窗权限
                if (!Settings.canDrawOverlays(MainActivity.this)){
                    startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
                }
            }

            Message mUrl = MyService.handler.obtainMessage();
            mUrl.what = deliverUrl;
            mUrl.obj = url+(animentid+1)+"/"+(seasonid+1)+"/"+(episodeid+1)+".txt";
            MyService.handler.sendMessage(mUrl);

            Message mName = MyService.handler.obtainMessage();
            mName.what = deliverName;
            mName.obj = animents.get(animentid).getName()+"\n第"+(seasonid+1)+"季 第"+(episodeid+1)+"集";
            MyService.handler.sendMessage(mName);

            if (prefe.getBoolean("tutorial1",false)){
                MyService.handler.sendEmptyMessage(openFloat);
                //回到主界面
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
                intent1.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
                startActivity(intent1);

            }
        }


}
