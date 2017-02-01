package net.blackscarx.discordmusicplayer.object;

import java.util.List;

/**
 * Created by BlackScarx on 16-01-17. BlackScarx All right reserved
 */
public class Guild {

    public String name;
    public String guildId;
    public List<Channel> channelList;

    public Guild(String name, String guildId, List<Channel> channelList) {
        this.name = name;
        this.guildId = guildId;
        this.channelList = channelList;
    }

    @Override
    public String toString() {
        return name;
    }
}
