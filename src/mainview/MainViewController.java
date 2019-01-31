package mainview;

import application.Main;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import player.MP3Player;

import java.io.File;

public class MainViewController {

    private MP3Player player;
    private Main application;
    private FileChooser fileChooser = new FileChooser();
    private Stage primaryStage;

    //FXML Ressourcen aus dem Scene-Builder:

        //Wraps:
    @FXML
    private AnchorPane anchor = new AnchorPane();

    @FXML
    private StackPane parentContainer = new StackPane();

        //Labels:
    @FXML
    private Label startLabel = new Label();

    @FXML
    private Label songTitelM = new Label();

    @FXML
    private Label songTitelL = new Label();

    @FXML
    private Label ueberschriftTop = new Label();

    @FXML
    private Label ueberschriftSongs = new Label();

    @FXML
    private Label platz1 = new Label();

    @FXML
    private Label platz2 = new Label();

    @FXML
    private Label platz3 = new Label();

        //Buttons:
    @FXML
    private Button playButton = new Button();

    @FXML
    private Button fightButton = new Button();

    @FXML
    private Button muteButton = new Button();

    @FXML
    private Button songButton1 = new Button();

    @FXML
    private Button songButton2 = new Button();

    @FXML
    private Button songButton3 = new Button();

    @FXML
    private Button changeSongButton = new Button();

    @FXML
    private Button highscoreButton = new Button();
    @FXML
    private Button exitButton = new Button();

        //Slider:
    @FXML
    private Slider sliderSong = new Slider();

    @FXML
    private Slider sliderAudio = new Slider();

        //Rectangles:
    @FXML
    private Rectangle rec1= new Rectangle();

    @FXML
    private Rectangle rec2 = new Rectangle();

    @FXML
    private Rectangle bottomRec = new Rectangle();

        //Transitions:
    private TranslateTransition translateTransition = new TranslateTransition();


    //CONTROLLER:

        //Kontruktor:
    public MainViewController(MP3Player player, Main application, Stage primaryStage) {
        this.player = player;
        this.application = application;
        this.primaryStage = primaryStage;
         }

    public void initialize() {
        info();
        sliderStandart();

        sliderAudio.valueProperty().addListener((observable,oldValue,newValues)->{
            player.volume(newValues.floatValue());
        });
    }


    //TODO Slider abfangen und reagieren

        //Button Methoden:
    @FXML
    private void playMusic() {
        //System.out.println(player.isPlaying());
        if (player.isPlaying()) {
            player.pause();
        } else { player.playWithBeatThread();}
        //System.out.println(player.isPlaying());
    }

    @FXML
    private void muteMusic() {
        player.mute();
    }

    @FXML
    private void playSong1() {
        /*TODO den letzten Song in der Liste abspielen*/
    }

    @FXML
    private void playSong2() {
        /*TODO den vorletzten Song in der Liste abspielen*/
    }

    @FXML
    private void playSong3() {
        /*TODO den drittletzten Song in der Liste abspielen*/
    }

    @FXML
    private void changeSong() {
        File file = fileChooser.showOpenDialog(primaryStage);
        player.stop();
        player.loadTrack(file.getPath());
        info();
        System.out.println(player.isPlaying());
    }

    @FXML
    private void showHighscore() {
        /*TODO Die Highscores zum ausgewählten Song zeigen
        * View Change*/
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
        sliderAudio.adjustValue(1);
    }


    //GETTER:
    public StackPane getParentContainer() { return parentContainer; }

    public AnchorPane getAnchor() { return anchor; }

}
