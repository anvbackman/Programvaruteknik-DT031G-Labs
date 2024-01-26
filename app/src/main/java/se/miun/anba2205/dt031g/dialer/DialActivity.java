package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class DialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        //    private enum Sound {ONE, TWO, THREE}
        //    private Map<Sound, Integer> soundIds;
        SoundPlayer soundPlayer = new SoundPlayer(this);

        // Find your DialpadButton instance (replace R.id.your_dialpad_button_id with the actual ID)
        Dialpad dialpad = findViewById(R.id.dialpad);

        // Set the SoundPlayer reference in DialpadButton
        dialpad.setSoundPlayer(soundPlayer);



        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));




    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//
//        SoundPlayer soundPlayer2 = new SoundPlayer(this);
//        if (soundPlayer2 != null) {
//            soundPlayer2.destroy();
//        }
//    }
}