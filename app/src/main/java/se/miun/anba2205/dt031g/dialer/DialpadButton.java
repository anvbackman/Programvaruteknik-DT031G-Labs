package se.miun.anba2205.dt031g.dialer;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class DialpadButton extends ConstraintLayout {

    private TextView title;
    private TextView message;

    private View colorView;
    private TextView hexTextView;

    private static int UNDEFINED = -1;
    @ColorInt
    private int color = UNDEFINED;


    public interface OnColorChangeListener {
        void onColorChange(@ColorInt int newColor);
    }

    private DialpadButton.OnColorChangeListener listener;

    public void setOnColorChangeListener(OnColorChangeListener listener) {
        this.listener = listener;
    }

//    public DialpadButton(Context context) {
//        super(context);
//        init(context, null);
//    }

//    public DialpadButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs);
//    }

    public DialpadButton(Context context) {
        super(context);
        init(null);




        // Obtain custom attributes and set text
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DialpadButton);
//        setTitle(a.getString(R.styleable.DialpadButton_title));
//        setMessage(a.getString(R.styleable.DialpadButton_message));
//        a.recycle();
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.dialpad_button, this);
//        LayoutInflater.from(context).inflate(R.layout.dialpad_button, this, true);
//        title = findViewById(R.id.title);
//        message = findViewById(R.id.message);
        colorView = findViewById(R.id.color_view);
        hexTextView = findViewById(R.id.color_hex);

        TypedArray customAttributes = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DialpadButton, 0, 0);
//        setTitle();

        int xmlColor = UNDEFINED;
        try {
            xmlColor = customAttributes.getColor(R.styleable.DialpadButton_button_color_start, UNDEFINED);
        }
        finally {
            customAttributes.recycle();
        }

        setColor(xmlColor);

        colorView.setOnClickListener(view -> setRandomColor());
        setSaveEnabled(true);
    }

    public void setColor(@ColorInt int color) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), this.color, color);
        this.color = color;

        if (listener != null) {
            listener.onColorChange(this.color);
        }
        animator.setDuration(200);

        animator.addUpdateListener(animate -> {
            int animationColor = (int) animate.getAnimatedValue();
            colorView.setBackgroundColor(animationColor);

            String hexColor = String.format("#%06X", (0xFFFFFF & animationColor));
            hexTextView.setText(hexColor);
        });

        animator.start();
    }

    public void setRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        int randomColor = Color.rgb(red, green, blue);

        setColor(randomColor);

    }

    private static class Save extends BaseSavedState {
        int color;

        public Save(Parcelable state) {
            super(state);
        }

        public Save(Parcel source) {
            super(source);
            color = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(color);
        }

        public static final Parcelable.Creator<Save> CREATOR = new Parcelable.Creator<Save>() {
            public Save createFromParcel(Parcel in) {
                return new Save(in);
            }
            public Save[] newArray(int size) {
                return new Save[size];
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable state = super.onSaveInstanceState();

        Save randomColorButtonState = new Save(state);
        randomColorButtonState.color = this.color;
        return randomColorButtonState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Save randomColorButtonState = (Save) state;
        super.onRestoreInstanceState(randomColorButtonState.getSuperState());
        setColor(randomColorButtonState.color);
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
}

