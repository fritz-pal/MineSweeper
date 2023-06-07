package me.fritzpal.minesweeper;


import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;

public final class ImagePath {
    public static Image getResource(String file) {
        Image image = null;
        try {
            URL resource = ImagePath.class.getResource("/" + file);
            if (resource == null) throw new Exception("Resource not found: " + file);
            image = ImageIO.read(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}