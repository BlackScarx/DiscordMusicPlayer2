package net.blackscarx.discordmusicplayer.object;

import javax.swing.*;
import java.awt.*;

/**
 * Created by BlackScarx on 01-02-17. BlackScarx All right reserved
 */
public class MusicLabel {

    public JLabel l = new JLabel();
    public JLabel r = new JLabel();

    public MusicLabel(String l, String r) {
        this.l.setText(l);
        this.r.setText(r);
        this.l.setHorizontalAlignment(SwingConstants.LEFT);
        this.r.setHorizontalAlignment(SwingConstants.RIGHT);
        Font font = new Font("Arial", Font.BOLD, 12);
        this.l.setFont(font);
        this.r.setFont(font);
        this.l.setForeground(Color.WHITE);
        this.r.setForeground(Color.WHITE);
    }

    public void setBounds(int x, int y, int width, int height) {
        l.setBounds(x, y, width, height);
        r.setBounds(x, y, width, height);
    }

    public void add(JComponent component) {
        component.add(l);
        component.add(r);
    }

    public void setText(String l, String r) {
        this.l.setText(l);
        this.r.setText(r);
    }

}
