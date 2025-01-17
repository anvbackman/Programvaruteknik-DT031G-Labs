package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
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

        // Set the call button click listener to request call permission
        instance.setCallButtonClickListener(new Dialpad.CallButtonClickListener() {
            @Override
            public void onCallButtonClick(boolean isClicked) {
                requestCallPermission();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // Requests call permission
    private void requestCallPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            revokeSelfPermissionOnKill(Manifest.permission.CALL_PHONE); // Used to remove permission on kill for easier testing

            String callPermission = Manifest.permission.CALL_PHONE;
            int hasPermission = ContextCompat.checkSelfPermission(this, callPermission);
            String[] permissions = new String[] { callPermission };
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, REQUEST_CODE);
            }
            else {
                Toast.makeText(this, "Already permitted", Toast.LENGTH_SHORT).show();
                startCall();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        startCall();
        super.onRequestPermissionsResult(requestCode, permissions, results);
    }

    // Starts the call based on permission
    private void startCall() {
        Dialpad instance = findViewById(R.id.dialpad);
        String callPermission = Manifest.permission.CALL_PHONE;
        int hasPermission = ContextCompat.checkSelfPermission(this, callPermission);
        // Deciding which intent to use based on permission
        Intent intent;
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            intent = new Intent(Intent.ACTION_CALL);
        } else {
            intent = new Intent(Intent.ACTION_DIAL);
        }
        String dialedNumber = instance.getNumber();
        intent.setData(Uri.parse("tel: " + dialedNumber));
        startActivity(intent);
    }

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