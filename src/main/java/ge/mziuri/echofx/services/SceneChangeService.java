package ge.mziuri.echofx.services;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneChangeService {
    // Changes scenes
    public static void changeScene(ActionEvent event, URL newView) {
        if (newView == null) {
            throw new IllegalArgumentException("FXML URL cannot be null.");
        }

        // Loads the vies
        Parent root = null;
        try {
            root = FXMLLoader.load(newView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // e.g., from a button or any node
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Changes scenes and window title
    public static void changeScene(ActionEvent event, URL newView, String title) {
        if (newView == null) {
            throw new IllegalArgumentException("FXML URL cannot be null.");
        }

        // Loads the view
        Parent root = null;
        try {
            root = FXMLLoader.load(newView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // e.g., from a button or any node
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
