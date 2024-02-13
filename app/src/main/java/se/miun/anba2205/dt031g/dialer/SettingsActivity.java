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

            deletePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    deleteStoredNumbers(requireContext());
                    return true;
                }
            });
        }
    }

    private static void deleteStoredNumbers(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Set<String> calledNumbers = sharedPreferences.getStringSet("call_list_textView", new HashSet<>());
        calledNumbers.clear();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("call_list_textView", calledNumbers);
        editor.apply();
    }



//
//
//        return sharedPreferences.getBoolean();




//    public boolean onPreferenceTreeClick(Preference preference) {
//        String key = preference.getKey();
//
//        if ("stored_numbers".equals(key)) {
//            callList.deleteStoredNumbers();
//            return true;
//        }
//    }
}