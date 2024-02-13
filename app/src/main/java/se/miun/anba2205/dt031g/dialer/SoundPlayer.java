package se.miun.anba2205.dt031g.dialer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

//    enum Sound {
//        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, ZERO, STAR, POUND
//    }

    private Context context;
    private SoundPool soundPool;
    private Map<String, Integer> soundIds;




    public SoundPlayer(Context context) {
        this.context = context;
        soundIds = new HashMap<>();

        if (soundPool == null) {

            System.out.println("Soundpool null in constructor");
            soundPool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(new AudioAttributes.Builder().
                            setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build())
                    .build();
            System.out.println("Soundpool created");
            System.out.println("Context not null");

            loadSounds();



        }
    }

    public void loadSounds() {
        for (Map.Entry<String, String> entry : Util.DEFAULT_VOICE_FILE_NAMES.entrySet()) {
            String soundKey = entry.getKey();
//            String soundValue = entry.getValue();
            System.out.println("Soundkey: " + soundKey);
            try {
                // Attempt to parse soundKey as an integer
                int keyIndex;
                if (soundKey.equals("*")) {
                    keyIndex = 10;
                }
                else if (soundKey.equals("#")) {
                    keyIndex = 11;
                }
                else {
                    keyIndex = Integer.parseInt(soundKey);
                }


                System.out.println("SoundIndex: " + soundKey);

                // Check if keyIndex is a valid index in Util.DEFAULT_VOICE_RESOURCE_IDS
                if (keyIndex >= 0 && keyIndex < Util.DEFAULT_VOICE_RESOURCE_IDS.length) {
                    int resourceId = Util.DEFAULT_VOICE_RESOURCE_IDS[keyIndex];
                    int soundId = soundPool.load(context, resourceId, 1);
                    soundIds.put(soundKey, soundId);
                    System.out.println("Soundkey: " + soundKey);
                } else {
                    System.out.println("SoundPlayer Invalid key index: " + soundKey);
                }
            } catch (NumberFormatException e) {
                System.out.println("SoundPlayer nvalid soundKey: " + soundKey);
            }
        }
    }

    public void playSound(DialpadButton dialpadButton) {
        // Plays the specified sound
        String buttonTitle = dialpadButton.getTitleText();
        Integer soundId = soundIds.get(buttonTitle);

        System.out.println("Key passed: " + buttonTitle);
        System.out.println("Value passed: " + soundId);

        if (soundId != null) {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f);
        } else {
            // Handle the case where soundId is null (button title not found in the map)
            Log.e("SoundPlayer", "Sound ID not found for button title: " + buttonTitle);
        }


//        if (soundPool != null) {
//            soundPool.play(Util.DEFAULT_VOICE_RESOURCE_IDS[Integer.parseInt(Util.DEFAULT_VOICE_FILE_NAMES.get(dialpadButton.getTitleText()))], 1f, 1f, 1, 0, 1f);
//        }

//        soundPool.play(Util.DEFAULT_VOICE_RESOURCE_IDS[Integer.parseInt(dialpadButton.getTitleText())], 1f, 1f, 1, 0, 1f);
//        if (soundIndex != null) {
//            soundPool.play(Integer.parseInt(Util.DEFAULT_VOICE_FILE_NAMES.get(soundIndex)), 1f, 1f, 1, 0, 1f);
//        }
    }

    public void destroy() {
        if (soundPool != null) {
            System.out.println("Soundpool not null in destroy");
            // Iterate over resources for each sound and unloads it to make it unavailable to free up resources
            for (int soundId : soundIds.values()) {
                soundPool.unload(soundId);
            }
        }
        // Releases soundpool and sets it to null
        soundPool.release();
        soundPool = null;
        System.out.println("Soundpool set to null in destroy");
    }
}
