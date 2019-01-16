package player;

import java.util.EventObject;

public class InfoEvent extends EventObject {
    private Track track;

    public InfoEvent(Object source, Track track) {
        super(source);
        this.track = track;
    }

    public Track getTrack(){
        return track;
    }
}
