package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.ListPreference;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class DownloadActivity extends AppCompatActivity {

    public static final String URL_LOCATION = "https://dt031g.programvaruteknik.nu/dialer/voices/";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));

        webView = findViewById(R.id.web_view);
//        webView.getSettings();
        webView.loadUrl(URL_LOCATION);
//
//
//
//        webView.setWebViewClient(new WebViewClient());
//
//
//        webView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
//                System.out.println("NOW DOWNLOADING");
//
//
//
//            }

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                startActivity(new Intent(getApplicationContext(), ));
//            }
//        });



    }

    @Override
    protected void onResume() {
        super.onResume();

//        webView.loadUrl(URL_LOCATION);
//        webView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
//                System.out.println("NOW DOWNLOADING");
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);
//            }
//        });
    }


}