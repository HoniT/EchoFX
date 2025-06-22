package ge.mziuri.echofx.controllers;

import ge.mziuri.echofx.database.models.Song;
import ge.mziuri.echofx.database.repository.SongRepository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SongController {
    @FXML
    private Text titleText;
    @FXML
    private Text artistText;
    @FXML
    private Button playButton;
    @FXML
    public Button playlistButton;

    @FXML
    private Button favoriteButton;
    @FXML
    private ImageView starImage;

    public void setTitle(String title) {
        titleText.setText(title);
    }

    public void setArtist(String title) {
        artistText.setText(title);
    }

    public void setPlayAction(EventHandler<ActionEvent> action) {
        playButton.setOnAction(action);
    }

    public void setFavoriteAction(Song song, Runnable addAction, Runnable removeAction) {
        // If we have this song in favorites making the starts opacity 1
        if(SongRepository.checkFavoriteSong(song)) starImage.setOpacity(1);

        favoriteButton.setOnAction((action) -> {
            starImage.setOpacity(starImage.getOpacity() - .75f);
            // Starting opacity of star is .15f (only slightly visible - disabled), if we subtract .75f it becomes <0, and we'll set it to 1 (meaning enabled)
            if(starImage.getOpacity() < 0) {
                starImage.setOpacity(1);
                addAction.run();
            }
            // If we subtract .75f from 1 (enabled - full opacity) it means we removed from favorites, and we'll keep the opacity like this
            else {
                removeAction.run();
            }
        });
    }
}
