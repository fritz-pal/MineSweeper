package me.fritzpal.minesweeper;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Sound {
    public static void play(String fileName) {
        try {
            InputStream soundStream = Sound.class.getResourceAsStream("/" + fileName);
            if (soundStream == null) throw new IOException("Sound file not found: " + fileName);
            InputStream bufferedIn = new BufferedInputStream(soundStream);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}