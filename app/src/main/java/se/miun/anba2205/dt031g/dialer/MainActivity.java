package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // The name of the file in which to store the number of button clicks
    private static final String FILENAME = "clicks.txt";

    // References to the two RandomColorButtons used in the activity
    private DialpadButton button1;
    private DialpadButton button2;
    private TextView clicksTextView; // Display number of clicks on each button

    // To count number of times each button changes color (is clicked)
    private int button1Clicks;
    private int button2Clicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.dialpad_button_1);
        button2 = findViewById(R.id.dialpad_button_2);
        clicksTextView = findViewById(R.id.clicks);

        // Update text view with number of clicks
        updateClicks();

        // Register OnColorChangeListener on each button

        // For button1 we use the 'old style' with an anonymous inner class
        button1.setOnColorChangeListener(new DialpadButton.OnColorChangeListener() {
            @Override
            public void onColorChange(int newColor) {
                button1Clicks++; // Increase click count
                updateClicks(); // Update text view
            }
        });

        // For button2 we use the 'new style' with a Lambda
        button2.setOnColorChangeListener(newColor -> {
            button2Clicks++; // Increase click count
            updateClicks(); // Update text view
        });

        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));

        // Starts the Dial
        Button startDial = findViewById(R.id.DIAL);
        startDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DialActivity.class);
                startActivity(intent);
            }
        });

        // Starts the Call List
        Button startCallList = findViewById(R.id.CALL_LIST);
        startCallList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CallListActivity.class);
                startActivity(intent);
            }
        });

        // Starts the Settings
        Button startSettings = findViewById(R.id.SETTINGS);
        startSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Starts the Map
        Button startMap = findViewById(R.id.MAP);
        startMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        // Starts the About screen using the specified text in strings
        Button startAbout = findViewById(R.id.ABOUT);
        startAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                String aboutText = getString(R.string.about_text);
                builder.setMessage(aboutText)
                        .setTitle("About");

                // Add button to close the About screen
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                // Creates and shows the dialog screen
                AlertDialog dialog = builder.create();
                dialog.show();
            }


        });
    }
    }

    private void updateClicks() {
        String s = String.format("Button 1: %d%nButton 2: %d", button1Clicks, button2Clicks);
        clicksTextView.setText(s);
    }

    @Override
    protected void onResume() {
        super.onResume(); // Always call super first

        // Read number of clicks and update UI
        readClicks();
        updateClicks();
    }

    @Override
    protected void onPause() {
        super.onPause(); // Always call super first

        // Store number of clicks
        storeClicks();
    }

    /***
     * Reads number of clicks for the two buttons from app-specific storage.
     * If file does not exists, or an Exception occurs, set clicks to 0 (zero).
     */
    private void readClicks() {
        // Get the absolute path to the directory on the filesystem where app-specific files are stored
        File localDir = getApplicationContext().getFilesDir();

        // We use a Scanner to read number of clicks from the text file (button 1 = row 1 and button 2 = row 2)
        // Using try-with-resources we do not need to manually call close on the scanner
        // Calling getFilename in SettingsActivity to get the name of the file
        try (Scanner in = new Scanner(
                new File(localDir, SettingsActivity.getFilename(this)))) {
            button1Clicks = in.nextInt();
            button2Clicks = in.nextInt();
        } catch (Exception e) {
            Log.e(TAG, "Error reading number of clicks from file: " + e.getMessage());
            // No matter the type of Exception (FileNotFound, NoSuchElement, InputMismatch),
            // we just set number of clicks to 0
            button1Clicks = button2Clicks = 0;
        }
    }

    /***
     * Writes number of clicks for the two buttons to app-specific storage.
     */
    private void storeClicks() {
        // Get the absolute path to the directory on the filesystem where app-specific files are stored
        File localDir = getApplicationContext().getFilesDir();

        // We use a PrintWriter to write number of clicks to the text file (button 1 = row 1 and button 2 = row 2)
        // Using try-with-resources we do not need to manually call close on the stream
        // Calling getFilename in SettingsActivity to get the name of the file
        try (PrintWriter out = new PrintWriter(new FileWriter(
                new File(localDir, SettingsActivity.getFilename(this))))) {
            out.println(button1Clicks);
            out.println(button2Clicks);
        } catch (IOException e) {
            // If an IOException occurs, we just ignore it
            Log.e(TAG, "Error writing number of clicks to file: " + e.getMessage());
        }
    }

    /**
     * Override this method to inflate your menu to the activity.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Override this method to handle selections of items in the menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.menu_settings) {
            // User chose the "Settings" item, start the settings activity
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }






}