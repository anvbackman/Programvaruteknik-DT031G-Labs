package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;

public class DownloadActivity extends AppCompatActivity {

    private static String paths = "";
    private WebView webView;
    private ProgressBar progressBar;
    private ViewGroup progressBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));


        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        progressBarLayout = findViewById(R.id.progress_bar_layout);
        progressBar.setMax(100);

//        String url = getString(R.string.url_location);
//        String path = getIntent().getStringExtra("storagePath");

        // Override the web view client and starts the download
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String clickURL = request.getUrl().toString();
                startDownload(clickURL); // Start download with the clicked URL
                return true;
            }
        });
    }

    private void startDownload(String url) {
        int cutLastIndex = url.lastIndexOf("/");
        String fileName = url.substring(cutLastIndex + 1);
        download(url, fileName);
    }

    private void download(String urlPath, String fileName) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Run progress bar on separate thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        animateView(progressBarLayout, View.VISIBLE);
                    }
                });

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
                            long total = 0;

                            while ((bytesRead = input.read(data)) != -1) {
                                total += bytesRead;
                                publishProgress((int) (total * 100 / connection.getContentLength()));
                                output.write(data, 0, bytesRead);
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        File destinationDir = new File(getFilesDir(), "voices"); // Destination directory
                        if (!destinationDir.exists()) { // Create directory if it does not exist
                            destinationDir.mkdir();
                        }
                        Util.unzip(destination, destinationDir);

                        if (destination.exists()) { // Delete the downloaded file
                            destination.delete();
                        }
                    } else {
                        throw new Exception("Failed to download file: " + connection.getResponseCode() + " " + connection.getResponseMessage());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            animateView(progressBarLayout, View.GONE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Method to update the progress bar
    private void publishProgress(Integer... progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progress[progress.length - 1]);
                TextView progressText = findViewById(R.id.progress_bar_text);
                progressText.setText(getString(R.string.download_progress) + " " + progress[progress.length - 1] + "%");
            }
        });
    }

    // Method to animate the progress bar
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