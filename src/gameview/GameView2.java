package gameview;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class GameView2 extends BorderPane {
    private Canvas canvas;
    Pane wrapPane;
    private ArrayList<GameEntity> allEntitys = new ArrayList<>();
    GameEntity spieler;


    public GameView2(){

        spieler = new GameEntity(1920/2,800,30,30,0,0,Color.PINK);
        //allEntitys.add(spieler);
        wrapPane = new Pane();
        canvas = new Canvas(1920,1000);
        wrapPane.setStyle("-fx-background-color: green");

        this.setCenter(wrapPane);
        wrapPane.getChildren().addAll(canvas);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            Long lastSystemTime = System.nanoTime();
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastSystemTime) / 1000000000.0;
                lastSystemTime = currentNanoTime;

                graphicsContext.clearRect(0,0,1920,1000);
                for(GameEntity entity: allEntitys) {
                    entity.update(elapsedTime);
                    entity.render(graphicsContext);
                    spieler.render(graphicsContext);
                }


            }
        }.start();

    }

    public void addGameEntity(GameEntity entity){
        allEntitys.add(entity);

    }
}
