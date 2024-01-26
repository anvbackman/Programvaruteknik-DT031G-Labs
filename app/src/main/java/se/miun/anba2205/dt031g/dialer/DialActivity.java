package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class DialActivity extends AppCompatActivity {

//    private enum Sound {ONE, TWO, THREE}
//    private Map<Sound, Integer> soundIds;
    private SoundPlayer soundplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

//        soundplayer = new SoundPlayer(this);
//
//        DialpadButton button1 = findViewById(R.id.dialpad_button_1);
//        button1.setSound

//        findViewById(R.id.dialpad_button_1).setOnClickListener(view -> soundplayer.playSound(Sound.ONE));




        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));

//        DialpadButton button1 = new DialpadButton(this, null);
//        button1.setTitle("1");
//        button1.setMessage("ABC");
//        setContentView(button1);


    }
}