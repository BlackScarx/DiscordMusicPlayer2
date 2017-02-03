package net.blackscarx.discordmusicplayer.object;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackScarx on 02-02-17. BlackScarx All right reserved
 */
public class Playlist implements Serializable {

    public List<Properties> playlist = new ArrayList<>();

    public Playlist() {
    }

    public Playlist(List<AudioTrack> audioTracks) {
        for (AudioTrack track : audioTracks) {
            playlist.add(new Properties(track.getInfo().identifier.startsWith("http"), track.getInfo().identifier));
        }
    }

    public class Properties implements Serializable {

        public Boolean isRemote;
        public String identifier;

        public Properties(Boolean isRemote, String identifier) {
            this.isRemote = isRemote;
            this.identifier = identifier;
        }

    }

}
