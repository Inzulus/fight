package highscoreview;

import application.Main;
import gameview.Highscore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import player.MP3Player;

import java.util.ArrayList;

public class HighscoreViewController {

    private MP3Player player;
    private Main application;
    private ArrayList<Highscore> highscoreList = new ArrayList<>();

    //FXML Ressources:

    @FXML
    private Label songtitel = new Label();

    @FXML
    private Button playButton = new Button();

    @FXML
    private Button homeButton = new Button();

    @FXML
    private ListView<Highscore> listview = new ListView();


    //Kontruktor:
    public HighscoreViewController(MP3Player player, Main application, ArrayList<Highscore> highscoreList) {
        this.player = player;
        this.application = application;
        this.highscoreList = highscoreList;
        loadPlaylist(highscoreList);
    }

    //TODO label müssen Infomationen aus dem HighscoreObjekt holen
    //FXML Methoden:

    @FXML
    public void playSong () {
        player.playWithBeatThread();
    }

    @FXML
    public void menue () {
        application.switchView("mainView");

    }

    public void loadPlaylist(ArrayList<Highscore> highscoreList){
        ObservableList<Highscore> observableList = FXCollections.observableArrayList();

        for(int i = 0;i<highscoreList.size();i++){
            observableList.add(highscoreList.get(i));
        }

        listview.setItems(observableList);
        listview.setCellFactory(new Callback<javafx.scene.control.ListView<Highscore>, ListCell<Highscore>>() {
            @Override
            public ListCell<Highscore> call(javafx.scene.control.ListView<Highscore> param) {
                return new HighscoreCell();
            }
        });
    }

}
