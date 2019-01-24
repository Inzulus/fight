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

public class GameViewController2 {
    private GameView2 gameView;
    private MP3Player player;
    private Main application;

    public GameViewController2(MP3Player player, Main application) {
        this.player = player;
        this.application = application;
        gameView = new GameView2();

        gameView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.LEFT){
                    System.out.println("hallo");
                    gameView.spieler.setX(gameView.spieler.getX()-20);
                }
                if(event.getCode()== KeyCode.RIGHT){
                    gameView.spieler.setX(gameView.spieler.getX()+20);
                }

            }
        });

        player.bl.getIsKickProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //System.out.println(newValue);
                if(newValue){
                    //System.out.println("KICK");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameView.addGameEntity(new GameEntity((int)(Math.random()*1920),0,30,30,0,30, Color.BLUE));
                        }
                    });
                }
            }
        });


    }


    public GameView2 getGameView(){
        return gameView;
    }
}
