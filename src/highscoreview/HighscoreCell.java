package highscoreview;

import gameview.Highscore;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class HighscoreCell extends ListCell<Highscore> {

    private Pane view;
    private Label platzierung;
    private Label score;
    private Label name;

    public HighscoreCell () {
        HBox cellKasten = new HBox();

        platzierung = new Label();
        score = new Label();
        name = new Label();
        Font font = new Font("System",30);
        name.setFont(font);
        score.setFont(font);
        view = new Pane();

        cellKasten.setAlignment(Pos.CENTER);
        cellKasten.setSpacing(20);
        cellKasten.getChildren().addAll(platzierung, score,name);

        view.getChildren().add(cellKasten);
        this.setStyle("-fx-background-color: transparent");
        this.setAlignment(Pos.CENTER);
        this.setGraphic(view);
    }

    @Override
    protected void updateItem(Highscore highscore, boolean empty) {

        super.updateItem(highscore, empty);

        if (highscore != null) {
            score.setText(String.valueOf(highscore.getScore()));
            name.setText(highscore.getSpielerName());
            setGraphic(view);
        } else {
            setGraphic(null);
        }
    }
}
