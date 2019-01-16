package gameview;

import application.Main;
import javafx.event.ActionEvent;
import player.MP3Player;

public class GameViewController {

    private GameView gameView;
    private MP3Player player;
    private Main application;

    public GameViewController(MP3Player player, Main application){
        this.player = player;
        this.application = application;
        gameView = new GameView();
        gameView.spawnEnemy.addEventHandler(ActionEvent.ACTION, event -> {
            gameView.addEnemyRectangle();
        });
        gameView.fire.addEventHandler(ActionEvent.ACTION,event -> {
            gameView.fireProjectile((int)(Math.random()*500));
        });
        gameView.menu.addEventHandler(ActionEvent.ACTION,event -> {
            application.switchView("mainView");
        });
    }

    public GameView getGameView(){
        return gameView;
    }
}
