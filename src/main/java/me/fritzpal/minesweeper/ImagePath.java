package me.fritzpal.minesweeper;


import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

public final class ImagePath {
    public static HashMap<String, Image> IMAGES = new HashMap<>();

    static{
        IMAGES.put("1.png", getResource("1.png"));
        IMAGES.put("2.png", getResource("2.png"));
        IMAGES.put("3.png", getResource("3.png"));
        IMAGES.put("4.png", getResource("4.png"));
        IMAGES.put("5.png", getResource("5.png"));
        IMAGES.put("6.png", getResource("6.png"));
        IMAGES.put("7.png", getResource("7.png"));
        IMAGES.put("8.png", getResource("8.png"));
        IMAGES.put("mine.png", getResource("mine.png"));
        IMAGES.put("flag.png", getResource("flag.png"));
        IMAGES.put("wrong.png", getResource("wrong.png"));
        IMAGES.put("covered.png", getResource("covered.png"));
    }
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