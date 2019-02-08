package gameview;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameEntity {
    double x;
    double y;
    private double width;
    private double height;
    double speedX;
    private double speedY;
    private Color color;

    public GameEntity(int x, int y,int width,int height, int speedX, int speedY,Color color){
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void update(double time)
    {
        x += speedX * time;
        y += speedY * time;
    }

    public void render(GraphicsContext gc){
        gc.setFill(color);
        gc.fillRect(x,y,width,height);
    }

    public boolean collide(GameEntity gameEntity){
        return gameEntity.getBoundary().intersects(this.getBoundary());
    }

    private Rectangle2D getBoundary()
    {
        return new Rectangle2D(x,y,width,height);
    }


}
