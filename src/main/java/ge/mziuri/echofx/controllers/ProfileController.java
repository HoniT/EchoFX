package ge.mziuri.echofx.controllers;

import ge.mziuri.echofx.Session;
import ge.mziuri.echofx.database.repository.UserRepository;
import ge.mziuri.echofx.services.AudioPlayService;
import ge.mziuri.echofx.services.SceneChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;

import static ge.mziuri.echofx.services.CreditCardService.isValidCardNumber;

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

    @FXML
    private void logout(ActionEvent event) {
        AudioPlayService.destroyMedia();

        // Deleting user and going to log in view
        Session.deleteUser();
        URL fxmlUrl = MainController.class.getResource("/ge/mziuri/echofx/views/LogInView.fxml");
        SceneChangeService.changeScene(event, fxmlUrl, "EchoFX - Log In");
    }

    @FXML
    private TextField cardNumber, ccvCode, expirationDate;

    @FXML
    private void buyPremium() {
        // Retrieving card number
        String cardNumberText = cardNumber.getText();
        String ccvCodeText = ccvCode.getText();
        String expirationDateText = expirationDate.getText();

        if(cardNumberText.isEmpty() || ccvCodeText.isEmpty() || expirationDateText.isEmpty()) {
            System.out.println("Please enter necessary info!");
            return;
        }

        if(ccvCodeText.length() != 3 || !expirationDateText.contains("/")) {
            System.out.println("Please enter a valid CCV or expiration date!");
            return;
        }

        if(!isValidCardNumber(cardNumberText)) {
            System.out.println("Credit card not valid!");
            return;
        }

        System.out.println("Buying premium");
        UserRepository.makePremium(Session.getUser());

    }
}
