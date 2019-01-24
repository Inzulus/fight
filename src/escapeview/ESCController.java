package escapeview;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
