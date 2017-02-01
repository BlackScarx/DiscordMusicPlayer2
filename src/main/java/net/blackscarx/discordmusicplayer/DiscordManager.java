package net.blackscarx.discordmusicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import net.blackscarx.discordmusicplayer.object.MusicLabel;
import net.blackscarx.discordmusicplayer.utils.AudioPlayerSendHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by BlackScarx on 15-01-17. BlackScarx All right reserved
 */
public class DiscordManager {

    public JDA jda;
    public String guildId;
    public String channelId;
    public AudioPlayerManager remoteManager = new DefaultAudioPlayerManager();
    public AudioPlayerManager localManager = new DefaultAudioPlayerManager();
    public AudioPlayer player = remoteManager.createPlayer();
    public AudioPlayerSendHandler handler = new AudioPlayerSendHandler(player);
    public ObservableList<AudioTrack> playList = FXCollections.observableList(new ArrayList<>());

    public DiscordManager(String token) throws LoginException, InterruptedException, RateLimitedException {
        playList.addListener(new ListChangeListener<AudioTrack>() {
            @Override
            public void onChanged(Change<? extends AudioTrack> c) {
                DiscordMusicPlayer.instance.mainPanel.setPlayList();
            }
        });
        jda = new JDABuilder(AccountType.BOT).setToken(token).setBulkDeleteSplittingEnabled(false).buildBlocking();
        jda.getPresence().setGame(Game.of("powered by DiscordMusicPlayer"));
        AudioSourceManagers.registerRemoteSources(remoteManager);
        AudioSourceManagers.registerLocalSource(localManager);
        player.addListener(new Musique());
    }

    public List<Guild> getGuilds() {
        return jda.getGuilds();
    }

    public List<VoiceChannel> getChannelsGuild(String guildId) {
        return jda.getGuildById(guildId).getVoiceChannels();
    }

    public void connectGuild(String guildId) {
        if (this.guildId != null) {
            disconnectGuild();
        }
        this.guildId = guildId;
        jda.getGuildById(guildId).getAudioManager().setSendingHandler(handler);
    }

    public void disconnectChannel() {
        jda.getGuildById(guildId).getAudioManager().closeAudioConnection();
        channelId = null;
    }

    public void disconnectGuild() {
        if (channelId != null)
            disconnectChannel();
        if (guildId != null) {
            jda.getGuildById(guildId).getAudioManager().setSendingHandler(null);
            guildId = null;
        }
    }

    public void connectChannel(String channelId) throws PermissionException {
        try {
            jda.getGuildById(guildId).getAudioManager().openAudioConnection(jda.getVoiceChannelById(channelId));
            this.channelId = channelId;
        } catch (PermissionException e) {
            throw new PermissionException(e.getPermission(), e.getMessage());
        }
    }

    public void addSource(String source, boolean isRemote, boolean wait) {
        Future<Void> add = !isRemote ? localManager.loadItem(source, new AudioLoad()) : remoteManager.loadItem(source, new AudioLoad());
        if (wait)
            while (!add.isDone()) ;
    }

    public void play() {
        if (player.isPaused() && player.getPlayingTrack() != null) {
            player.setPaused(false);
        } else if (!playList.isEmpty() && player.getPlayingTrack() == null) {
            player.playTrack(playList.remove(0));
        }
    }

    public void pause() {
        if (!player.isPaused() && player.getPlayingTrack() != null) {
            player.setPaused(true);
        }
    }

    public void next() {
        if (player.getPlayingTrack() != null && !playList.isEmpty()) {
            boolean isPaused = player.isPaused();
            player.playTrack(playList.remove(0));
            player.setPaused(isPaused);
        }
    }

    public void stop() {
        if (player.getPlayingTrack() != null)
            player.stopTrack();
        if (!playList.isEmpty())
            playList.clear();
    }

    public void setVolume(int volume) {
        player.setVolume(volume);
    }

    class AudioLoad implements AudioLoadResultHandler {

        @Override
        public void trackLoaded(AudioTrack audioTrack) {
            playList.add(audioTrack);
        }

        @Override
        public void playlistLoaded(AudioPlaylist audioPlaylist) {
            for (AudioTrack audioTrack : audioPlaylist.getTracks()) {
                playList.add(audioTrack);
            }
        }

        @Override
        public void noMatches() {
            System.out.println("No match");
        }

        @Override
        public void loadFailed(FriendlyException e) {
            e.printStackTrace();
        }

    }

    class Musique extends AudioEventAdapter {

        Timer timer;

        @Override
        public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
            if (endReason.equals(AudioTrackEndReason.FINISHED)) {
                if (!playList.isEmpty()) {
                    player.playTrack(playList.remove(0));
                }
            }
        }

        @Override
        public void onTrackStart(AudioPlayer player, AudioTrack track) {
            if (timer == null) {
                timer = new Timer();
                timer.start();
            }
        }

        @Override
        public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
            if (!playList.isEmpty()) {
                player.playTrack(playList.remove(0));
            }
        }

    }

    class Timer extends Thread implements Runnable {

        MusicLabel info = DiscordMusicPlayer.instance.mainPanel.info;
        String noMusic = DiscordMusicPlayer.instance.lang.noMusic;
        String title;

        public void setTitle(AudioTrack audioTrack) {
            title = !audioTrack.getInfo().title.equals("Unknown title") ? audioTrack.getInfo().title : new File(audioTrack.getIdentifier()).getName();
        }

        @Override
        public void run() {
            int i = 0;
            while (DiscordMusicPlayer.instance.mainPanel.panel.isVisible()) {
                if (player.getPlayingTrack() != null) {
                    setTitle(player.getPlayingTrack());
                    String text = title + "                    ";
                    if (text.length() <= i)
                        i = 0;
                    StringBuilder builder = new StringBuilder(text + new StringBuilder(text).substring(0, i));
                    info.setText(builder.substring(i, 35 + i), "   |   " + getFormattedTime() + " ");
                    i++;
                } else {
                    info.setText(noMusic, "");
                }
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            info.setText(noMusic, "");
            this.interrupt();
        }

        public String getFormattedTime() {
            if (player.getPlayingTrack() == null)
                return "";
            AudioTrack track = player.getPlayingTrack();
            Date pos = new Date(track.getPosition());
            Date dur = new Date(track.getDuration());
            DateFormat format = new SimpleDateFormat("H:m:ss");
            String posString = format.format(pos);
            String durString = format.format(dur);
            DateFormat h = new SimpleDateFormat("H");
            if (Integer.valueOf(h.format(dur)) == 1) {
                durString = durString.replaceFirst("1:", "");
                posString = posString.replaceFirst("1:", "");
            } else {
                durString = durString.replaceFirst(h.format(dur), String.valueOf((Integer.valueOf(h.format(dur)) - 1)));
                posString = posString.replaceFirst(h.format(pos), String.valueOf((Integer.valueOf(h.format(pos)) - 1)));
            }

            return posString + "/" + durString;
        }
    }

}
