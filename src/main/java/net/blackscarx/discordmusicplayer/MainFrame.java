package net.blackscarx.discordmusicplayer;

import fr.theshark34.swinger.Swinger;
import net.blackscarx.discordmusicplayer.utils.Utils;

import javax.swing.*;

/**
 * Created by BlackScarx on 15-01-17. BlackScarx All right reserved
 */
public class MainFrame extends JFrame {

    public MainFrame() {

        this.setName("DiscordMusicPlayer");
        this.setSize(960, 540);
        this.setLocationRelativeTo(null);
        this.setIconImage(Utils.makeRoundedCorner(Swinger.getResource("icon.png"), 15));
        this.setTitle("DiscordMusicPlayer");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(DiscordMusicPlayer.instance.mainPanel);
        this.setVisible(true);

    }

}
