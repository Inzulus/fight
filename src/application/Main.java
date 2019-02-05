package application;

import aftergameview.AfterGameController;
import escapeview.ESCController;
import gameview.GameViewController;
import gameview.Highscore;
import highscoreview.HighscoreViewController;
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
import player.Track;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Main extends Application {

    //TODO Anzeigefehler beim eneuten Spieleversuch

    private FXMLLoader loader;
    private MP3Player player;
    private Scene scene;
    private GameViewController gameViewController;
    private MainViewController mainViewController;
    private AfterGameController afterGameController;
    private ESCController escController;
    private HighscoreViewController highscoreController;
    private Parent mainView;
    private Parent afterGameView;
    private Parent escView;
    private Parent highscoreView;
    private ArrayList<Highscore> highscoreList = new ArrayList<>();

    public void init() {
        player = new MP3Player("boom.mp3");

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            InputStream inputStream = new FileInputStream("settings.xml");

            Properties prop = new Properties();
            prop.loadFromXML(inputStream);

            player.loadTrack(prop.getProperty("currentTrack"));
            highscoreList.add(new Highscore(new Track(prop.getProperty("hs1Track")), prop.getProperty("hs1Name"), Integer.parseInt(prop.getProperty("hs1Score"))));
            highscoreList.add(new Highscore(new Track(prop.getProperty("hs2Track")), prop.getProperty("hs2Name"), Integer.parseInt(prop.getProperty("hs2Score"))));
            highscoreList.add(new Highscore(new Track(prop.getProperty("hs3Track")), prop.getProperty("hs3Name"), Integer.parseInt(prop.getProperty("hs3Score"))));
            highscoreList.add(new Highscore(new Track(prop.getProperty("hs4Track")), prop.getProperty("hs4Name"), Integer.parseInt(prop.getProperty("hs4Score"))));
            highscoreList.add(new Highscore(new Track(prop.getProperty("hs5Track")), prop.getProperty("hs5Name"), Integer.parseInt(prop.getProperty("hs5Score"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        loader = new FXMLLoader(getClass().getResource("/aftergameview/afterGameView.fxml"));
        afterGameController = new AfterGameController(this,player,highscoreList);
        loader.setController(afterGameController);
        afterGameView = loader.load();

        loader = new FXMLLoader(getClass().getResource("/mainview/mainView.fxml"));
        mainViewController = new MainViewController(player,this, primaryStage,highscoreList);
        loader.setController(mainViewController);
        mainView = loader.load();

        loader = new FXMLLoader(getClass().getResource("/escapeview/escView.fxml"));
        escController = new ESCController(player,this);
        loader.setController(escController);
        escView = loader.load();

        loader = new FXMLLoader(getClass().getResource("/highscoreview/highscoreView.fxml"));
        highscoreController = new HighscoreViewController(player, this, highscoreList);
        loader.setController(highscoreController);
        highscoreView = loader.load();

        gameViewController = new GameViewController(player,this,highscoreList);
        Parent root = mainView;

        scene = new Scene(root);
        primaryStage.setTitle("FIGHT");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop()throws Exception{

        try (OutputStream out = new FileOutputStream("settings.xml")) {
            Properties properties = new Properties();
            properties.setProperty("currentTrack", player.getCurrentTrack().getPath());
            properties.setProperty("hs1Track",highscoreList.get(0).getTrack().getPath());
            properties.setProperty("hs1Name",highscoreList.get(0).getSpielerName());
            properties.setProperty("hs1Score", Integer.toString(highscoreList.remove(0).getScore()));
            properties.setProperty("hs2Track",highscoreList.get(0).getTrack().getPath());
            properties.setProperty("hs2Name",highscoreList.get(0).getSpielerName());
            properties.setProperty("hs2Score", Integer.toString(highscoreList.remove(0).getScore()));
            properties.setProperty("hs3Track",highscoreList.get(0).getTrack().getPath());
            properties.setProperty("hs3Name",highscoreList.get(0).getSpielerName());
            properties.setProperty("hs3Score", Integer.toString(highscoreList.remove(0).getScore()));
            properties.setProperty("hs4Track",highscoreList.get(0).getTrack().getPath());
            properties.setProperty("hs4Name",highscoreList.get(0).getSpielerName());
            properties.setProperty("hs4Score", Integer.toString(highscoreList.remove(0).getScore()));
            properties.setProperty("hs5Track",highscoreList.get(0).getTrack().getPath());
            properties.setProperty("hs5Name",highscoreList.get(0).getSpielerName());
            properties.setProperty("hs5Score", Integer.toString(highscoreList.remove(0).getScore()));

            properties.storeToXML(out,"Settings XML File for FightGame");
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.stop();
        player.stop();

    }

    public void switchView(String viewName){
        switch (viewName){
            case "afterGameView":
                scene.setRoot(afterGameView);
                afterGameController.updateScore();
                break;
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
            case "highscoreView":
                scene.setRoot(highscoreView);
                highscoreController.loadPlaylist(highscoreList);
                break;

            case "ESC":
                StackPane painESC = new StackPane();

                painESC.getChildren().addAll(gameViewController.getGameView(), escView);
                painESC.getChildren().get(1).translateYProperty().set(-1080);
                scene.setRoot(painESC);

                Timeline timelineESC = new Timeline();
                KeyValue kvESC = new KeyValue(painESC.getChildren().get(1).translateYProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kfESC = new KeyFrame(Duration.millis(1000), kvESC);
                timelineESC.getKeyFrames().add(kfESC);

                timelineESC.play();
                break;
            case "ESCback":
                StackPane painESC2 = new StackPane();

                painESC2.getChildren().addAll(gameViewController.getGameView(), escView);
                painESC2.getChildren().get(1).translateYProperty().set(0);
                scene.setRoot(painESC2);

                Timeline timelineESC2 = new Timeline();
                KeyValue kvESC2 = new KeyValue(painESC2.getChildren().get(1).translateYProperty(), -1080, Interpolator.EASE_IN);
                KeyFrame kfESC2 = new KeyFrame(Duration.millis(1000), kvESC2);
                timelineESC2.getKeyFrames().add(kfESC2);
                timelineESC2.setOnFinished(event -> {
                    painESC2.getChildren().remove(1);
                });

                timelineESC2.play();
                getGameViewController().getGameView().requestFocus();

                break;
        }
    }

    public GameViewController getGameViewController() {
        return gameViewController;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
