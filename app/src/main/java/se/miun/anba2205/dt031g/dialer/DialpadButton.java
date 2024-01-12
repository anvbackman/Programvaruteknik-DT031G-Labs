package se.miun.anba2205.dt031g.dialer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import se.miun.anba2205.dt031g.dialer.databinding.DialpadButtonBinding;

public class DialpadButton extends ConstraintLayout {

    private TextView title;
    private TextView message;


    public DialpadButton(Context context) {
        super(context);
        initialize(context, null);
    }

    public DialpadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
        // Obtain custom attributes and set text


//        setOnTouchListener(this);

//        setSize();
    }

    public DialpadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    public void initialize(Context context, AttributeSet attrs) {

        DialpadButtonBinding binding = DialpadButtonBinding.inflate(LayoutInflater.from(context), this, true);
        title = binding.title;
        message = binding.message;

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.dialpad_button);
            try {
                int titleColor = typedArray.getColor(R.styleable.dialpad_button_title_color, title.getCurrentTextColor());
                float titleSize = typedArray.getDimension(R.styleable.dialpad_button_title_text_size, title.getTextSize());

                int messageColor = typedArray.getColor(R.styleable.dialpad_button_message_color, message.getCurrentTextColor());
                float messageSize = typedArray.getDimension(R.styleable.dialpad_button_message_text_size, message.getTextSize());

                title.setTextColor(titleColor);
                title.setTextSize(titleSize);

                message.setTextColor(messageColor);
                message.setTextSize(messageSize);

                setTitle(typedArray.getString(R.styleable.dialpad_button_title));
                setMessage(typedArray.getString(R.styleable.dialpad_button_message));

            }
            finally {
                typedArray.recycle();
            }
        }
    }

    public void setTitle(String titleText) {
        if (titleText != null && !titleText.isEmpty()) {
            title.setText(titleText.substring(0, 1));
        }
    }

    public void setMessage(String messageText) {
        if (messageText != null && !messageText.isEmpty()) {
            message.setText(messageText.substring(0, Math.min(4, messageText.length())));
        }
    }

//    private void init(AttributeSet attrs) {
//        inflate(getContext(), R.layout.dialpad_button, this);
//        title = findViewById(R.id.title);
//        message = findViewById(R.id.message);
//
//        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.dialpad_button, 0, 0);
//        setTitle(typedArray.getString(R.styleable.dialpad_button_title));
//        setMessage(typedArray.getString(R.styleable.dialpad_button_message));
//        typedArray.recycle();
//
//    }
//
    public void setSize() {
        int titleHeight = (int) (getHeight() * 0.75);
        int messageHeight = getHeight() - titleHeight;
        setMinimumHeight(Math.min(titleHeight, messageHeight));

        title.getLayoutParams().height = titleHeight;
        message.getLayoutParams().height = messageHeight;

        title.requestLayout();
        message.requestLayout();
    }
//
//    public void setTitle(String titleText) {
//        if (titleText != null && !titleText.isEmpty()) {
//            title.setText(titleText.substring(0, 1));
//        }
//    }
//
//    public void setMessage(String messageText) {
//        if (messageText != null && !messageText.isEmpty()) {
//            message.setText(messageText.substring(0, Math.min(4, messageText.length())));
//        }
//    }
//
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        switch (motionEvent.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                // Handle touch down event
//                animation(1.1f); // Scale up by 10%
//                break;
//
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                // Handle touch up or cancel event
//                animation(1.0f); // Reset to the original size
//                break;
//        }
//        return true; // Consume the touch event
//    }
//
//    private void animation(float scale) {
//
//
//        PropertyValuesHolder x = PropertyValuesHolder.ofFloat(View.SCALE_X, scale);
//        PropertyValuesHolder y = PropertyValuesHolder.ofFloat(View.SCALE_Y, scale);
//
//
//        ObjectAnimator buttonAnimation = ObjectAnimator.ofPropertyValuesHolder(this, x, y);
//        buttonAnimation.setDuration(300);
//        buttonAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//
//        buttonAnimation.start();
//
//
//    }
//



}

