package ge.mziuri.echofx.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PlaylistController {
    @FXML
    private Text nameText;

    public void setNameText(String name) {
        nameText.setText(name);
    }
}
