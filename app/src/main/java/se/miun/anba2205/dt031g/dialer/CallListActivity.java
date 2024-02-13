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
import android.preference.Preference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class CallListActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    private TextView textView;
    private List<String> calledNumbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);

        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));






        textView = findViewById(R.id.call_list_textView);

        if (savedInstanceState != null) {
            calledNumbers = new ArrayList<>(savedInstanceState.getStringArrayList("calledNumbers"));
        }
        else {
            calledNumbers = new ArrayList<>();
        }


        updateText();

//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


    }

//    protected void updateText() {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String textShown = sharedPreferences.getString("switch", "");
//
//        if (textShown.equals("")) {
//            textView.setText("Test");
//        }
//        else {
//            textView.setText("");
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
//        boolean saveNumber = sharedPreferences.getBoolean("switch", false);




//        System.out.println("Resumed: " + textView);

        String newNumber = getIntent().getStringExtra("newNumber");
        if (newNumber != null && !newNumber.isEmpty()) {
            boolean saveNumber = sharedPreferences.getBoolean("switch", false);
            addNumberToCallList(newNumber, saveNumber);
        }


        calledNumbers = new ArrayList<>(sharedPreferences.getStringSet("call_list_textView", new HashSet<>()));


//        saveNumber(isShowing);
        updateText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);

    }

    public void addNumberToCallList(String number, boolean isSaved) {




        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        calledNumbers = new ArrayList<>(sharedPreferences.getStringSet("call_list_textView", new HashSet<>()));

        if (isSaved) {
            if (!calledNumbers.contains(number)) {
                calledNumbers.add(number);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("call_list_textView", new HashSet<>(calledNumbers));
                editor.apply();

                updateText();
            }
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.call_list_settings) {
            clearNumbers();
            return true;
        }


        return super.onOptionsItemSelected(item);

    }



    public void clearNumbers() {
        calledNumbers.clear();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("call_list_textView", new HashSet<>(calledNumbers));
        editor.apply();


        updateText();
    }


    private void updateText() {
        if (calledNumbers.isEmpty()) {
            textView.setText(R.string.empty_call_list);
            System.out.println("UpdateText called as empty");
        }
        else {
            StringBuilder builder = new StringBuilder();
            for (String number : calledNumbers) {
                builder.append(number).append("\n");
            }
            textView.setText(builder.toString().trim());
            System.out.println("Update text called with: " + textView);
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_list_menu, menu);

        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.call_list_settings) {
////            startActivity(new Intent(this, SettingsActivity.class));
//
////            finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }

//    private void isSaved(boolean state) {
//        if (state) {
//            findViewById(R.id.number_entered).setVisibility(View.VISIBLE);
//        }
//        else {
//            findViewById(R.id.number_entered).setVisibility(View.INVISIBLE);
//        }
//
//    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key != null && key.equals("switch")) {
            recreate();
        }
//        else if (key != null && key.equals("stored_numbers")) {
//            System.out.println("Clearing");
//            clearNumbers();
//        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("calledNumbers", new ArrayList<>(calledNumbers));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            calledNumbers = new ArrayList<>(savedInstanceState.getStringArrayList("calledNumbers"));
            updateText();
        }
    }
}