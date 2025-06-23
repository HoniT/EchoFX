package ge.mziuri.echofx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PlaylistController {
    @FXML
    private Text nameText;

    @FXML
    public Button addButton;

    @FXML
    public Button deleteButton;

    public void setNameText(String name) {
        nameText.setText(name);
    }
}
