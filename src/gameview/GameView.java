package gameview;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class GameView extends BorderPane {
    private Canvas canvas;
    Pane wrapPane;
    private ArrayList<GameEntity> allEntities = new ArrayList<>();
    GameEntity spieler;
    private ArrayList<EnemyEntity> enemyEntities = new ArrayList<>();
    private ArrayList<GameEntity> projectileEntities = new ArrayList<>();
    AnimationTimer animationTimer;
    private Long lastSystemTime;
    Label highscoreLabel;
    int currentHighscore;


    public GameView(){

        spieler = new GameEntity(1920/2,800,30,30,0,0,Color.PINK);
        allEntities.add(spieler);
        wrapPane = new Pane();
        highscoreLabel = new Label("XXXXXXXX");
        canvas = new Canvas(1920,1000);
        wrapPane.setStyle("-fx-background-color: black");

        this.setCenter(wrapPane);
        this.setTop(highscoreLabel);
        wrapPane.getChildren().addAll(canvas);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();


        lastSystemTime = System.nanoTime();

        animationTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastSystemTime) / 1000000000.0;
                System.out.println(elapsedTime);
                lastSystemTime = currentNanoTime;

                //damit die Animation nach einer Pause nicht nach vorne springt
                if(elapsedTime>1){
                    elapsedTime =0;
                }

                for(GameEntity projectile:projectileEntities){
                    for(EnemyEntity enemy:enemyEntities){
                        if(projectile.collide(enemy)){
                            System.out.println("remove");
                            enemyEntities.remove(enemy);
                            allEntities.remove(enemy);
                            currentHighscore+=enemy.getScore();
                        }
                    }
                }
                highscoreLabel.setText(Integer.toString(currentHighscore));

                graphicsContext.clearRect(0,0,1920,1000);
                for(GameEntity entity: allEntities) {
                    entity.update(elapsedTime);
                    entity.render(graphicsContext);
                }


            }
        };
    }
    public void startGame(){
        animationTimer.start();
        currentHighscore = 0;
    }

    public void addGameEntity(GameEntity entity){
        allEntities.add(entity);

    }

    public void addEnemyEntity(EnemyEntity entity){
        allEntities.add(entity);
        enemyEntities.add(entity);
    }

    public void addProjectileEntity(GameEntity entity){
        allEntities.add(entity);
        projectileEntities.add(entity);
    }
}
