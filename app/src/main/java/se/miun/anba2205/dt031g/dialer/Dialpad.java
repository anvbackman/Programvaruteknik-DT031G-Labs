package se.miun.anba2205.dt031g.dialer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dialpad extends ConstraintLayout {

    private TextView numberEntered;
    private DialpadButton dialpadButtonOne;
    private DialpadButton dialpadButtonTwo;
    private DialpadButton dialpadButtonThree;
    private DialpadButton dialpadButtonFour;
    private DialpadButton dialpadButtonFive;
    private DialpadButton dialpadButtonSix;
    private DialpadButton dialpadButtonSeven;
    private DialpadButton dialpadButtonEight;
    private DialpadButton dialpadButtonNine;
    private DialpadButton dialpadButtonZero;
    private DialpadButton dialpadButtonPound;
    private DialpadButton dialpadButtonStar;
    private String inputText = "";


    public Dialpad(Context context) {
        super(context);
        initialize(context);
    }

    public Dialpad(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public Dialpad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public void initialize(Context context) {
        // Inflates the layout resource into the Dialpad instance
        LayoutInflater.from(context).inflate(R.layout.dialpad, this, true);
        // Create a DialpadButton and sets the soundplayer
//        dialpadButton = new DialpadButton(context);
//        dialpadButton.setSoundPlayer(new SoundPlayer(context));

        Log.d("Dialpad", "Dialpad initialized");

        // Create a DialpadButton and sets the sound player
        numberEntered = findViewById(R.id.number_entered);
        Log.d("Dialpad", "Number Entered TextView initialized");

        dialpadButtonOne = findViewById(R.id.dialpad_button_1);
        dialpadButtonTwo = findViewById(R.id.dialpad_button_2);
        dialpadButtonThree = findViewById(R.id.dialpad_button_3);
        dialpadButtonFour = findViewById(R.id.dialpad_button_4);
        dialpadButtonFive = findViewById(R.id.dialpad_button_5);
        dialpadButtonSix = findViewById(R.id.dialpad_button_6);
        dialpadButtonSeven = findViewById(R.id.dialpad_button_7);
        dialpadButtonEight = findViewById(R.id.dialpad_button_8);
        dialpadButtonNine = findViewById(R.id.dialpad_button_9);
        dialpadButtonStar = findViewById(R.id.dialpad_button_star);
        dialpadButtonZero = findViewById(R.id.dialpad_button_0);
        dialpadButtonPound = findViewById(R.id.dialpad_button_pound);


        if (dialpadButtonOne != null) {
            Log.d("Dialpad", "Button One initialized");
        }


        setButtonOnClickListener();
        setBackspaceListener();
        setLongClickListener();
        callNumber();





//        setButtonOnClickListener();

//        dialpadButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String buttonTitle = dialpadButton.getTitleText().toString();
//                Log.d("Button clicked", buttonTitle);
//                appendNumberEntered(buttonTitle);
//            }
//        });
    }

//    public void appendNumberEntered(String text) {
//
//        Log.d("Dialpad", "Appending text: " + text);
//        String currentText = text;
//        numberEntered.setText(currentText + text);
//    }




    private void setButtonOnClickListener() {

        for (DialpadButton dialpadButton : getButtons()) {
            dialpadButton.setCustomClickListener(new DialpadButton.OnTextUpdateListener() {

                @Override
                public void onTextUpdate(DialpadButton dialpadButton) {
                    System.out.println(dialpadButton);
                    Log.d("Click", "Motherfukka");
                    System.out.println("********************aadsadsad**************");
                    inputText += dialpadButton.getTitleText();
                    numberEntered.setText(inputText);
//                appendNumberEntered(inputText);
                }
            });

        }
//        for (DialpadButton dialpadButton : getButtons()) {
//
//        }
//        dialpadButtonOne.setCustomClickListener(new DialpadButton.OnTextUpdateListener() {
//            @Override
//            public void onTextUpdate(DialpadButton dialpadButton) {
//                Log.d("Button", "Clicked");
//                inputText += dialpadButton.getTitleText();
//                numberEntered.setText("test");
//                appendNumberEntered(inputText);
//
//            }
//        });

    }

    private void setBackspaceListener() {
        Button backspaceButton = findViewById(R.id.clear_button);

        backspaceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputText.isEmpty()) {
                    inputText = inputText.substring(0, inputText.length() - 1);
                    System.out.println("AAAAAAAAAAAAa");
                    numberEntered.setText(inputText);
                }
            }
        });


    }

    private void setLongClickListener() {
        Button backspaceButton = findViewById(R.id.clear_button);

        backspaceButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inputText = "";
                numberEntered.setText(inputText);
                return true;
            }
        });
    }

    private void callNumber() {
        Button callButton = findViewById(R.id.call_button);

        callButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!inputText.isEmpty()) {


                    Intent intentCallList = new Intent(getContext(), CallListActivity.class);
                    intentCallList.putExtra("newNumber", inputText);
                    getContext().startActivity(intentCallList);

                    String encodedNumber = Uri.encode(inputText);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel: " + encodedNumber));
                    System.out.println("Call");
                    getContext().startActivity(intent);
                    System.out.println("Initiating call to: " + inputText);

                }

            }
        });
    }


    private List<DialpadButton> getButtons() {
        return Arrays.asList(dialpadButtonOne, dialpadButtonTwo, dialpadButtonThree, dialpadButtonFour, dialpadButtonFive,
                dialpadButtonSix, dialpadButtonSeven, dialpadButtonEight, dialpadButtonNine, dialpadButtonZero,
                dialpadButtonPound, dialpadButtonStar);
    }

    public String getInputText() {
        return inputText;
    }
}
