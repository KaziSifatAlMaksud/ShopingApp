package com.example.aexpress.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.aexpress.R;
import com.example.aexpress.databinding.ActivityWebsideviewBinding;

public class Websideview extends AppCompatActivity {
    WebView webView ;
    private ActivityWebsideviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebsideviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl("https://www.ewubd.edu/");
    }
}