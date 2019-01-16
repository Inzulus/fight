package mainview;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import player.MP3Player;

public class Maincontroller {

    MP3Player player  = new MP3Player("brain.mp3");

    @FXML
    private Label startLabel;

    @FXML
    private Label exitLabel;

    @FXML
    private Label highscoreLabel;

    @FXML
    private Button playButton;

    public Maincontroller(){
        player.getCurrentTimeProperty().timeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(()->startLabel.setText(String.valueOf(newValue.intValue())));
            }
        });
    }

    public void play(){
        player.play();
    }




}
