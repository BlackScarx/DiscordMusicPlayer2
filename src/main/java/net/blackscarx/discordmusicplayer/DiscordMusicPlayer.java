package net.blackscarx.discordmusicplayer;

import com.google.gson.Gson;
import fr.theshark34.swinger.Swinger;
import net.blackscarx.discordmusicplayer.utils.Lang;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

/**
 * Created by BlackScarx on 12-01-17. BlackScarx All right reserved
 */
public class DiscordMusicPlayer {

    public static DiscordMusicPlayer instance;
    public static Logger logger = Logger.getLogger("DiscordMusicPlayer");
    public MainFrame mainFrame;
    public MainPanel mainPanel;
    public Lang lang;

    public static void main(String[] args) throws UnsupportedEncodingException {
        String lang = System.getProperty("user.language");
        InputStream langIS;
        if ((langIS = DiscordMusicPlayer.class.getResourceAsStream("/lang/" + lang + ".json")) == null) {
            logger.warning(lang + " is not supported by DiscordMusicPlayer");
            logger.info("See how to add your language on the wiki");
            langIS = DiscordMusicPlayer.class.getResourceAsStream("/lang/en.json");
        }
        Gson gson = new Gson();
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/");
        instance = new DiscordMusicPlayer();
        instance.lang = gson.fromJson(new InputStreamReader(langIS, "UTF-8"), Lang.class);
        instance.mainPanel = new MainPanel();
        instance.mainFrame = new MainFrame();
    }

}
