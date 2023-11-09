package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setTitle(Html.fromHtml( + getString(R.string.app_name) + "</font>"));
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
//        Toolbar toolbar = findViewById(R.id.toolbarMain);
//        setSupportActionBar(toolbar);

//        ToggleButton toggleButton = findViewById(R.id.toggleButton);
//        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                } else {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                }
//            }
//        });




        Button startDial = findViewById(R.id.DIAL);
        startDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DialActivity.class);
                startActivity(intent);
            }
        });

        Button startCallList = findViewById(R.id.CALL_LIST);
        startCallList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CallListActivity.class);
                startActivity(intent);
            }
        });

        Button startSettings = findViewById(R.id.SETTINGS);
        startSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        Button startMap = findViewById(R.id.MAP);
        startMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        Button startAbout = findViewById(R.id.ABOUT);
        startAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                String aboutText = getString(R.string.about_text);
                builder.setMessage(aboutText)
                        .setTitle("About");

                // Add an "OK" button to close the dialog
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void openDialActivity(View view) {
        startActivity(new Intent(this, DialActivity.class));
    }

    public void openCallListActivity(View view) {
        startActivity(new Intent(this, CallListActivity.class));
    }


//    public void openAbout() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.about_dialog, null);
//        builder.setView(dialogView);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // Handle the OK button click if needed
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
}