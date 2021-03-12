package controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Utils {
    public static Icon getIconFromResource(String path) {
        Icon icon = null;

        try {
            Image img = ImageIO.read(Utils.class.getResource("/" + path));
            icon = new ImageIcon(img);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return icon;
    }

    public static Image getImageFromResource(String path) {
        Image img = null;

        try {
            img = ImageIO.read(Utils.class.getResource("/" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }
}
