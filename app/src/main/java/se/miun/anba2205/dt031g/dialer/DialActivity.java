package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class DialActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        Dialpad dialpad = findViewById(R.id.dialpad);
        dialpad.initialize(this);

        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPlayer != null) { // Makes sure the soundplayer is not null before destroying it
            soundPlayer.destroy();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dial_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_bar_settings) {
            startActivity(new Intent(this, SettingsActivity.class));

//            finish();
            return true;
        }
        else if (item.getItemId() == R.id.call_list_settings) {

        }
        return super.onOptionsItemSelected(item);

    }


    private void updateText() {

    }


}