package com.example.zmj;

/***by hansong*/
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
//用来设置Recyclerview的页边距
public class ChatBorder extends RecyclerView.ItemDecoration {

    private int space;

    public ChatBorder(int space){//输入边距的大小数值
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = (int)(space*0.5);

        if (parent.getChildPosition(view) == 0)
            outRect.top = (int)(space*0.5);;
    }

}
