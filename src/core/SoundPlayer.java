package core;

import javax.sound.sampled.*;
import java.io.IOException;

public class SoundPlayer {
    public static void playSound(String path) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}