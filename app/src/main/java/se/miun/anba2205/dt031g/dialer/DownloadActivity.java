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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class DownloadActivity extends AppCompatActivity {

    public static final String URL_LOCATION = "https://dt031g.programvaruteknik.nu/dialer/voices/";
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







        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (url.equalsIgnoreCase(URL_LOCATION)) {
                    startActivity(new Intent(getApplicationContext(), DownloadActivity.class));
                    System.out.println("NOW OVERRIDING");
                    return true;


                }
                return false;
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                System.out.println("NOW DOWNLOADING");
//                shouldOverrideUrlLoading(webView, s);

                download();

            }
        });


    }

    private void download() {
//        progressBarLayout.setVisibility(View.VISIBLE);
        animateView(progressBarLayout, View.VISIBLE);

        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {

            int count;

            @Override
            public void run() {

                //Background work here
                try {

                    // put your url.this is sample url.
                    URL url = new URL(URL_LOCATION);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    System.out.println("URL CONNECTED");


                    int lenghtOfFile = connection.getContentLength();

                    // download the file

                    InputStream input = connection.getInputStream();

                    //catalogfile is your destenition folder
//                    OutputStream output = new FileOutputStream(connection.getOutputStream());
//                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    OutputStream out = new FileOutputStream(new File(getExternalFilesDir(null), "downloaded_file.extension"));


                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....


                        publishProgress(Integer.valueOf("" + (int) ((total * 100) / lenghtOfFile)));
                        System.out.println("PUBLISHING");
                        Thread.sleep(20);

//                        try {
//                            Thread.sleep(3);
//                        }
//                        catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }


                        // writing data to file
                        out.write(data, 0, count);
                    }

                    // flushing output
                    out.flush();

                    // closing streams
                    out.close();
                    input.close();


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //UI Thread work here
                            animateView(progressBarLayout, View.GONE);
                            System.out.println("PROGRESSBAR HIDDEN");

                        }
                    });
                } catch (Exception e) {

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





//        URL url;
//        HttpsURLConnection connection;
//
//        try {
//            url = new URL(URL_LOCATION);
//            connection = (HttpsURLConnection) url.openConnection();
//
//
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            connection.setUseCaches(false);
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//
//
//            progressBar.setMax(20);
//
//
//            connection.connect();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String response = "test";
//
//        return response;


//    @Override
//    protected void onProgressUpdate(Integer... values) {
//        // Only publish the latest value
//        progressBar.setProgress(values[values.length - 1]);
//    }




    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("LOADED URL");
        webView.loadUrl(URL_LOCATION);

    }

    @Override
    protected void onStop() {
        super.onStop();


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



//        webView.setDownloadListener(new DownloadListener());


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


//    private class DownloadListener implements android.webkit.DownloadListener {
//
//
//        @Override
//        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//            if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(DownloadActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//
//            }
//            else {
//                startDownload(url, contentDisposition, mimetype);
//            }
//        }
//    }

//    private void startDownload(String url, String contentDisposition, String mimetype) {
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//        if (downloadManager != null) {
//            long downloadId = downloadManager.enqueue(request);
//        }


//    private class DownloadAsyncTask extends AsyncTask<ImageProxy, Integer, String> {
//
//        private ProgressBar progressBar;
//    }





}