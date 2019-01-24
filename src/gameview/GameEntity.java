package gameview;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameEntity {
    double x;
    double y;
    double width;
    double height;
    double speedX;
    double speedY;
    Color color;

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

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(x,y,width,height);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY(){
        return y;
    }
}
