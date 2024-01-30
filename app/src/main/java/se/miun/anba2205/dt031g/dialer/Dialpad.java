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
import android.widget.GridLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import se.miun.anba2205.dt031g.dialer.databinding.DialpadBinding;
import se.miun.anba2205.dt031g.dialer.databinding.DialpadButtonBinding;

public class Dialpad extends ConstraintLayout {

    SoundPlayer soundPlayer;

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

        LayoutInflater.from(context).inflate(R.layout.dialpad, this, true);

        DialpadButton dialpadButton = new DialpadButton(context);
        dialpadButton.setSoundPlayer(new SoundPlayer(context));

    }




}
