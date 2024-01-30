package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.Objects;

public class DialActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        Dialpad dialpad = findViewById(R.id.dialpad);
        dialpad.initialize(this);
//        SoundPlayer soundPlayer = new SoundPlayer(this);

//        DialpadButton dialpadButton = findViewById(R.id.dialpad_button_1);

//        dialpadButton.setSoundPlayer(soundPlayer);


        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));

//        List<DialpadButton> dialButtons = dialpad.getDialButtons();
//        for (DialpadButton btn : dialButtons) {
//            soundPlayer.addDependencies(btn);
//            btn.setOnClickedListener(new DialpadButton.OnClickedListener() {
//                @Override
//                public void onClick(DialpadButton button) {
//                    input.setText(new StringBuilder()
//                            .append(input.getText())
//                            .append(button.getTitle())
//                            .toString());
//                }
//            });
//        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        soundPlayer.destroy();




    }
}