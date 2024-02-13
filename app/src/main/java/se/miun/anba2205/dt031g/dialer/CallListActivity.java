package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        // Retrieves the TextView
        textView = findViewById(R.id.call_list_textView);

        // Retrieves saved numbers or creates an empty list
        if (savedInstanceState != null) {
            calledNumbers = new ArrayList<>(savedInstanceState.getStringArrayList("calledNumbers"));
        }
        else {
            calledNumbers = new ArrayList<>();
        }

        updateText();
    }



    @Override
    protected void onResume() {
        super.onResume();

        // Register listener for sharedpreference changes
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Retrieve numbers from intent and adds it to the call list via addNumberToCallList method
        String newNumber = getIntent().getStringExtra("newNumber");
        if (newNumber != null && !newNumber.isEmpty()) {
            // Gets the state of switch (if numbers should be saved or not)
            boolean saveNumber = sharedPreferences.getBoolean("switch", false);
            addNumberToCallList(newNumber, saveNumber);
        }

        // Retrieves numbers from the call list
        calledNumbers = new ArrayList<>(sharedPreferences.getStringSet("call_list_textView", new HashSet<>()));
        updateText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister listener for sharedpreference changes
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    // Adds the numbers to call list
    public void addNumberToCallList(String number, boolean isSaved) {

        // Retrieves the saved numbers
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        calledNumbers = new ArrayList<>(sharedPreferences.getStringSet("call_list_textView", new HashSet<>()));

        // If numbers should be saved and if call list not already contains said number
        // Add number to the call list
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

    // MenuItem that clears all numbers from the call list
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.call_list_settings) {
            clearNumbers();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Clears the numbers and update the displayed text
    public void clearNumbers() {
        calledNumbers.clear();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("call_list_textView", new HashSet<>(calledNumbers));
        editor.apply();

        updateText();
    }


    // Method to update the displayed text.
    // If there is no numbers it is shown, else sets the text for all numbers
    private void updateText() {
        if (calledNumbers.isEmpty()) {
            textView.setText(R.string.empty_call_list);
        }
        else {
            StringBuilder builder = new StringBuilder();
            for (String number : calledNumbers) {
                builder.append(number).append("\n");
            }
            textView.setText(builder.toString().trim());
        }
    }

    // Inflates menu for the call list
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_list_menu, menu);
        return true;
    }

    // Handles changes regarding if number should be stored or not.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key != null && key.equals("switch")) {
            recreate();
        }
    }

    // Saves numbers to the call list after changes
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("calledNumbers", new ArrayList<>(calledNumbers));
    }

    // Restores numbers to call list after changes
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            calledNumbers = new ArrayList<>(savedInstanceState.getStringArrayList("calledNumbers"));
            updateText();
        }
    }
}