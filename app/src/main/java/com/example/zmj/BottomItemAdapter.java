package com.example.zmj;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**create by hans in 2020,7,1*/
//用来在主界面显示是第几季,和第几集，两个recycle
public class BottomItemAdapter extends RecyclerView.Adapter<BottomItemAdapter.ViewHolder> {
    static int selectedPosition = 0;
    private List<String> animentList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.recycleview_text);
        }
    }

    public BottomItemAdapter(List<String> animents){
        animentList = animents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_animent_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String str = animentList.get(position);
        holder.textView.setText(str);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = holder.getAdapterPosition();//记录哪个是被选中的字幕、
                getListener.onClick(position);
                notifyDataSetChanged();
            }
        });
        if (position == selectedPosition) {
            holder.textView.setTextColor(holder.textView.getResources().getColor(R.color.colorRED));
            if (Build.VERSION.SDK_INT>15)holder.textView.setBackground(holder.textView.getResources().getDrawable(R.drawable.red_border_01));

        selectedPosition = 0;
        }else{
//            否则的话就全白色初始化背景
            holder.textView.setTextColor(holder.textView.getResources().getColor(R.color.colorGRAY));
            if (Build.VERSION.SDK_INT>15)holder.textView.setBackground(holder.textView.getResources().getDrawable(R.drawable.white_border));
        }

    }


    @Override
    public int getItemCount() {
        return animentList.size();
    }


    //定义一个接口用于回调到主界面的方法
    public interface GetListener{
        void onClick(int position);
    }
    //重点，用于监听的listener本体
    private BottomItemAdapter.GetListener getListener;
    //用来把listener初始化,用于监听
    public void setGetListener(BottomItemAdapter.GetListener getListener) {
        this.getListener = getListener;
    }


}
