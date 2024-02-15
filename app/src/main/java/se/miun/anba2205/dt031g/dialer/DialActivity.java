package se.miun.anba2205.dt031g.dialer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class DialActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer;


    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);



        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));



        Dialpad instance = findViewById(R.id.dialpad);

        instance.setCallButtonClickListener(new Dialpad.CallButtonClickListener() {
            @Override
            public void onCallButtonClick(boolean isClicked) {

                requestCallPermission();

//                requestCallPermission();
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                System.out.println("Callbutton clicked DialActivity");
//                if (callPermissionGranted()) {
//                    System.out.println("Permission granted starting call from app");
//
//                    intent.setData(Uri.parse("tel: " + instance.getNumber()));
//                    startActivity(intent);
//                }
//                else {
//                    instance.startCall();
//                    System.out.println("Starting call from phone");
//                }
            }
        });


//            callButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
////
//                }
//            });
        }



//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)) {
////            performAction();
//        }
//        else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.REQUEST_PERMISSION)) {
//            // Explain to user why permission is needed
//            Toast.makeText(this, "Allow call?", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            requestPermissions(this, new String[] {Manifest.permission.REQUESTED_PERMISSION}, REQUEST_CODE);
//        }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    System.out.println("Calling");
//                }
//        }
//    }

//    private void requestPermission() {
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUESTED_PERMISSION}, REQUEST_CALL_PHONE_PERMISSION);
//    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    private void requestCallPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            revokeSelfPermissionOnKill(Manifest.permission.CALL_PHONE);

            String callPermission = Manifest.permission.CALL_PHONE;
            int hasPermission = ContextCompat.checkSelfPermission(this, callPermission);
            String[] permissions = new String[] { callPermission };
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, REQUEST_CODE);
            }
            else {
                Toast.makeText(this, "Already permitted", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        Dialpad instance = findViewById(R.id.dialpad);
        Intent intent = new Intent(Intent.ACTION_CALL);
        if (results[0] == PackageManager.PERMISSION_GRANTED) {
            System.out.println("Call granted");
            intent.setData(Uri.parse("tel: " + instance.getNumber()));
            startActivity(intent);
        }
        else {
            System.out.println("Call not granted");
            instance.startCall();
        }
        super.onRequestPermissionsResult(requestCode, permissions, results);
    }


//    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//        if (isGranted) {
//
//        }
//        else {
//            System.out.println("GET FUK OUT");
//        }
//    });
//
//
//    private void requestCallPermission() {
//        requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE);
//    }
//
//
//    private boolean callPermissionGranted() {
//        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
//    }



    // Inflates the menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dial_menu, menu);
        return true;
    }

    // Decides where to navigate when menu item is used
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_bar_settings) {
            startActivity(new Intent(this, SettingsActivity.class));

            return true;
        }

        else if (item.getItemId() == R.id.action_bar_download) {
            startActivity(new Intent(this, DownloadActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPlayer != null) { // Makes sure the soundplayer is not null before destroying it
            soundPlayer.destroy();
        }
    }
}