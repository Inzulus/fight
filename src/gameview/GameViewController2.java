package gameview;

import application.Main;
import player.MP3Player;

public class GameViewController2 {
    private GameView2 gameView;
    private MP3Player player;
    private Main application;

    public GameViewController2(MP3Player player, Main application) {
        this.player = player;
        this.application = application;
        gameView = new GameView2();
    }


    public GameView2 getGameView(){
        return gameView;
    }
}
