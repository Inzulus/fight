package player;

import ddf.minim.AudioListener;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import javafx.beans.property.SimpleBooleanProperty;

//Die BeatListener Klasse die mit Hilfe des Minim Beatdetect bei jedem Beat die isKickProperty auf  kurz true setzt
public class BeatListener implements AudioListener
{
    private BeatDetect beat;
    private AudioPlayer source;
    private SimpleBooleanProperty isKickProperty;

    BeatListener(BeatDetect beat, AudioPlayer source) {
        this.source = source;
        this.source.addListener(this);
        this.beat = beat;
        isKickProperty = new SimpleBooleanProperty();
    }

    public void samples(float[] samps)
    {
        beat.detect(source.mix);
    }

    //Diese Methode wird vom Audiolistener jedes sample automatisch aufgerufen und prüft ob in dem Sample ein Beat vorhanden ist.
    public void samples(float[] sampsL, float[] sampsR) {
        beat.detect(source.mix);
        isKickProperty.set(beat.isKick());
    }

    public SimpleBooleanProperty getIsKickProperty(){
        return isKickProperty;
    }
}
