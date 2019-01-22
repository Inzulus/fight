package aftergameview;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import player.MP3Player;


public class AfterGameController {

    //Attribute:

    //FXML Ressourcen:

        //Buttons:
    @FXML
    private Button eingabeSpeichernButton = new Button();

    @FXML
    private Button homeButton = new Button();

        //Textfelder:
    @FXML
    private TextField eingabeTextfeld = new TextField();

        //Labels:
    @FXML
    private Label songtitelFeld = new Label();

    @FXML
    private Label scoreFeld = new Label();

    //Kontruktor
    public AfterGameController () {
        //TODO viel viel erzeugen
    }


    //FXML Methoden
    @FXML
    private void speicherEingabe() {
        //TODO Eingabe von Textfeld speichern
    }

    @FXML
    private void returnHome() {
        //TODO zum Hauptmenü zurückkehren
    }






}
