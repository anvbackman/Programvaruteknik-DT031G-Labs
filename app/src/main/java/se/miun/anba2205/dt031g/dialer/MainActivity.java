package se.miun.anba2205.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbarMain);
//        setSupportActionBar(toolbar);

        Button startDial = findViewById(R.id.DIAL);
        startDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DialActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openDialActivity(View view) {
        startActivity(new Intent(this, DialActivity.class));
    }

    public void openCallListActivity(View view) {
        startActivity(new Intent(this, CallListActivity.class));
    }
}