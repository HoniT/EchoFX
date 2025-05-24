package ge.mziuri.echofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Setting up scene
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("views/LogInView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        stage.setTitle("EchoFX - Log In");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}