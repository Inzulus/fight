package player;

import javafx.beans.property.SimpleIntegerProperty;

public class TimeProperty {
    private SimpleIntegerProperty time = new SimpleIntegerProperty();


    //SETTER:
    public void setTime(int time) {
        this.time.set(time);
    }


    //GETTER:
    public int getTime() {
        return time.get();
    }

    public SimpleIntegerProperty timeProperty() {
        return time;
    }
}
