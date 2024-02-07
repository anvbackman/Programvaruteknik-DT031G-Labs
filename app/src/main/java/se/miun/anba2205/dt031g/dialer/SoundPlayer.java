package se.miun.anba2205.dt031g.dialer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

    enum Sound {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, ZERO, STAR, POUND
    }
    private SoundPool soundPool;
    private Map<Sound, Integer> soundIds;

    public SoundPlayer(Context context) {

        if (soundPool == null) {
            soundIds = new HashMap<>();

            // Builds the soundpool
            soundPool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(new AudioAttributes.Builder().
                            setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build())
                    .build();

            // Loads the sound files to the map
            soundIds.put(Sound.ONE, soundPool.load(context, R.raw.one, 1));
            soundIds.put(Sound.TWO, soundPool.load(context, R.raw.two, 1));
            soundIds.put(Sound.THREE, soundPool.load(context, R.raw.three, 1));
            soundIds.put(Sound.FOUR, soundPool.load(context, R.raw.four, 1));
            soundIds.put(Sound.FIVE, soundPool.load(context, R.raw.five, 1));
            soundIds.put(Sound.SIX, soundPool.load(context, R.raw.six, 1));
            soundIds.put(Sound.SEVEN, soundPool.load(context, R.raw.seven, 1));
            soundIds.put(Sound.EIGHT, soundPool.load(context, R.raw.eight, 1));
            soundIds.put(Sound.NINE, soundPool.load(context, R.raw.nine, 1));
            soundIds.put(Sound.ZERO, soundPool.load(context, R.raw.zero, 1));
            soundIds.put(Sound.STAR, soundPool.load(context, R.raw.star, 1));
            soundIds.put(Sound.POUND, soundPool.load(context, R.raw.pound, 1));
        }
    }

    public void playSound(Sound soundIndex) {
        // Plays the specified sound
        soundPool.play(soundIds.get(soundIndex), 1f, 1f, 1, 0, 1f);
    }

    public void destroy() {
        if (soundPool != null) {
            // Iterate over resources for each sound and unloads it to make it unavailable to free up resources
            for (int soundId : soundIds.values()) {
                soundPool.unload(soundId);
            }
        }
        // Releases soundpool and sets it to null
        soundPool.release();
        soundPool = null;
    }
}
