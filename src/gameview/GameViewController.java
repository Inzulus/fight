package gameview;

import application.Main;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import player.MP3Player;

public class GameViewController {

    private GameView gameView;
    private MP3Player player;
    private Main application;

    public GameViewController(MP3Player player, Main application){
        this.player = player;
        this.application = application;
        gameView = new GameView();

        gameView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.H) {
                    gameView.fireProjectile((int) gameView.player.getX()+13);
                }
                if(event.getCode()== KeyCode.LEFT){
                    gameView.player.setX(gameView.player.getX()-20);
                }

                if(event.getCode()== KeyCode.RIGHT){
                    gameView.player.setX(gameView.player.getX()+20);
                }

            }
        });

        player.bl.getIsKickProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(newValue);
                if(newValue){
                    System.out.println("KICK");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameView.fireProjectile((int) gameView.player.getX() + 13);
                            gameView.addEnemyRectangle();
                        }
                    });
                }
            }
        });
    }


    public GameView getGameView(){
        return gameView;
    }
}
