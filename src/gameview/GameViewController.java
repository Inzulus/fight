package gameview;

import application.Main;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
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


    //Kontruktor:
    public GameViewController(MP3Player player, Main application,ArrayList<Highscore> highscoreList) {
        this.player = player;
        this.application = application;
        this.highscoreList = highscoreList;
        gameView = new GameView();

        //Setzt die Geschwindigkeit der Spielfigur hoch, wenn man eine der Pfeiltasten drückt um diese zu bewegens
        gameView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.LEFT){
                    gameView.spieler.speedX=-400;
                }
                if(event.getCode()== KeyCode.RIGHT){
                    gameView.spieler.speedX=400;
                }

                if (event.getCode()==KeyCode.ESCAPE){
                    pauseOrContinueGame();
                }
            }
        });

        //Setz die Geschwindigkeit der Spielfigur wieder auf 0 wenn man die linke oder rechte Pfeiltaste loslässt
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

    //Wird aufgerufen wenn man ESC drückt und pausiert bzw. resumed das Spiel
    public void pauseOrContinueGame() {
        if (!gameView.isRunning){
            gameView.isRunning = true;
            player.resume();
            gameView.animationTimer.start();
            application.switchView("ESCback");
        }
        else {
            gameView.isRunning = false;
            player.pause();
            gameView.animationTimer.stop();
            application.switchView("ESC");
        }
    }

    public void startGame(){
        //Spawnt einen Gegner und ein Projektl bei jedem erkannten Takt des BeatListeners
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

        //Wenn der Song fertig abgespielt wurde, während das Spiel lief fügt er den Highscore der Liste hinzu und wechselt auf den AfterGameView
        player.getIsFinished().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && gameView.isRunning){
                    gameView.stopGame();
                    highscoreList.add(new Highscore(player.getCurrentTrack(), gameView.currentHighscore));
                    application.switchView("afterGameView");
                    player.audioPlayer.rewind();
                }
            }
        });
        player.audioPlayer.rewind();
        player.playWithBeatThread();
        gameView.startGame();
    }

    //Lonely Getter:
    public GameView getGameView(){
        return gameView;
    }
}
