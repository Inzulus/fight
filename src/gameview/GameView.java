package gameview;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameView extends BorderPane {
    private Rectangle rec1;
    private Label highscoreLabel;
    private Rectangle player;
    private Pane gameArea;
    protected Button spawnEnemy = new Button("spawnEnemy");
    protected Button fire = new Button("fire");
    protected Button menu = new Button("menu");
    private ArrayList<Rectangle> enemyList;

    public GameView(){
        gameArea = new Pane();
        enemyList = new ArrayList<Rectangle>();
        rec1 = new Rectangle(20,20);
        highscoreLabel = new Label("XXXXX");
        player = new Rectangle(0,500,500,10);

        gameArea.getChildren().addAll(rec1,player);

        HBox topBox = new HBox();
        topBox.getChildren().addAll(highscoreLabel,spawnEnemy,fire,menu);

        this.setTop(topBox);
        this.setCenter(gameArea);
    }

    public void addEnemyRectangle(){
        Rectangle rectangle = new Rectangle((int)(Math.random()*500),0,20,20);
        enemyList.add(rectangle);
        gameArea.getChildren().add(rectangle);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(9000));
        translateTransition.setNode(rectangle);
        translateTransition.setByY(300);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        rectangle.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println("changed");
                collision(rectangle,player);
            }
        });
        translateTransition.play();
    }

    public void collision(Rectangle rectangle1, Rectangle rectangle2){
        Bounds bound1 = rectangle1.getBoundsInParent();
        Bounds bound2 = rectangle2.getBoundsInParent();
        if(bound1.intersects(bound2)){
            rectangle1.setFill(Color.PINK);
            //translateTransition.stop();
        }
    }

    public void fireProjectile(int posX){
        System.out.println(posX);
        Rectangle projectile = new Rectangle(posX,400,3,3);
        projectile.setStrokeWidth(0);
        projectile.setFill(Color.RED);
        gameArea.getChildren().add(projectile);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(9000));
        translateTransition.setNode(projectile);
        translateTransition.setByY(-300);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        projectile.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println("changed");
                for(Rectangle enemy: enemyList){
                    collision(enemy,projectile);
                }

            }
        });
        translateTransition.play();
    }
}
