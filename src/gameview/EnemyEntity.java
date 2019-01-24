package gameview;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class EnemyEntity extends GameEntity {
    private int score;
    private Image icon;

    public EnemyEntity(int x, int y,int width,int height, int speedX, int speedY,Color color,int score){
        super(x, y, width, height, speedX, speedY, color);
        this.score = score;
    }

    public EnemyEntity(int x, int y,int width,int height, int speedX, int speedY,Color color,int score,Image icon){
        super(x, y, width, height, speedX, speedY, color);
        this.score = score;
        this.icon = icon;
    }

    public int getScore() {
        return score;
    }
}
