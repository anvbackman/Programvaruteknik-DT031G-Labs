package se.miun.anba2205.dt031g.dialer;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import se.miun.anba2205.dt031g.dialer.databinding.DialpadButtonBinding;

public class DialpadButton extends ConstraintLayout {

    private static final float SCALE_UP = 1.1f; // Used for button scaling
    private TextView title;
    private TextView message;
    private SoundPlayer soundPlayer;


    public DialpadButton(Context context) {
        super(context);
        initialize(context, null);
    }

    public DialpadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public DialpadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    // Initializes the view
    public void initialize(Context context, AttributeSet attrs) {

//        soundPlayer = new SoundPlayer(context);
        // Inflates the layout using binding
        DialpadButtonBinding binding = DialpadButtonBinding.inflate(LayoutInflater.from(context), this, true);
        title = binding.title;
        message = binding.message;

        // Handles attributes from XML
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.dialpad_button);
            try {
                // Get color and size for the title
                int titleColor = typedArray.getColor(R.styleable.dialpad_button_title_color, title.getCurrentTextColor());
                float titleSize = typedArray.getDimension(R.styleable.dialpad_button_title_text_size, title.getTextSize());
                // Get color and size for the message
                int messageColor = typedArray.getColor(R.styleable.dialpad_button_message_color, message.getCurrentTextColor());
                float messageSize = typedArray.getDimension(R.styleable.dialpad_button_message_text_size, message.getTextSize());

                // Sets the color and size of the title and message
                title.setTextColor(titleColor);
                title.setTextSize(titleSize);
                message.setTextColor(messageColor);
                message.setTextSize(messageSize);

                // Sets the text of the title and message
                setTitle(typedArray.getString(R.styleable.dialpad_button_title));
                setMessage(typedArray.getString(R.styleable.dialpad_button_message));

            } finally {
                typedArray.recycle();
            }
        }


        // Creates an ObjectAnimator used to scale the button
        ObjectAnimator scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(
                this,
                PropertyValuesHolder.ofFloat(View.SCALE_X, SCALE_UP),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, SCALE_UP)
        );

        // Sets initial settings for the ObjectAnimator
        scaleAnimator.setRepeatCount(1);
        scaleAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnimator.setDuration(100);
        scaleAnimator.setInterpolator(new LinearInterpolator());

        // Sets the onClickListener on the root which triggers the ObjectAnimator
        binding.getRoot().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleAnimator.start();
//                soundPlayer.play(context);
            }
        });


    }

    public TextView getTitle() {
        return title;
    }

    // Sets the title text and makes sure that only the first character will show
    public void setTitle(String titleText) {
        if (titleText != null && !titleText.isEmpty()) {
            title.setText(titleText.substring(0, 1));
        }
    }

    // Sets the message text and makes sure that only the first 3 characters will show
    public void setMessage(String messageText) {
        if (messageText != null && !messageText.isEmpty()) {
            message.setText(messageText.substring(0, Math.min(3, messageText.length())));
        }
    }
}



