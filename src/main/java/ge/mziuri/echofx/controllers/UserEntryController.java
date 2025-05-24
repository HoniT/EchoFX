package ge.mziuri.echofx.controllers;

import ge.mziuri.echofx.database.repository.UserRepository;
import ge.mziuri.echofx.services.EncryptionService;
import ge.mziuri.echofx.services.SceneChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class UserEntryController {

    // <editor-fold desc="New Scene Loading">
    @FXML
    private void loadSignUpScene(ActionEvent event) throws IOException {
        // Loading sign up view
        URL fxmlUrl = UserEntryController.class.getResource("/ge/mziuri/echofx/views/SignUpView.fxml");
        SceneChangeService.changeScene(event, fxmlUrl, "EchoFX - Sign Up");
    }

    @FXML
    private void loadLogInScene(ActionEvent event) throws IOException {
        // Loading sign up view
        URL fxmlUrl = UserEntryController.class.getResource("/ge/mziuri/echofx/views/LogInView.fxml");
        SceneChangeService.changeScene(event, fxmlUrl, "EchoFX - Log In");
    }

    private void loadMainView(ActionEvent event) throws IOException {
        // Loading main view
        URL fxmlUrl = UserEntryController.class.getResource("/ge/mziuri/echofx/views/MainView.fxml");
        SceneChangeService.changeScene(event, fxmlUrl, "EchoFX");
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
}