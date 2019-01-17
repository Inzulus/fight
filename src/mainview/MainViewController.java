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

    @FXML
    private Label startLabel;

    @FXML
    private Label exitLabel;

    @FXML
    private Label highscoreLabel;

    @FXML
    private Button playButton = new Button();

    @FXML
    private Rectangle rec1= new Rectangle();

    @FXML
    private Rectangle rec2 = new Rectangle();

    @FXML
    private Rectangle bottomRec = new Rectangle();

    @FXML
    private Button fightButton = new Button();


    private TranslateTransition translateTransition = new TranslateTransition();


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

    @FXML
    public void handlePlayButtonAction(){
        application.switchView("gameView");
    }

    @FXML
    public void handleFightButton(){
        application.switchView("gameView");
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
