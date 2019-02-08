package escapeview;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import player.MP3Player;

public class ESCController {

    //TODO (FÜR NACH DER ABGABE) Labelinformationen aus dem GameViewController rausholen zu jedem Zeitpunkt

    private MP3Player player;
    private Main application;
    private FileChooser fileChooser = new FileChooser();

    //FXML Resourcen

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


    //FXML Methoden

    @FXML
    private void continueGame () {
        application.getGameViewController().pauseOrContinueGame();
    }

    @FXML
    private void restartSong () {
        application.getGameViewController().getGameView().stopGame();
        application.switchView("ESCback");
        application.getGameViewController().startGame();
    }

    @FXML
    private void returnHome () {
        application.getGameViewController().getGameView().stopGame();
        player.audioPlayer.rewind();
        application.switchView("mainView");
    }
}
