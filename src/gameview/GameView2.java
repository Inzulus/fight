package gameview;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class GameView2 extends BorderPane {
    private Canvas canvas;
    private Pane wrapPane;

    public GameView2(){
        wrapPane = new Pane();
        canvas = new Canvas(1920,1000);

        this.setCenter(wrapPane);
        wrapPane.getChildren().addAll(canvas);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            Long lastSystemTime = System.nanoTime();
            double x = 100;
            double y = 100;
            int counter = 0;
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastSystemTime) / 1000000000.0;
                lastSystemTime = currentNanoTime;


                graphicsContext.clearRect(0,0,1920,1000);

                graphicsContext.fillOval(50,50,50,50);
                graphicsContext.setFill(Color.BLUE);
                //graphicsContext.fillRect(100,100);
            }
        }.start();

    }
}
