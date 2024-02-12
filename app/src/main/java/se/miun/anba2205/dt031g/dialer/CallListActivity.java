package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class CallListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));





//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isShowing = sharedPreferences.getBoolean("switch", false);

        saveNumber(isShowing);
    }

    private void saveNumber(boolean isSaved) {
        TextView textView = findViewById(R.id.call_list_textView);
        if (isSaved) {
            textView.setText(R.string.call_list_text);
        }
        else {
            textView.setText(R.string.empty_call_list);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_list_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.call_list_settings) {
//            startActivity(new Intent(this, SettingsActivity.class));

//            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

//    private void isSaved(boolean state) {
//        if (state) {
//            findViewById(R.id.number_entered).setVisibility(View.VISIBLE);
//        }
//        else {
//            findViewById(R.id.number_entered).setVisibility(View.INVISIBLE);
//        }
//
//    }
}