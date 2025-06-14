package ge.mziuri.echofx.controllers;

import ge.mziuri.echofx.database.models.Song;
import ge.mziuri.echofx.database.repository.UserRepository;
import ge.mziuri.echofx.services.AudioPlayService;
import ge.mziuri.echofx.services.EncryptionService;
import ge.mziuri.echofx.services.SceneChangeService;
import ge.mziuri.echofx.services.SongService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        // Load FXML with FXMLLoader to access controller
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/ge/mziuri/echofx/views/MainView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller instance tied to the new scene
        MainController controller = loader.getController();
        MainController.controller = controller;

        // Set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("EchoFX");
        stage.show();

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
    private void onSignup(ActionEvent event) throws SQLException, IOException {
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

    @FXML
    private VBox songDisplayPane;

    // Adds every song to song display pane
    private void listSongs() {
        List<Song> songs = SongService.getUserSongs();
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
        // Playing previous audio in dir
        int index = SongService.audioFiles.indexOf(AudioPlayService.currentAudio);
        if (index == -1) {
            System.out.println("Item not found in the list.");
        } else {
            String previous = (index > 0) ? SongService.audioFiles.get(index - 1) : SongService.audioFiles.getLast();
            AudioPlayService.playMusic(previous);
            setCurrentSongText(SongService.getTitle(previous));
        }
    }

    @FXML
    private void nextAudio() {
        // Playing next audio in dir
        int index = SongService.audioFiles.indexOf(AudioPlayService.currentAudio);
        if (index == -1) {
            System.out.println("Item not found in the list.");
        } else {
            String next = (index < SongService.audioFiles.size() - 1) ? SongService.audioFiles.get(index + 1) : SongService.audioFiles.getFirst();
            AudioPlayService.playMusic(next);
            setCurrentSongText(SongService.getTitle(next));
        }
    }

    @FXML
    private void loadProfileView(ActionEvent event) {
        URL fxmlUrl = MainController.class.getResource("/ge/mziuri/echofx/views/ProfileView.fxml");
        SceneChangeService.changeScene(event, fxmlUrl, "EchoFX - Profile");
    }

    // </editor-fold>
}