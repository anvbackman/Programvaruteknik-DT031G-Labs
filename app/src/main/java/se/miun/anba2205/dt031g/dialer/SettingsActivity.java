package se.miun.anba2205.dt031g.dialer;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference deletePreference = findPreference("stored_numbers");
            SwitchPreferenceCompat switchPreference = findPreference("switch");

            // Preference click listener for removing all saved numbers from call list
            deletePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    deleteStoredNumbers(requireContext());
                    return true;
                }
            });

            // Preference change listener for if numbers should be saved or not
            switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                    boolean isSave = (Boolean) newValue;
                    updateNumber(requireContext(), isSave);
                    return true;
                }
            });
        }
    }

    // Method to delete numbers from SharedPreferences
    private static void deleteStoredNumbers(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Gets the saved numbers and clears them
        Set<String> calledNumbers = sharedPreferences.getStringSet("call_list_textView", new HashSet<>());
        calledNumbers.clear();
        // Then puts the cleared numbers back into the textView
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("call_list_textView", calledNumbers);
        editor.apply();
    }

    // Method to update SharedPreference for saving numbers
    private static void updateNumber(Context context, boolean isSave) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("switch", isSave);
        editor.apply();
    }
}