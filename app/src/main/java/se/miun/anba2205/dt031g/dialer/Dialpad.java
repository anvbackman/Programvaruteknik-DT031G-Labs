package se.miun.anba2205.dt031g.dialer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Dialpad extends ConstraintLayout {

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
        DialpadButton dialpadButton = new DialpadButton(context);
        dialpadButton.setSoundPlayer(new SoundPlayer(context));
    }
}
