package se.miun.anba2205.dt031g.dialer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

    private Context context;
    private SoundPool soundPool;
    private Map<String, Integer> soundIds;

    public SoundPlayer(Context context) {
        this.context = context;
        soundIds = new HashMap<>();

        if (soundPool == null) {

            // Building soundpool
            soundPool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(new AudioAttributes.Builder().
                            setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build())
                    .build();

            loadSounds();
        }
    }

    // Method to load sound
    public void loadSounds() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String voiceName = sharedPreferences.getString("change_voice", Util.DEFAULT_VOICE);

        for (Map.Entry<String, String> entry : Util.DEFAULT_VOICE_FILE_NAMES.entrySet()) {
            String soundKey = entry.getKey();
            int keyIndex;

            // Gives the keyIndex a numerical value if soundKey is not
            if (soundKey.equals("*")) {
                keyIndex = 10;
            }
            else if (soundKey.equals("#")) {
                keyIndex = 11;
            }
            else {
                keyIndex = Integer.parseInt(soundKey);
            }

            // Checks if the keyIndex is avalilable
            if (keyIndex >= 0 && keyIndex < Util.DEFAULT_VOICE_RESOURCE_IDS.length) {
                int resourceId = Util.DEFAULT_VOICE_RESOURCE_IDS[keyIndex];
                // Loads the default sound
                if (voiceName.equals(Util.DEFAULT_VOICE)) {
                    int soundId = soundPool.load(context, resourceId, 1);
                    System.out.println("REGULAR: " + resourceId + " " + soundId);
                    System.out.println("REGULAR Soundkey: " + soundKey + " SoundId: " + soundId);
                    soundIds.put(soundKey, soundId);
                }
                else {
                    String filename = entry.getValue();
                    File voiceDirectory = Util.getInternalStorageDir(context);
                    File voiceFile = new File(voiceDirectory + "/voices/", voiceName + "/" + filename);
                    System.out.println("Voice file to play: " + voiceFile);
                    System.out.println("Voice directory: " + voiceDirectory);
                    if (voiceFile.exists()) {
                        int soundId = soundPool.load(voiceFile.getPath(), 1);
                        System.out.println("Loaded sound id : " + soundId);
                        System.out.println("NOT REGULAR Soundkey: " + soundKey + " SoundId: " + soundId);
                        soundIds.put(soundKey, soundId);
                    }
                }
            }
        }
    }

    // Method to play the sound
    public void playSound(DialpadButton dialpadButton) {

        // Gets the button title to use as key in soundIds
        String buttonTitle = dialpadButton.getTitleText();
        Integer soundId = soundIds.get(buttonTitle);
        System.out.println("Button Title: " + buttonTitle);
        System.out.println("Sound ID: " + soundId);

        // Plays the sound if not null
        if (soundId != null) {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f);
        }
    }

    // Releases each sound in soundPool
    public void destroy() {
        if (soundPool != null) {
            for (int soundId : soundIds.values()) {
                soundPool.unload(soundId);
            }
        }
        soundPool.release();
        soundPool = null;
    }
}
