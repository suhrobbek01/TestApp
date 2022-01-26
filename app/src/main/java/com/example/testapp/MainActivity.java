package com.example.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String switchTv = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        binding.matem.setOnClickListener(view -> {
            intent.putExtra("key", "matem");
            intent.putExtra("switch", switchTv);
            startActivity(intent);
        });
        binding.tarix.setOnClickListener(view -> {
            intent.putExtra("key", "tarix");
            intent.putExtra("switch", switchTv);
            startActivity(intent);
        });
        binding.onaTili.setOnClickListener(view -> {
            intent.putExtra("key", "ona_tili");
            intent.putExtra("switch", switchTv);
            startActivity(intent);
        });
    }
}