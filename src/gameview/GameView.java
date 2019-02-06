package gameview;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class GameView extends BorderPane {

    private ArrayList<GameEntity> allEntities = new ArrayList<>();
    private ArrayList<EnemyEntity> enemyEntities = new ArrayList<>();
    private ArrayList<GameEntity> projectileEntities = new ArrayList<>();
    GameEntity spieler;

    private Canvas canvas;
    GraphicsContext graphicsContext;
    Pane wrapPane;
    Label highscoreLabel;

    AnimationTimer animationTimer;
    private Long lastSystemTime;
    int currentHighscore;
    boolean isRunning;
    /*Image image;
    ImageView iv;*/


    public GameView(){
        wrapPane = new Pane();
        highscoreLabel = new Label("XXXXXXXX");
        highscoreLabel.setStyle("-fx-font-size: 30pt");
        highscoreLabel.setLayoutX(1850);
        canvas = new Canvas(1920,1000);
        wrapPane.setStyle("-fx-background-color: black");

        /*File file = new File("files/Gameviewimage.png");
        image = new Image(file.toURI().toString());
        iv = new ImageView(image);*/

        this.setCenter(wrapPane);
        wrapPane.getChildren().addAll(canvas,highscoreLabel);

        graphicsContext = canvas.getGraphicsContext2D();


        lastSystemTime = System.nanoTime();
        animationTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastSystemTime) / 1000000000.0;
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
        spieler = new GameEntity(1920/2,900,30,30,0,0,Color.PINK);
        allEntities.add(spieler);
        isRunning = true;
        animationTimer.start();
        currentHighscore = 0;
    }

    public void stopGame(){
        isRunning = false;
        animationTimer.stop();
        enemyEntities.removeAll(enemyEntities);
        allEntities.removeAll(allEntities);
        projectileEntities.removeAll(projectileEntities);
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
