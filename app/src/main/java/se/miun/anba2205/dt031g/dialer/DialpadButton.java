package se.miun.anba2205.dt031g.dialer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
}

