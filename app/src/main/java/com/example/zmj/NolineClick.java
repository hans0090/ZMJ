package com.example.zmj;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
//作用 去掉下划线
public class NolineClick extends ClickableSpan {

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
        ds.toString();
    }
    
    @Override
    public void onClick(@NonNull View view) {

    }
}
