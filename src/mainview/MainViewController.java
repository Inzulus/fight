package mainview;

import application.Main;
import gameview.Highscore;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import player.MP3Player;

import java.io.File;
import java.util.ArrayList;

public class MainViewController {

    private MP3Player player;
    private Main application;
    private FileChooser fileChooser = new FileChooser();
    private Stage primaryStage;
    private ArrayList<Highscore> highscoreList;

    //FXML Ressourcen aus dem Scene-Builder:

        //Wraps:
    @FXML
    private AnchorPane anchor = new AnchorPane();

    @FXML
    private StackPane parentContainer = new StackPane();

        //Labels:

    @FXML
    private Label songTitelM = new Label();

    @FXML
    private Label songTitelL = new Label();

    @FXML
    private Label ueberschriftTop = new Label();

    @FXML
    private Label ueberschriftSongs = new Label();


        //Buttons:
    @FXML
    private Button playButton = new Button();

    @FXML
    private Button fightButton = new Button();


    @FXML
    private Button changeSongButton = new Button();

    @FXML
    private Button highscoreButton = new Button();
    @FXML
    private Button exitButton = new Button();

    @FXML
    private ToggleButton muteButton = new ToggleButton();

        //Slider:
    @FXML
    private Slider sliderSong = new Slider();

    @FXML
    private Slider sliderAudio = new Slider();


    //CONTROLLER:

        //Kontruktor:
    public MainViewController(MP3Player player, Main application, Stage primaryStage, ArrayList<Highscore> highscoreList) {
        this.player = player;
        this.application = application;
        this.primaryStage = primaryStage;
        this.highscoreList = highscoreList;
    }

    public void initialize() {
        info();
        sliderStandart();

        sliderAudio.valueProperty().addListener((observable,oldValue,newValues)->{
            player.volume(newValues.floatValue());
        });
        player.getCurrentTimeProperty().timeProperty().addListener((observable,oldValue,newValues)->{
            sliderSong.setValue(newValues.doubleValue());
        });

    }


    //TODO Slider abfangen und reagieren

        //Button Methoden:
    @FXML
    private void playMusic() {
        if (player.isPlaying()) {
            player.pause();
        }
        else if(player.isPaused()){
            player.resume();
        }
        else{
            player.playWithBeatThread();
            }
    }

    @FXML
    private void muteMusic() {
        player.mute();
    }

    @FXML
    private void changeSong() {
        File file = fileChooser.showOpenDialog(primaryStage);
        player.stop();
        player.loadTrack(file.getPath());
        info();
        System.out.println(player.isPlaying());
        sliderStandart();
    }

    @FXML
    private void showHighscore() {
       application.switchView("highscoreView");
    }

    @FXML
    private void exit() {
        primaryStage.close();
    }


        //Spiele Mathoden:
    @FXML
    public void handleFightButton(){
        application.switchView("gameView");
    }


    //Others:
    private void info() {
        songTitelM.setText(player.getCurrentTrack().getName());
        songTitelL.setText(player.getCurrentTrack().getName());
    }

    private void sliderStandart() {
        sliderAudio.setMin(0);
        sliderAudio.setMax(2);
        sliderAudio.setValue(1);
        sliderSong.setMin(0);
        sliderSong.setMax(player.getCurrentTrack().getLength());
    }


    //GETTER:
    public StackPane getParentContainer() { return parentContainer; }

    public AnchorPane getAnchor() { return anchor; }

}
