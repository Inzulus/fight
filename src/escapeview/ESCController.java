package escapeview;

import application.Main;
import gameview.GameViewController;
import gameview.Highscore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import player.MP3Player;

import java.util.ArrayList;

public class ESCController {

    private MP3Player player;
    private Main application;
    private FileChooser fileChooser = new FileChooser();

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
    public ESCController (MP3Player player, Main application) {
        this.player = player;
        this.application = application;
    }

    //TODO Labelinformationen aus dem GameViewController rausholen zu jedem Zeitpunkt


    //FXML Methoden

    @FXML
    private void continueGame () {
        //TODO Spiel fortsetzen, ohne das es Freezt
    }

    @FXML
    private void restartSong () {
        //TODO richtige Schwierigkeiten die Methoden des GameViewControllers aufzurufen
    }

    @FXML
    private void returnHome () {
        application.switchView("mainView");
    }
}
