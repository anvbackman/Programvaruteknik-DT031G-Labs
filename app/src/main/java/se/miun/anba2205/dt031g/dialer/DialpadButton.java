package se.miun.anba2205.dt031g.dialer;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.constraintlayout.widget.ConstraintLayout;

import se.miun.anba2205.dt031g.dialer.databinding.DialpadButtonBinding;

public class DialpadButton extends ConstraintLayout {


    private static final float SCALE_UP = 1.1f;
    private static final float SCALE_DOWN = 1.0f;

    private TextView title;
    private TextView message;

    private Animation scaleUp, scaleDown;

    private Button button;

//    private OnClickListener listener;

//    private DialpadClickListener listener;


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

            } finally {
                typedArray.recycle();
            }
        }

        ObjectAnimator scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(
                this,
                PropertyValuesHolder.ofFloat(View.SCALE_X, SCALE_UP),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, SCALE_UP)
        );

        scaleAnimator.setRepeatCount(1);
        scaleAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnimator.setDuration(100);
        scaleAnimator.setInterpolator(new LinearInterpolator());

        binding.getRoot().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleAnimator.start();
            }
        });


    }


    public void setTitle(String titleText) {
        if (titleText != null && !titleText.isEmpty()) {
            title.setText(titleText.substring(0, 1));
        }
    }

    public void setMessage(String messageText) {
        if (messageText != null && !messageText.isEmpty()) {
            message.setText(messageText.substring(0, Math.min(3, messageText.length())));
        }
    }
}



