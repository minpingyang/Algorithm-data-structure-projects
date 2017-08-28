package resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public enum ImageResource {
    ME1("menu.jpg"),
    LEFT("left.png"),
    RIGHT("right.png");


    public Image img;

    ImageResource(String resourceName){
        try{ img = ImageIO.read(ImageResource.class.getResource(resourceName)); }
        catch (IOException e){ throw new Error(e); }
    }
}
