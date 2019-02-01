package highscoreview;

import gameview.Highscore;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.logging.LoggingMXBean;

public class HighscoreCell extends ListCell<Highscore> {

    private Pane view;
    private Label platzierung;
    private Label score;
    private Label name;
    private static int highscoreCount = 0;

    public HighscoreCell () {
        HBox cellKasten = new HBox();
        VBox tabelle = new VBox();

        platzierung = new Label();
        score = new Label();
        name = new Label();
        view = new Pane();

        //tabelle.getChildren().addAll(score, name);
        cellKasten.setAlignment(Pos.CENTER);
        cellKasten.setSpacing(20);
        cellKasten.getChildren().addAll(platzierung, score,name);

        view.getChildren().add(cellKasten);
        this.setGraphic(view);
    }

    @Override
    protected void updateItem(Highscore highscore, boolean empty) {

        super.updateItem(highscore, empty);

        if (highscore != null) {
            score.setText(String.valueOf(highscore.getScore()));
            name.setText(highscore.getSpielerName());

            //TODO sortierte Liste mit, um Platzierungen festzusetzen
            platzierung.setText(String.valueOf(highscoreCount++));
            setGraphic(view);
        } else {
            setGraphic(null);
        }
    }
}
