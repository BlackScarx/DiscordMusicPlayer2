package net.blackscarx.discordmusicplayer.utils;

import net.blackscarx.discordmusicplayer.object.Playlist;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * Created by BlackScarx on 15-01-17. BlackScarx All right reserved
 */
public class Utils {

    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

    public static void savePlaylist(Playlist playlist, File file) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream object = new ObjectOutputStream(out);
            object.writeObject(playlist);
            object.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Playlist loadPlaylist(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream object = new ObjectInputStream(in);
            Object playlist = object.readObject();
            if (playlist instanceof Playlist) {
                object.close();
                in.close();
                return (Playlist) playlist;
            } else {
                object.close();
                in.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Playlist();
    }

    public static int showDialog(String message, String title) {
        try {
            JImage image = new JImage(ImageIO.read(new URL(message)));
            image.setSize(480, 360);
            image.setPreferredSize(new Dimension(480, 360));
            return JOptionPane.showConfirmDialog(null, image, title, JOptionPane.YES_NO_CANCEL_OPTION);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 2;
    }

}
