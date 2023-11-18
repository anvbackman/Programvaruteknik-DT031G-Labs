package se.miun.anba2205.dt031g.dialer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class DialpadButton extends ConstraintLayout {

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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DialpadButton);
        setTitle(a.getString(R.styleable.DialpadButton_title));
        setMessage(a.getString(R.styleable.DialpadButton_message));
        a.recycle();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                animation();
            }
        });
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

    private void animation() {

//        final Button button = (Button) findViewById(R.id.dialpad_button_1);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.1f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.1f);


        ObjectAnimator buttonAnimation = ObjectAnimator.ofPropertyValuesHolder(this, x, y);
        buttonAnimation.setDuration(300);
        buttonAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        buttonAnimation.setRepeatCount(1);
        buttonAnimation.setRepeatMode(ValueAnimator.REVERSE);
        buttonAnimation.start();


    }

//    private void setupAnimation(View view, final Animator animation, final int animationID) {
//        view.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                animation.start();
//            }
//        });


}

