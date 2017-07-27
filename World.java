package gosen;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class World {
    public abstract void loadMedia() throws IOException;
    
    public abstract void draw(GraInfo ginfo);

    public abstract void init(GraInfo ginfo);
}
