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
    private Rectangle bottomBar;
    private Rectangle topBar;
    protected Rectangle player;
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
        bottomBar = new Rectangle(0,700,1920,2);//630 ist feldhöhe
        topBar = new Rectangle(0,0,1920,2);
        player = new Rectangle(800,600,30,30);

        gameArea.getChildren().addAll(topBar, bottomBar,player);

        HBox topBox = new HBox();
        topBox.getChildren().addAll(highscoreLabel,spawnEnemy,fire,menu);

        this.setTop(topBox);
        this.setCenter(gameArea);
    }

    public void addEnemyRectangle(){
        Rectangle rectangle = new Rectangle((int)(Math.random()*1920),0,20,20);
        enemyList.add(rectangle);
        gameArea.getChildren().add(rectangle);
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(9000));
        translateTransition.setNode(rectangle);
        translateTransition.setByY(710);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        rectangle.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println("changed");
                collision(rectangle, bottomBar);
            }
        });
        translateTransition.play();
    }

    public void collision(Rectangle enemy, Rectangle rectangle2){
        Bounds bound1 = enemy.getBoundsInParent();
        Bounds bound2 = rectangle2.getBoundsInParent();
        if(bound1.intersects(bound2)){
            enemy.setFill(Color.PINK);
            //translateTransition.stop();
            gameArea.getChildren().remove(enemy);
            //enemyList.remove(enemy);
            System.out.println(enemyList.size());
        }
    }

    public void fireProjectile(int posX){
        System.out.println(posX);
        Rectangle projectile = new Rectangle(posX,600,3,3);
        projectile.setStrokeWidth(0);
        projectile.setFill(Color.RED);
        gameArea.getChildren().add(projectile);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(9000));
        translateTransition.setNode(projectile);
        translateTransition.setByY(-700);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        projectile.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println("changed");
                collision(projectile,topBar);
                for(Rectangle enemy: enemyList){
                    collision(enemy,projectile);
                }

            }
        });
        translateTransition.play();
    }
}
