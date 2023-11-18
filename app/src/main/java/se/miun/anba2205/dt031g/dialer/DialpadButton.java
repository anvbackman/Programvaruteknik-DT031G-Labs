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

public class DialpadButton extends ConstraintLayout implements View.OnTouchListener {

    private TextView title;
    private TextView message;

//    public DialpadButton(Context context) {
//        super(context);
//        init(context, null);
//    }

//    public DialpadButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs);
//    }

    public DialpadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        // Obtain custom attributes and set text
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.dialpad_button);
        setTitle(a.getString(R.styleable.dialpad_button_title));
        setMessage(a.getString(R.styleable.dialpad_button_message));
        a.recycle();

        setOnTouchListener(this);

    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.dialpad_button, this, true);
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Handle touch down event
                animation(1.1f); // Scale up by 10%
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Handle touch up or cancel event
                animation(1.0f); // Reset to the original size
                break;
        }
        return true; // Consume the touch event
    }

    private void animation(float scale) {

//        final Button button = (Button) findViewById(R.id.dialpad_button_1);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat(View.SCALE_X, scale);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat(View.SCALE_Y, scale);


        ObjectAnimator buttonAnimation = ObjectAnimator.ofPropertyValuesHolder(this, x, y);
        buttonAnimation.setDuration(300);
        buttonAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

//        buttonAnimation.setRepeatCount(1);
//        buttonAnimation.setRepeatMode(ValueAnimator.REVERSE);
        buttonAnimation.start();


    }

//    private void setupAnimation(View view, final Animator animation, final int animationID) {
//        view.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                animation.start();
//            }
//        });


}

