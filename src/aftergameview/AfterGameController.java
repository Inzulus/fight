package aftergameview;

import application.Main;
import gameview.Highscore;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import player.InfoEvent;
import player.InfoListener;
import player.MP3Player;

import java.util.ArrayList;


public class AfterGameController {

    //Attribute:
    private Main application;
    private ArrayList<Highscore> highscoreList;

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

        //Others:
    private InfoListener il = new InfoListener() {

        @Override
        public void infoReceived(InfoEvent event) {
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    songtitelFeld.setText(event.getTrack().getName());
                }
            });
        }
    };
    MP3Player player;


    //Kontruktor
    public AfterGameController (Main application, MP3Player player, ArrayList<Highscore> highscoreList) {
        this.application = application;
        this.highscoreList = highscoreList;
        player.addInfoListener(il);
        this.player = player;
    }


    //FXML Methoden
    @FXML
    private void speicherEingabe() {
        Highscore aktuellerHighscore = highscoreList.remove(highscoreList.size()-1);
        aktuellerHighscore.setSpielerName(eingabeTextfeld.getText());
        highscoreList.add(aktuellerHighscore);
        highscoreList.sort(Highscore::compareTo);
        application.switchView("mainView");
    }

    @FXML
    private void returnHome() {
        application.switchView("mainView");
    }

    //Others:
    public void updateScore(){
        scoreFeld.setText(Integer.toString(highscoreList.get(highscoreList.size()-1).getScore()));
        songtitelFeld.setText(player.getCurrentTrack().getName());
    }
}
