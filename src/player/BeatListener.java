package player;

import ddf.minim.*;
import ddf.minim.analysis.BeatDetect;
import javafx.beans.property.SimpleBooleanProperty;

public class BeatListener implements AudioListener
{
    private BeatDetect beat;
    private AudioPlayer source;
    private SimpleBooleanProperty isKickProperty;

    BeatListener(BeatDetect beat, AudioPlayer source)
    {
        this.source = source;
        this.source.addListener(this);
        this.beat = beat;
        isKickProperty = new SimpleBooleanProperty();
    }

    public void samples(float[] samps)
    {
        beat.detect(source.mix);
    }

    public void samples(float[] sampsL, float[] sampsR)
    {
        beat.detect(source.mix);
        isKickProperty.set(beat.isKick());
    }

    public BeatDetect getBeatDetect(){
        return beat;
    }

    public SimpleBooleanProperty getIsKickProperty(){
        return isKickProperty;
    }
}
