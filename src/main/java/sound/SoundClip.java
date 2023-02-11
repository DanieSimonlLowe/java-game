package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundClip {
    private final File clipFile;

    public SoundClip(String filepath) throws UnsupportedAudioFileException, IOException {
        clipFile = new File(filepath);

    }

    synchronized public void playClip() throws IOException, LineUnavailableException, InterruptedException {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }).start();
    }


}
