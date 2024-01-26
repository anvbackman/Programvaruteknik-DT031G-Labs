package se.miun.anba2205.dt031g.dialer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

    private enum Sound {ONE, TWO, THREE}
    private SoundPool soundPool;
    private Map<Sound, Integer> soundIds;

    public SoundPlayer(Context context) {
        setSoundPool(context);
    }

    private void setSoundPool(Context context) {
        if (soundPool == null) {
            soundIds = new HashMap<>();

            soundPool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(new AudioAttributes.Builder().
                    setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build())
                    .build();

            soundIds.put(Sound.ONE, soundPool.load(context, R.raw.one, 1));



//            soundIds = new int[12];
//            soundIds[0] = soundPool.load(R.raw.zero, 1);
//            soundIds[1] = soundPool.load(context, R.raw.one, 1);
//            soundIds[2] = soundPool.load(context, R.raw.two, 1);
//            soundIds[3] = soundPool.load(context, R.raw.three, 1);
//            soundIds[4] = soundPool.load(context, R.raw.four, 1);
//            soundIds[5] = soundPool.load(context, R.raw.five, 1);
//            soundIds[6] = soundPool.load(context, R.raw.six, 1);
//            soundIds[7] = soundPool.load(context, R.raw.seven, 1);
//            soundIds[8] = soundPool.load(context, R.raw.eight, 1);
//            soundIds[9] = soundPool.load(context, R.raw.nine, 1);
//            soundIds[10] = soundPool.load(context, R.raw.star, 1);
//            soundIds[11] = soundPool.load(context, R.raw.pound, 1);
        }
    }

    public void playSound(Sound soundIndex) {
        soundPool.play(soundIds.get(soundIndex), 1f, 1f, 1, 0, 1f);
    }

    public void destroy() {
        soundPool.release();
        soundPool = null;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        setSoundPool();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (soundPool != null) {
//            soundPool.release();
//            soundPool.setOnLoadCompleteListener(null);
//            soundPool = null;
//        }
//    }


}
