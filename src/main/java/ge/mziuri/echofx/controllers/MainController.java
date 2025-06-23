package ge.mziuri.echofx.controllers;

import ge.mziuri.echofx.Session;
import ge.mziuri.echofx.database.models.Song;
import ge.mziuri.echofx.database.repository.SongRepository;
import ge.mziuri.echofx.database.repository.UserRepository;
import ge.mziuri.echofx.services.AudioPlayService;
import ge.mziuri.echofx.services.EncryptionService;
import ge.mziuri.echofx.services.SceneChangeService;
import ge.mziuri.echofx.services.SongService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class MainController {

    // <editor-fold desc="New Scene Loading">
    @FXML
    private void loadSignUpScene(ActionEvent event) {
        // Loading sign up view
        URL fxmlUrl = MainController.class.getResource("/ge/mziuri/echofx/views/SignUpView.fxml");
        SceneChangeService.changeScene(event, fxmlUrl, "EchoFX - Sign Up");
    }

    @FXML
    private void loadLogInScene(ActionEvent event) {
        // Loading sign up view
        URL fxmlUrl = MainController.class.getResource("/ge/mziuri/echofx/views/LogInView.fxml");
        SceneChangeService.changeScene(event, fxmlUrl, "EchoFX - Log In");

    }

    public static MainController controller;
    public void loadMainView(ActionEvent event) {
        URL fxmlUrl = MainController.class.getResource("/ge/mziuri/echofx/views/MainView.fxml");
        FXMLLoader loader = SceneChangeService.changeSceneWithController(event, fxmlUrl, "EchoFX");

        // Get the controller instance tied to the new scene
        MainController controller = loader.getController();
        MainController.controller = controller;

        // Initialize scene with songs
        controller.listSongs();
    }

    // </editor-fold>

    // <editor-fold desc="Signup and Login">

    // Login
    @FXML
    private TextField usernameFieldLogin;
    @FXML
    private TextField passwordFieldLogin;

    // Signup
    @FXML
    private TextField usernameFieldSignup;
    @FXML
    private TextField emailFieldSignup;
    @FXML
    private TextField passwordFieldSignup;

    @FXML
    // Login logic
    private void onLogin(ActionEvent event) throws SQLException, IOException {
        // Retrieving info from text fields
        String username = usernameFieldLogin.getText();
        String password = passwordFieldLogin.getText();

        if(username.isBlank() || password.isBlank()) {
            System.out.println("Password or Username empty!");
            return;
        }

        // Querying user info with encrypted password
        if(UserRepository.logIntoUser(username, EncryptionService.encryptPassword(password)))
            // Loading main view
            loadMainView(event);
    }

    @FXML
    // Signup logic
    private void onSignup(ActionEvent event) {
        // Retrieving info from text fields
        String username = usernameFieldSignup.getText();
        String email = emailFieldSignup.getText();
        String password = passwordFieldSignup.getText();

        if(username.isBlank() || password.isBlank() || email.isBlank()) {
            System.out.println("Email, Password or Username empty!");
            return;
        }

        // Adding user info with encrypted password
        if(UserRepository.addUser(username, email,EncryptionService.encryptPassword(password)))
            // Loading main view
            loadMainView(event);
    }

    // </editor-fold>

    // <editor-fold desc="Main View">

    public enum SongTypes {
        HOME,
        FAVORITES,
        INTERNET
    }

    private static SongTypes currentListing = SongTypes.HOME;

    @FXML
    private VBox songDisplayPane;

    // Adds every song to song display pane
    @FXML
    public void listSongs() {
        songDisplayPane.getChildren().clear();
        List<Song> songs = SongService.getUserSongs();
        for(Song song : songs) {
            songDisplayPane.getChildren().add(song.createBanner());
        }
        MainController.currentListing = SongTypes.HOME;
    }

    public void listSongs(List<Song> songs) {
        songDisplayPane.getChildren().clear();
        for(Song song : songs) {
            songDisplayPane.getChildren().add(song.createBanner());
        }
    }

    @FXML
    private Text currentSongText;

    public void setCurrentSongText(String title) {
        currentSongText.setText(title);
    }

    @FXML
    private void toggleMusicPause(ActionEvent event) {
        // Toggling music on/off
        AudioPlayService.toggleMusicPause();
    }

    @FXML
    private void previousAudio() {
        List<Song> songs;
        switch(MainController.currentListing) {
            case FAVORITES:
                songs = SongRepository.favoriteSongs;
                break;
            default:
                songs = SongService.songs;
                break;
        }

        // Playing previous audio in dir
        int index = songs.indexOf(AudioPlayService.currentSong);
        if (index == -1) {
            System.out.println("Item not found in the list.");
        } else {
            Song previous = (index > 0) ? songs.get(index - 1) : songs.getLast();
            AudioPlayService.playMusic(previous.getAddress(), previous);
            setCurrentSongText(previous.getTitle());
        }
    }

    @FXML
    private void nextAudio() {
        if(Session.getSkipsThisHour() >= 4 && !Session.getUser().isPremium()) {
            System.out.println("Maximum amount of skips reached! Restart the application for an additional 4 skips.");
            return;
        }

        List<Song> songs;
        switch(MainController.currentListing) {
            case FAVORITES:
                songs = SongRepository.favoriteSongs;
                break;
            default:
                songs = SongService.songs;
                break;
        }

        // Playing next audio in dir
        int index = songs.indexOf(AudioPlayService.currentSong);
        if (index == -1) {
            System.out.println("Item not found in the list.");
        } else {
            Song next = (index < songs.size() - 1) ? songs.get(index + 1) : songs.getFirst();
            AudioPlayService.playMusic(next.getAddress(), next);
            setCurrentSongText(next.getTitle());
        }

        Session.setSkipsThisHour(Session.getSkipsThisHour() + 1);
    }

    @FXML
    private void loadProfileView(ActionEvent event) {
        URL fxmlUrl = MainController.class.getResource("/ge/mziuri/echofx/views/ProfileView.fxml");
        SceneChangeService.changeScene(event, fxmlUrl, "EchoFX - Profile");
    }

    @FXML
    private Slider volumeSlider;

    public void bindSliderToMedia() {
        AudioPlayService.getMediaPlayer().volumeProperty().bind(
                volumeSlider.valueProperty().divide(100.0)
        );
    }

    @FXML
    private void showFavoriteSongs() {
        // Deleting old songs and showing only favorite songs
        songDisplayPane.getChildren().clear();
        MainController.controller.listSongs(SongRepository.getFavoriteSongs());
        MainController.currentListing = SongTypes.FAVORITES;
    }

    @FXML
    private void turnMusicOff() {
        AudioPlayService.destroyMedia();
        setCurrentSongText("");
    }

    public void openPlaylistPanel(ActionEvent event, Song song) {
        URL fxmlUrl = MainController.class.getResource("/ge/mziuri/echofx/views/PlaylistsView.fxml");
        FXMLLoader loader = SceneChangeService.changeSceneWithController(event, fxmlUrl, "EchoFX - Playlists");

        PlaylistViewController playlistViewController = loader.getController();
        playlistViewController.setSongNameText(song);
    }

    @FXML
    private void showPlaylists() {
        songDisplayPane.getChildren().clear();
        PlaylistViewController.displayPlaylists(songDisplayPane, false);
    }

    // </editor-fold>
}