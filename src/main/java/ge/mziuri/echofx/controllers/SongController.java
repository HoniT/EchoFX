package ge.mziuri.echofx.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SongController {
    @FXML
    private Text titleText;
    @FXML
    private Text artistText;
    @FXML
    private Button playButton;

    public void setTitle(String title) {
        titleText.setText(title);
    }

    public void setArtist(String title) {
        artistText.setText(title);
    }

    public void setPlayAction(EventHandler<ActionEvent> action) {
        playButton.setOnAction(action);
    }
}
