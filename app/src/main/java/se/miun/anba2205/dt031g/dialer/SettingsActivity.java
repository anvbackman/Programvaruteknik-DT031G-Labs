package se.miun.anba2205.dt031g.dialer;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;
import java.io.File;
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

            ListPreference voicePreference = findPreference("change_voice");
            File voiceDirectory = new File(Util.getInternalStorageDir(getActivity().getApplicationContext()), "voices");
            String[] voiceNames = voiceDirectory.list();
            System.out.println("voiceDirectory" + voiceDirectory);
            System.out.println("Voice names" + voiceNames.toString());

            if (voiceNames != null) {
                voicePreference.setEntries(voiceNames);
                voicePreference.setEntryValues(voiceNames);
            }

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
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("switch", (boolean) newValue);
                    editor.apply();

                    if ((boolean) newValue) { // Check if the new value is true
                        Set<String> calledNumbers = sharedPreferences.getStringSet("calledNumbers", new HashSet<>());
                        String lastDialedNumber;
                        if (!calledNumbers.isEmpty()) {
                            lastDialedNumber = calledNumbers.iterator().next();
                            saveNumber(requireContext(), lastDialedNumber);
                        }
                    }
                    return true;
                }
            });

            voicePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                    String voiceName = (String) newValue;
                    return true;
                }
            });

        }
    }

    // Method to save the numbers
    public static void saveNumber(Context context, String number) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isSave = sharedPreferences.getBoolean("switch", false); // Get the state of the switch

        if (isSave) { // If the switch preference is set to true
            Set<String> originalCalledNumbers = sharedPreferences.getStringSet("calledNumbers", new HashSet<>());
            // Create a new set with the same elements as the original set
            Set<String> calledNumbers = new HashSet<>(originalCalledNumbers);

            calledNumbers.add(number);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("calledNumbers", calledNumbers);
            editor.apply();
        }
    }

    // Method to delete numbers from SharedPreferences
    private static void deleteStoredNumbers(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Put an empty set into SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("calledNumbers", new HashSet<>());
        editor.apply();
    }
}