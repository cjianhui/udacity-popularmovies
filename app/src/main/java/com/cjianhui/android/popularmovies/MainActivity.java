package com.cjianhui.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cjianhui.android.popularmovies.utilities.NetworkUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            System.out.println(NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl("popular")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
