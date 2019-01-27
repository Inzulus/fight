package application;

import aftergameview.AfterGameController;
import gameview.GameViewController;
import gameview.Highscore;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainview.MainViewController;
import player.MP3Player;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {


    private FXMLLoader loader;
    private MP3Player player;
    private Scene scene;
    private GameViewController gameViewController;
    private MainViewController mainViewController;
    private AfterGameController afterGameController;
    private Parent mainView;
    private Parent afterGameView;
    //private Parent gameOverlay;
    private ArrayList<Highscore> highscoreList = new ArrayList<>();

    public void init() {
        player = new MP3Player("technoBeat.mp3");

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/aftergameview/afterGameView.fxml"));
        afterGameController = new AfterGameController(this,highscoreList);
        loader.setController(afterGameController);
        afterGameView = loader.load();

        loader = new FXMLLoader(getClass().getResource("/mainview/mainView.fxml"));
        mainViewController = new MainViewController(player,this, primaryStage);
        loader.setController(mainViewController);
        mainView = loader.load();

        /*loader = new FXMLLoader(getClass().getResource("/gameview/gameOverlay.fxml"));
        gameViewController = new GameViewController(player,this,highscoreList);
        loader.setController(gameViewController);
        gameOverlay = loader.load();*/

        gameViewController = new GameViewController(player,this,highscoreList);
        Parent root = mainView;

        scene = new Scene(root);
        primaryStage.setTitle("FIGHT");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchView(String viewName){
        switch (viewName){
            case "afterGameView":
                scene.setRoot(afterGameView);
                break;
            case "mainView":
                scene.setRoot(mainView);
                break;
            case "gameView":
                StackPane pain = new StackPane();
                /*StackPane game = new StackPane();

                game.getChildren().add(gameViewController.getGameView());
                game.getChildren().add(gameOverlay);*/

                pain.getChildren().add(mainView);
                pain.getChildren().add(gameViewController.getGameView());

                pain.getChildren().get(1).translateYProperty().set(-1080);
                scene.setRoot(pain);

                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(pain.getChildren().get(1).translateYProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event-> {
                    pain.getChildren().remove(0);
                });

                timeline.play();
                gameViewController.startGame();
                gameViewController.getGameView().requestFocus();
                break;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
