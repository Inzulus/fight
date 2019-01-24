package application;

import gameview.GameViewController;
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

public class Main extends Application {


    private FXMLLoader loader;
    private MP3Player player;
    private Scene scene;
    private GameViewController gameViewController;
    private MainViewController mainViewController;
    private Parent mainView;

    public void init() {
        player = new MP3Player("technoBeat.mp3");

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/mainview/mainView.fxml"));
        mainViewController = new MainViewController(player,this, primaryStage);
        loader.setController(mainViewController);
        mainView = loader.load();

        gameViewController = new GameViewController(player,this);
        Parent root = mainView;

        scene = new Scene(root);
        primaryStage.setTitle("FIGHT");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchView(String viewName){
        switch (viewName){
            case "mainView":
                scene.setRoot(mainView);
                break;
            case "gameView":
                StackPane pain = new StackPane();
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
