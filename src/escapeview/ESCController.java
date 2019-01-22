package escapeview;

import application.Main;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import player.MP3Player;

public class ESCController {

    //FXML Resourcen

        //Labels:
    @FXML
    private Label bestScore = new Label();

    @FXML
    private Label yourScore = new Label();

        //Buttons:
    @FXML
    private Button continueButton = new Button();

    @FXML
    private Button restartButton = new Button();

    @FXML
    private Button homeButton = new Button();


    //Kontruktor:
    public ESCController () {
        //TODO all this shit
    }


    //FXML Methoden

    @FXML
    private void continueGame () {
        //TODO Spiel soll an angehaltener Stelle weiterlaufen
    }

    @FXML
    private void restartSong () {
        //TODO startet das Spiel mit dem selben Song neu
    }

    @FXML
    private void returnHome () {
        //TODO Zurück zum Hauptmenü, aber den Song behalten
    }
}
