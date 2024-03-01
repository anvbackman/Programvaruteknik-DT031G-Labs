package se.miun.anba2205.dt031g.dialer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private boolean isClicked = false;
    private String numberToCall = "";

    private CallButtonClickListener callButtonClickListener;

    public interface CallButtonClickListener {
        void onCallButtonClick(boolean isClicked);
    }

    public void setCallButtonClickListener(CallButtonClickListener listener) {
        this.callButtonClickListener = listener;
    }


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

        // Retrieves the TextView
        numberEntered = findViewById(R.id.number_entered);

        // Initialize the buttons
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

        // Calls relevant methods
        setButtonOnClickListener();
        setBackspaceListener();
        setLongClickListener();
        callNumber();
    }

    // Method to set click listener on all buttons and add the digit to the TextView
    private void setButtonOnClickListener() {
        for (DialpadButton dialpadButton : getButtons()) {
            dialpadButton.setCustomClickListener(new DialpadButton.OnClickListener() {
                @Override
                public void onClick(DialpadButton dialpadButton) {
                    inputText += dialpadButton.getTitleText();
                    numberEntered.setText(inputText);
                }
            });
        }
    }





    // Method to remove last digit when backspace button is clicked
    private void setBackspaceListener() {
        Button backspaceButton = findViewById(R.id.clear_button);
        backspaceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputText.isEmpty()) {
                    inputText = inputText.substring(0, inputText.length() - 1);
                    numberEntered.setText(inputText);
                }
            }
        });
    }

    // Method used to clear the number shown when holding down the backspace button
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
                    // Used to store number
                    Intent intentCallList = new Intent(getContext(), CallListActivity.class);

                    String encodedNumber = Uri.encode(inputText); // Encodes the number to allow "#"
                    intentCallList.putExtra("encodedNumber", encodedNumber);

                    setNumber(encodedNumber);
//                    SettingsActivity.saveNumber(getContext(), encodedNumber);
                    System.out.println("number set to: " + encodedNumber);

                    // Save the encoded number in SharedPreferences
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    Set<String> originalCalledNumbers = sharedPreferences.getStringSet("calledNumbers", new HashSet<>());

                    // Create a new set with the same elements as the original set
                    Set<String> calledNumbers = new HashSet<>(originalCalledNumbers);

                    calledNumbers.add(encodedNumber);
                    sharedPreferences.edit().putStringSet("calledNumbers", calledNumbers).apply();

                    setClicked(true);


                    if (callButtonClickListener != null) {
                        callButtonClickListener.onCallButtonClick(isClicked);
                    }
                }
            }
        });
    }

    public void startCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: " + getNumber()));
        getContext().startActivity(intent);
    }

    // Returns all buttons as List
    private List<DialpadButton> getButtons() {
        return Arrays.asList(dialpadButtonOne, dialpadButtonTwo, dialpadButtonThree, dialpadButtonFour, dialpadButtonFive,
                dialpadButtonSix, dialpadButtonSeven, dialpadButtonEight, dialpadButtonNine, dialpadButtonZero,
                dialpadButtonPound, dialpadButtonStar);
    }


    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean getCallButtonClicked() {
        return isClicked;
    }

    public void setNumber(String number) {
        this.numberToCall = number;
    }
    public String getNumber() {
        return numberToCall;
    }

}
