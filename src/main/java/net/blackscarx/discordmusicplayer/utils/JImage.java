package net.blackscarx.discordmusicplayer.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by BlackScarx on 16-01-17. BlackScarx All right reserved
 */
public class JImage extends JComponent {

    public Image texture;

    public JImage(Image texture) {
        this.texture = texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(texture, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
