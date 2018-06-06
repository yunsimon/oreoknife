package com.yunsimon.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yunsimon.oreoknife.annotations.OreoMethod;
import com.yunsimon.oreoknife.bind.OreoBinder;

/**
 * oreo demo
 * Created by yunsimon on 2018/6/4.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OreoBinder.bind(this);
    }

    @OreoMethod(log = "run test")
    public void test() {
    }
}
