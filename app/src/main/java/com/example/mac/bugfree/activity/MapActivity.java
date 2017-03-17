package com.example.mac.bugfree.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mac.bugfree.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public boolean showPin(){
        return true;
    }

    public boolean showDetail(){
        return true;
    }



}
