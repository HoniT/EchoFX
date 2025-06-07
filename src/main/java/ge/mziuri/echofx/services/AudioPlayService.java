package ge.mziuri.echofx.services;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayService {
    private static MediaPlayer mediaPlayer;
    public static String currentAudio = "";

    // Plays music from given address
    public static void playMusic(String address) {
        currentAudio = address;
        Media audio = new Media(address);

        // Stopping previous audio
        if(mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(audio);
        mediaPlayer.play();
    }

    // Pauses/unpauses music
    public static void toggleMusicPause() {
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED)
            mediaPlayer.play();
        else mediaPlayer.pause();
    }

    public static void changeAudioVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }
}
