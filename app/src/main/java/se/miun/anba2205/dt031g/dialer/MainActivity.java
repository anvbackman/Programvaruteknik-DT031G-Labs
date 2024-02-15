package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String URL_LOCATION = "https://dt031g.programvaruteknik.nu/dialer/voices/";
    private WebView webView;
    private boolean isAboutUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));

        copySound();

        // Starts the Dial
        Button startDial = findViewById(R.id.DIAL);
        startDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DialActivity.class);
                startActivity(intent);
            }
        });

        // Starts the Call List
        Button startCallList = findViewById(R.id.CALL_LIST);
        startCallList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CallListActivity.class);
                startActivity(intent);
            }
        });

        // Starts the Settings
        Button startSettings = findViewById(R.id.SETTINGS);
        startSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Starts the Map
        Button startMap = findViewById(R.id.MAP);
        startMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        // Starts the About screen using the specified text in strings
        Button startAbout = findViewById(R.id.ABOUT);
        startAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                if (!isAboutUsed) { // Checks if about has been used before
                    String aboutText = getString(R.string.about_text);
                    builder.setMessage(aboutText)
                            .setTitle("About");

                    // Add button to close the About screen
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            isAboutUsed = true;
                        }
                    });

                    // Creates and shows the dialog screen
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                else { // Dialog to show if the about function has already been used
                    Toast.makeText(MainActivity.this, "About dialog can only be shown once!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button startDownload = findViewById(R.id.DOWNLOAD);
        startDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        copySound();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("about_dialog_state", isAboutUsed);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isAboutUsed = savedInstanceState.getBoolean("about_dialog_state");
    }

    // Method to copy sound to new directory utilizing the Util class
    private void copySound() {
        // If sound not already in directory
        if (!Util.defaultVoiceExist(this)) {
            boolean copied = Util.copyDefaultVoiceToInternalStorage(this);
            if (copied) {
                Toast.makeText(this, "Files Copied.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Failed to copy default voice files.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}