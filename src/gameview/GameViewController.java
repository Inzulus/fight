package gameview;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import player.MP3Player;

public class GameViewController {

    private GameView gameView;
    private MP3Player player;
    private Main application;

    public GameViewController(MP3Player player, Main application){
        this.player = player;
        this.application = application;
        gameView = new GameView();
        /*gameView.spawnEnemy.addEventHandler(ActionEvent.ACTION, event -> {
            gameView.addEnemyRectangle();
        });
        gameView.fire.addEventHandler(ActionEvent.ACTION,event -> {
            gameView.fireProjectile((int)(Math.random()*1920));
        });
        gameView.menu.addEventHandler(ActionEvent.ACTION,event -> {
            application.switchView("mainView");
        });
        gameView.addEventHandler(KeyEvent.KEY_PRESSED,event -> {
            if(event.getCode() == KeyCode.SPACE){
                gameView.fireProjectile((int)(Math.random()*1920));
            }
        });*/
        gameView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.H) {
                    gameView.fireProjectile((int) gameView.player.getX()+12);
                }
                if(event.getCode()== KeyCode.LEFT){
                    gameView.player.setX(gameView.player.getX()-20);
                }

                if(event.getCode()== KeyCode.RIGHT){
                    gameView.player.setX(gameView.player.getX()+20);
                }

            }
        });
    }

    public GameView getGameView(){
        return gameView;
    }
}
