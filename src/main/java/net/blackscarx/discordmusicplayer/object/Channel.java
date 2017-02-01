package net.blackscarx.discordmusicplayer.object;

/**
 * Created by BlackScarx on 16-01-17. BlackScarx All right reserved
 */
public class Channel {

    public String name;
    public String channelId;

    public Channel(String name, String channelId) {
        this.name = name;
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return name;
    }
}
