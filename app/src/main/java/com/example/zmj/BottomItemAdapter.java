package com.example.zmj;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String str = animentList.get(position);
        holder.textView.setText(str);
    }

    @Override
    public int getItemCount() {
        return animentList.size();
    }
}
