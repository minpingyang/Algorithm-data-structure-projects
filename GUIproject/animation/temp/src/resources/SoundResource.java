package resources;

import java.applet.Applet;

/**
 * Created by minpingyang on 27/08/17.
 */
public enum SoundResource {
    DISAPPEAR("disappear.wav");

    public java.applet.AudioClip sound;
    SoundResource(String resourceName){
        sound= Applet.newAudioClip(getClass().getResource(resourceName));

    }

}
