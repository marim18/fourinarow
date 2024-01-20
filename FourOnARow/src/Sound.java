
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sound {

    public void placedSound()
    {
        try {
            FileInputStream fis = new FileInputStream("src/sound/placed.mp3");
            Player soundPlayer = new Player(fis);
            soundPlayer.play();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (JavaLayerException e) {

            e.printStackTrace();
        }
    }

    public void winnerSound()
    {
        try {
            FileInputStream fis = new FileInputStream("src/sound/winner.mp3");
            Player soundPlayer = new Player(fis);
            soundPlayer.play();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (JavaLayerException e) {

            e.printStackTrace();
        }
    }
}
