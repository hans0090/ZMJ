package com.example.zmj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class agreement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        TextView textView = (TextView)findViewById(R.id.aggreement);

        textView.setText("隐私政策\n"+agreement.this.getString(R.string.privacy)+"\n\n"+"用户协议\n"+agreement.this.getString(R.string.protocol));
    }
}
