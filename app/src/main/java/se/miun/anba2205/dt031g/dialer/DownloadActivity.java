package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.ListPreference;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class DownloadActivity extends AppCompatActivity {

    //    public static final String URL_LOCATION = "https://dt031g.programvaruteknik.nu/dialer/voices/";
//    public static final String DOWNLOAD_PATH = "/<app-specific-storage>/voices/";
    private static String paths = "";




    private WebView webView;
    private ProgressBar progressBar;
    private ViewGroup progressBarLayout;
//    private DownloadAsyncTask downloadAsyncTask;

    private long downloadId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));


        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        progressBarLayout = findViewById(R.id.progress_bar_layout);

        String url = getString(R.string.url_location);
//        String path = getString(R.string.storage_path);
        String path = getIntent().getStringExtra("storagePath");





        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String clickURL = request.getUrl().toString();
                startDownload(clickURL);
                System.out.println("URL is: " + url);
                System.out.println("path is: " + path);
                return true;
            }
        });

//        webView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
//                System.out.println("NOW DOWNLOADING");
////                shouldOverrideUrlLoading(webView, s);
//
//                download(url, path);
//
////                imageUploadAsyncTask = new ImageUploadAsyncTask();
////                imageUploadAsyncTask.execute(image);
//
//            }
//        });


    }

    private void setFilePath(String filePath) {
        this.paths = filePath;

    }

    public String getFilePath() {
        return paths;
    }

    private void startDownload(String url) {
        int cutLastIndex = url.lastIndexOf("/");
        String fileName = url.substring(cutLastIndex + 1);
//        String downloadPath = (url + fileName);
        setFilePath(url);
//        if (!downloadPath.isEmpty()) {
//            setFilePath(downloadPath);
//        }
        download(url, fileName);

//        if (!downloadPath.isEmpty()) {
//            return Util.unzip(downloadedFilePath);
//        } else {
//            return "";
//        }
    }

    private void download(String urlPath, String fileName) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlPath);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        File destination = new File(getFilesDir(), fileName);

                        try (BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
                             FileOutputStream output = new FileOutputStream(destination)) {
                            byte[] data = new byte[1024];
                            int bytesRead;

                            while ((bytesRead = input.read(data)) != -1) {
                                output.write(data, 0, bytesRead);
                            }
                        }

                        File destinationDir = new File(getFilesDir(), "voices");
                        if (!destinationDir.exists()) {
                            destinationDir.mkdir();
                        }
                        Util.unzip(destination, destinationDir);
                        System.out.println("Downloaded file: " + destination);
                        System.out.println("Destination: " + destinationDir);
                        if (destination.exists()) {
                            destination.delete();
                        }
                    } else {
                        throw new Exception("Failed to download file: " + connection.getResponseCode() + " " + connection.getResponseMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void publishProgress(Integer... progress) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progress[progress.length - 1]);

            }
        });

    }

    private void animateView(View view, int visibility) {
        ViewGroup parent = (ViewGroup) view.getParent();

        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(100);
        transition.addTarget(view);
        TransitionManager.beginDelayedTransition(parent, transition);

        view.setVisibility(visibility);
    }






    @Override
    protected void onResume() {
        super.onResume();
//        System.out.println("LOADED URL");
        webView.loadUrl(getString(R.string.url_location));

    }

    @Override
    protected void onStop() {
        super.onStop();


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }







}