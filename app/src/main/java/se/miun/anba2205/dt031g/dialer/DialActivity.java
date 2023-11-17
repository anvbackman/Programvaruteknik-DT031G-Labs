package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.util.Objects;

public class DialActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        // Change of the color of the action bar
        ResourcesCompat.getColor(getResources(), R.color.action_bar, null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.action_bar, null)));

//        DialpadButton dialpadButton = new DialpadButton(this);
//        dialpadButton.setTitle("1");
//        dialpadButton.setMessage("ABC");
//        setContentView(dialpadButton);

    }
}