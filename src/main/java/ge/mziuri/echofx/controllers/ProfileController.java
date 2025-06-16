package ge.mziuri.echofx.controllers;

import ge.mziuri.echofx.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ProfileController {
    @FXML
    private Text usernameText;
    @FXML
    private Text emailText;

    @FXML
    private void initialize() {
        // Setting user info
        usernameText.setText(usernameText.getText() + Session.getUser().getUsername());
        emailText.setText(emailText.getText() + Session.getUser().getEmail());
    }

    @FXML
    private void returnToHome(ActionEvent event) {
        MainController.controller.loadMainView(event);
    }
}
