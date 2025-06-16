package ge.mziuri.echofx.services;

import ge.mziuri.echofx.controllers.MainController;
import ge.mziuri.echofx.database.models.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayService {
    private static MediaPlayer mediaPlayer;

    public static String currentAudio = "";
    public static Song currentSong;

    // Plays music from given address
    public static void playMusic(String address, Song song) {
        currentAudio = address;
        currentSong = song;
        Media audio = new Media(address);

        // Stopping previous audio
        if(mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(audio);
        mediaPlayer.play();

        // Binding volume slider to media player
        MainController.controller.bindSliderToMedia();
    }

    // Pauses/unpauses music
    public static void toggleMusicPause() {
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED)
            mediaPlayer.play();
        else mediaPlayer.pause();
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
