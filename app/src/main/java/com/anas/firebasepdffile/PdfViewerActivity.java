package com.anas.firebasepdffile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.net.URLEncoder;

public class PdfViewerActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        webView=findViewById(R.id.webView);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);

        String filename = getIntent().getStringExtra("Filename");
        String fileurl = getIntent().getStringExtra("Fileurl");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(filename);
        progressDialog.setMessage("Opening file... "+fileurl);
        progressDialog.show();

//        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+fileurl);

        String url = " ";
        try {
            url = URLEncoder.encode(fileurl,"UTF-8");
        }
        catch (Exception e){}

        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}