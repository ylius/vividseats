package com.example.vividseats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        String title = intent.getStringExtra("title");
        tvTitle.setText(title);
    }
}