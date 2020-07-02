package com.example.zmj;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/***create in  2020,6.15 by hans*/
//本类是主界面中-选择字幕 recycleView的Adapter
public class AllAnimentAdapter extends RecyclerView.Adapter<AllAnimentAdapter.ViewHolder> {

    ArrayList<animent> animents; //要展示的元素

    static int selectedPosition = 0;//被选中的字幕，用来设置recycleView的颜色这里默认是第一个
    //构造方法
    public AllAnimentAdapter(ArrayList<animent> animents) {
        this.animents = animents;
    }

    @NonNull
    @Override//创建adapterview时候调用
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animent_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(animents.get(position).getName());
        //为 选择字幕adapterview 的item设置点击事件
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击后变成红色
                selectedPosition = holder.getAdapterPosition();//记录哪个是被选中的字幕、
                //点击后出现一个toast显示名称
//                Toast.makeText(view.getContext(),animents.get(selectedPosition).getName(),Toast.LENGTH_SHORT).show();
                //设置点击后变色,利用接口回调mainActivity中的方法刷新
                getListener.onClick(position);
                notifyDataSetChanged();//刷新适配器，当条目发生改变这是必须的
            }
        });
        //        如果下标和传回来的下标相等 那么确定是点击的条目 把背景设置一下颜色
        if (position == selectedPosition) {
            holder.name.setTextColor(holder.name.getResources().getColor(R.color.colorRED));
            if (Build.VERSION.SDK_INT>15)holder.name.setBackground(holder.name.getResources().getDrawable(R.drawable.red_border_01));
        }else{
//            否则的话就全白色初始化背景
            holder.name.setTextColor(holder.name.getResources().getColor(R.color.colorGRAY));
            if (Build.VERSION.SDK_INT>15)holder.name.setBackground(holder.name.getResources().getDrawable(R.drawable.gray_border));

        }
    }

    @Override
    public int getItemCount() {
        return animents.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
         TextView name;
        public ViewHolder(@NonNull View item){
            super(item);
            name = (TextView) item.findViewById(R.id.name);
        }
    }




    //定义一个接口用于回调到主界面的方法
    public interface GetListener{
        void onClick(int position);
    }
    //重点，用于监听的listener本体
    private GetListener getListener;
    //用来把listener初始化,用于监听
    public void setGetListener(GetListener getListener) {
        this.getListener = getListener;
    }
}
