package gameview;

import application.Main;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import player.MP3Player;

import java.util.ArrayList;

public class GameViewController {
    private GameView gameView;
    private MP3Player player;
    private Main application;
    private ArrayList<Highscore> highscoreList = new ArrayList<>();

    //FXML Ressources:


    public GameViewController(MP3Player player, Main application,ArrayList<Highscore> highscoreList) {
        this.player = player;
        this.application = application;
        this.highscoreList = highscoreList;
        gameView = new GameView();
        player.getIsFinished().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(newValue);
                if(newValue){
                    gameView.stopGame();
                    highscoreList.add(new Highscore(player.getCurrentTrack(), gameView.currentHighscore));
                    application.switchView("afterGameView");
                }
            }
        });
        player.bl.getIsKickProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameView.addEnemyEntity(new EnemyEntity((int)(Math.random()*1920),0,30,30,0,30, Color.BLUE,10));
                            gameView.addProjectileEntity(new GameEntity((int)(gameView.spieler.x)+13,(int)(gameView.spieler.y),3,3,0,-200,Color.RED));
                        }
                    });
                }
            }
        });
        gameView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.H){
                    player.pause();
                    gameView.animationTimer.stop();
                }
                if (event.getCode()==KeyCode.J){
                    player.resume();
                    gameView.animationTimer.start();
                }
            }
        });

        gameView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.LEFT){
                    gameView.spieler.speedX=-100;
                }
                if(event.getCode()== KeyCode.RIGHT){
                    gameView.spieler.speedX=100;
                }

                if (event.getCode()==KeyCode.J){
                    gameView.animationTimer.start();
                }
            }
        });

        gameView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.LEFT){
                    gameView.spieler.speedX=0;
                }
                if(event.getCode()== KeyCode.RIGHT){
                    gameView.spieler.speedX=0;
                }
            }
        });
    }

    public void startGame(){
        player.audioPlayer.rewind();
        player.playWithBeatThread();
        gameView.startGame();
    }

    public GameView getGameView(){
        return gameView;
    }
}
