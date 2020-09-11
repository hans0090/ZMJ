package com.example.zmj;

import android.app.Service;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Map;

public class SpanableStringOperation {


    public static SpannableStringBuilder makeSString(String s, final Service service, final Map<String, String> wordList){
        SpannableStringBuilder sstring= new SpannableStringBuilder();
        //确定要展示的是内容（字幕）
        sstring.append(s);

        //分隔记录单词
        int blankCount = 1;
        int[] blank = new int[2000];
        for (int i = 0;i<s.length();i++){
            if(s.charAt(i)==' '){
                blank[blankCount++]=i;//这个数组记录分割单词的空格位置
            }
        }
        blank[blankCount++]=s.length();
        NolineClick csp;
        int i=0;
        //为每一个单词设置点击事件，利用循环添加监听事件怎么实现？
        if (blankCount>1) {
            final String word = sstring.subSequence(blank[i], blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word))Toast.makeText(service, word + ":"+wordList.get(word), Toast.LENGTH_LONG).show();
                }
            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
// ///////////////////////////////////////////////////////////////

        if (blankCount>2){
            i++;
            final String word2 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word2))Toast.makeText(service, word2+ ":"+wordList.get(word2), Toast.LENGTH_LONG).show();
                }
            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////////////////////////////////////////////////////////////////////

        }


        if (blankCount>3){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();
                }
            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////////////////////////////
        }
        if (blankCount>4) {
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();
                }
            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        ///////////////////////////////////////////////////////////////////
        if (blankCount>5){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if (wordList.containsKey(word3))
                        Toast.makeText(service, word3 + ":" + wordList.get(word3), Toast.LENGTH_LONG).show();
                }
            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }
        if (blankCount>6){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();
                }
            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>7){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}
            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>8){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>9){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>10){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>11){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>12){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>13){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>14){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>15){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>16){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>17){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>18){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>19){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>20){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>21){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>22){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>23){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>24){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>25){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>26){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>27){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>28){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }        if (blankCount>29){
            i++;
            final String word3 = sstring.subSequence(blank[i]+1, blank[1 + i]).toString();
            csp = new NolineClick() {
                @Override
                public void onClick(@NonNull View view) {
                    super.onClick(view);
                    if(wordList.containsKey(word3))Toast.makeText(service, word3+":"+wordList.get(word3), Toast.LENGTH_LONG).show();}            };
            sstring.setSpan(csp, blank[i], blank[1 + i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ////////////////////////////////////////////
        }


        return sstring;
    }

}

