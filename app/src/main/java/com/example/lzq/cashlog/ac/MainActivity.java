package com.example.lzq.cashlog.ac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lzq.cashlog.R;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = ((TextView) findViewById(R.id.tv));
//        et = ((EditText) findViewById(R.id.et));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.getText().toString();
            }
        });
    }
}
