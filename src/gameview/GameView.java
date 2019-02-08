package gameview;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class GameView extends BorderPane {

    private ArrayList<GameEntity> allEntities = new ArrayList<>();
    private ArrayList<EnemyEntity> enemyEntities = new ArrayList<>();
    private ArrayList<GameEntity> projectileEntities = new ArrayList<>();
    GameEntity spieler;

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Pane wrapPane;
    private Label highscoreLabel;

    AnimationTimer animationTimer;
    private Long lastSystemTime;
    int currentHighscore;
    boolean isRunning;


    //Kontruktor:
    public GameView(){
        wrapPane = new Pane();
        highscoreLabel = new Label("XXXXXXXX");
        highscoreLabel.setStyle("-fx-font-size: 50pt");
        highscoreLabel.setTextFill(Color.web("#011425"));

        highscoreLabel.setLayoutX(1500);
        canvas = new Canvas(1920,1000);
        wrapPane.setStyle("-fx-background-image: url(\"files/Gameviewimage.png\");");
        wrapPane.getChildren().addAll(canvas,highscoreLabel);

        this.setCenter(wrapPane);
        graphicsContext = canvas.getGraphicsContext2D();

        lastSystemTime = System.nanoTime();

        //eigentlicher Gameloop in dem alle Elemente ca. 60mal pro Sekunde geupdatet und auf Kollision überprüft werden
        animationTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastSystemTime) / 1000000000.0;
                lastSystemTime = currentNanoTime;

                //damit die Animation nach einer Pause nicht nach vorne springt
                if(elapsedTime>1){
                    elapsedTime =0;
                }

                //Einmal Ignorieren bitte:
                /*Iterator<GameEntity> projectileIterator = projectileEntities.iterator();
                Iterator<EnemyEntity> enemyEntityIterator = enemyEntities.iterator();
                while(projectileIterator.hasNext()){
                    GameEntity projectile = projectileIterator.next();
                    //System.out.println(projectile);
                    while(enemyEntityIterator.hasNext()){
                        EnemyEntity enemy = enemyEntityIterator.next();
                        //System.out.println(enemy);
                        //System.out.println(projectile);
                        if(enemy.collide(projectile)){
                            System.out.println("remove");
                            currentHighscore+=enemy.getScore();
                            projectileIterator.remove();
                            enemyEntityIterator.remove();
                        }
                    }
                }*/

                //durchläuft die Projectile und Enemylisten um auf Kollision zu prüfen
                try{
                    for(GameEntity projectile:projectileEntities){
                        for(EnemyEntity enemy:enemyEntities){
                            if(projectile.collide(enemy)){
                                enemyEntities.remove(enemy);
                                allEntities.remove(enemy);
                                currentHighscore+=enemy.getScore();
                            }
                        }
                    }
                }catch (ConcurrentModificationException cme){

                }

                highscoreLabel.setText(Integer.toString(currentHighscore));

                //Leert den GraphicsContext und füllt ihn wieder mit allen noch vorhanden Objekten und ihrer geupdateten Position
                graphicsContext.clearRect(0,0,1920,1000);
                for(GameEntity entity: allEntities) {
                    entity.update(elapsedTime);
                    entity.render(graphicsContext);
                }
            }
        };
    }
    //startet das Spiel und fügt die Spielerfigur hinzu
    public void startGame(){
        spieler = new GameEntity(1920/2,900,30,30,0,0,Color.PINK);
        allEntities.add(spieler);
        isRunning = true;
        animationTimer.start();
        currentHighscore = 0;
    }

    //Hält das Spiel an und entfernt alle Elemnte vom Spielfeld
    public void stopGame(){
        isRunning = false;
        animationTimer.stop();
        enemyEntities.removeAll(enemyEntities);
        allEntities.removeAll(allEntities);
        projectileEntities.removeAll(projectileEntities);
    }


    //Adds:
    public void addEnemyEntity(EnemyEntity entity){
        allEntities.add(entity);
        enemyEntities.add(entity);
    }

    public void addProjectileEntity(GameEntity entity){
        allEntities.add(entity);
        projectileEntities.add(entity);
    }
}
