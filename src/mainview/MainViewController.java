package mainview;

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

import java.awt.*;
import java.io.IOException;

public class MainViewController {

    private MP3Player player;
    private Main application;

    //FXML Ressourcen aus dem Scene-Builder:

        //Labels:
    @FXML
    private Label sontitel = new Label();

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
    public MainViewController(MP3Player player, Main application) {
        this.player = player;
        this.application = application;
        player.getCurrentTimeProperty().timeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(()->startLabel.setText(String.valueOf(newValue.intValue())));
            }
        });


    }

    //TODO Slider abfangen und reagieren

        //Button Methoden:
    @FXML
    private void playMusic() {
       /*TODO Song soll abgespielt werden können, nachdem ein Song ausgewählt wurde*/
    }

    @FXML
    private void muteMusic() {
        /*TODO Musik muten können*/
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
        /*TODO Song aus dem Windows-Explorer auswählen*/
    }

    @FXML
    private void showHighscore() {
        /*TODO Die Highscores zum ausgewählten Song zeigen
        * View Change*/
    }

    @FXML
    private void exit() {
        /*TODO Spiel verlassen*/
    }


        //Spiele Mathoden:
    @FXML
    public void handlePlayButtonAction(){
        application.switchView("gameView");
    }

    @FXML
    public void handleFightButton(){
        application.switchView("gameView");
        player.playWithBeatThread();
    }


    public void collision(){
        Bounds bound1 = rec1.getBoundsInParent();
        Bounds bound2 = bottomRec.getBoundsInParent();
        if(bound1.intersects(bound2)){
            rec1.setFill(Color.RED);
            rec2.setFill(Color.PINK);
            translateTransition.stop();
        }
    }

    public void play(){
        //player.play();

        System.out.println(rec1.getY());
        System.out.println(rec1.getTranslateY());
        System.out.println(rec1.getLayoutY());


        translateTransition.setDuration(Duration.millis(10000));
        translateTransition.setNode(rec1);
        translateTransition.setByY(1000);

        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);

        rec1.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println("changed");
                collision();


            }
        });
        translateTransition.play();


    }




}
