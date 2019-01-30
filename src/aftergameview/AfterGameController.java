package aftergameview;

import application.Main;
import gameview.Highscore;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    //Kontruktor
    public AfterGameController (Main application, ArrayList<Highscore> highscoreList) {
        this.application = application;
        this.highscoreList = highscoreList;
    }


    //FXML Methoden
    @FXML
    private void speicherEingabe() {
        //TODO Eingabe von Textfeld speichern
        System.out.println("speicherEingabe");
        Highscore aktuellerHighscore = highscoreList.remove(highscoreList.size()-1);
        aktuellerHighscore.setSpielerName(eingabeTextfeld.getText());
        highscoreList.add(aktuellerHighscore);
        highscoreList.sort(Highscore::compareTo);
        for(Highscore hs : highscoreList){
            System.out.println(hs.getSpielerName());
            System.out.println(hs.getScore());
            System.out.println(hs.getTrack());
        }
        application.switchView("mainView");
    }

    @FXML
    private void returnHome() {
        //TODO zum Hauptmenü zurückkehren
        System.out.println("returnHome");
        application.switchView("mainView");
    }






}
