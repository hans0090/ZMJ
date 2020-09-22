package com.example.zmj;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class ChangeToSpanableString {



    public static String purifyWord(String s){
        String word = new String();
        char[] seq = s.toCharArray();
        for (int i = 0; i < seq.length; i++) {
            if (Character.isLetter(seq[i])){
                if (Character.isUpperCase(seq[i])){
                    seq[i] = Character.toLowerCase(seq[i]);
                }
                word =word + seq[i];

            }
        }

        return word;
    }

    public static SpannableStringBuilder change(String original, final Context context, final Map<String, String> wordList){
        SpannableStringBuilder sString = new SpannableStringBuilder();
        original = original+" ";
        sString.append(original);

        char[] chars = original.toCharArray();
        final int[] blank = new int[2000];//记录空格位置
        int wordNum = 0;
        blank[wordNum] = 0;


        for (int i = 1; i < chars.length; i++) {
            if (chars[i]==' '||chars[i]=='\n'){
                blank[++wordNum]=i;
            }
        }



        for (int i1 = 0; i1 < wordNum; i1++) {
            final int fi = i1;
            final String word = original.substring(blank[fi],blank[fi+1]);
            final String word1 = purifyWord(word);


            String translation = wordList.get(word1);
            if (translation!=null){
                NolineClick nolineClick = new NolineClick(){
                    @Override
                    public void onClick(@NonNull View view) {
                        super.onClick(view);
                        Toast.makeText(context,word1+":"+wordList.get(word1),Toast.LENGTH_SHORT).show();
                    }
                };
                sString.setSpan(nolineClick,blank[fi],blank[fi+1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                continue;
            }

            if (word1.endsWith("s")){
                final String word2 = word1.substring(0,word1.length()-1);
                translation = wordList.get(word2);

                if (translation != null){
                    NolineClick nolineClick = new NolineClick(){
                        @Override
                        public void onClick(@NonNull View view) {
                            super.onClick(view);
                            Toast.makeText(context,word2+":"+wordList.get(word2),Toast.LENGTH_SHORT).show();
                        }
                    };
                    sString.setSpan(nolineClick,blank[fi],blank[fi+1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    continue;
                }
            }

            if (word1.endsWith("ing")){
                final String word3 = word1.substring(0,word1.length()-3);
                translation = wordList.get(word3);

                if (translation != null){
                    NolineClick nolineClick = new NolineClick(){
                        @Override
                        public void onClick(@NonNull View view) {
                            super.onClick(view);
                            Toast.makeText(context,word3+":"+wordList.get(word3),Toast.LENGTH_SHORT).show();
                        }
                    };
                    sString.setSpan(nolineClick,blank[fi],blank[fi+1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    continue;
                }
            }

            if (word1.endsWith("ed")){
                final String word4 = word1.substring(0,word1.length()-2);
                translation = wordList.get(word4);

                if (translation != null){
                    NolineClick nolineClick = new NolineClick(){
                        @Override
                        public void onClick(@NonNull View view) {
                            super.onClick(view);
                            Toast.makeText(context,word4+":"+wordList.get(word4),Toast.LENGTH_SHORT).show();
                        }
                    };
                    sString.setSpan(nolineClick,blank[fi],blank[fi+1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    continue;
                }
            }

        }

        return sString;

    }
}
