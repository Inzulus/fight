package application;

import gameview.GameViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        player = new MP3Player("brain.mp3");

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/mainview/mainView.fxml"));
        mainViewController = new MainViewController(player,this);
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
                scene.setRoot(gameViewController.getGameView());
                break;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
