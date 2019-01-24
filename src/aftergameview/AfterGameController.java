package aftergameview;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


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
