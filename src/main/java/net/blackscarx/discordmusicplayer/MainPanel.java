package net.blackscarx.discordmusicplayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.theshark34.swinger.Swinger;
import net.blackscarx.discordmusicplayer.object.Channel;
import net.blackscarx.discordmusicplayer.object.Guild;
import net.blackscarx.discordmusicplayer.object.MusicLabel;
import net.blackscarx.discordmusicplayer.utils.JImage;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackScarx on 15-01-17. BlackScarx All right reserved
 */
public class MainPanel extends JPanel implements ActionListener, ItemListener, ChangeListener, MouseListener {

    public DiscordMusicPlayer instance = DiscordMusicPlayer.instance;
    public DiscordManager manager;
    public Image orBackground;
    public Image background;
    public JTextField tokenField = new JTextField();
    public JButton login = new JButton(instance.lang.login);
    public JButton logout = new JButton(instance.lang.logout);
    public JImage status = new JImage(Swinger.getResource("red.png"));
    public JButton setting = new JButton(new ImageIcon(Swinger.getResource("settings.png")));
    public JComboBox<Guild> guild = new JComboBox<>();
    public JButton addGuild = new JButton(new ImageIcon(Swinger.getResource("addserver.png")));
    public JButton refreshGuild = new JButton(new ImageIcon(Swinger.getResource("refresh.png")));
    public JComboBox<Channel> channel = new JComboBox<>();
    public JButton go = new JButton("Go");
    public JPanel panel = new JPanel(null);
    public JTextField link = new JTextField();
    public JButton fileEx = new JButton(new ImageIcon(Swinger.getResource("file.png")));
    public JButton addFile = new JButton(new ImageIcon(Swinger.getResource("addserver.png")));
    public JSlider volume = new JSlider(0, 100, 100);
    public JList<AudioTrack> list = new JList<>();
    public JScrollPane scrollPane = new JScrollPane(list);
    public JButton play = new JButton(new ImageIcon(Swinger.getResource("play.png")));
    public JButton pause = new JButton(new ImageIcon(Swinger.getResource("pause.png")));
    public JButton next = new JButton(new ImageIcon(Swinger.getResource("next.png")));
    public MusicLabel info = new MusicLabel(instance.lang.noMusic, "");
    public JMenuItem deleteOption = new JMenuItem(instance.lang.deleteOption);

    public MainPanel() {
        this.orBackground = Swinger.getResource("debian.jpg");
        background = orBackground.getScaledInstance(960, 540, 0);
        this.setLayout(new BorderLayout());

        JPanel pageStart = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pageStart.setOpaque(false);
        tokenField.setPreferredSize(new Dimension(500, 25));
        login.setOpaque(false);
        login.setFocusable(false);
        login.addActionListener(this);
        logout.setOpaque(false);
        logout.setFocusable(false);
        logout.addActionListener(this);
        logout.setVisible(false);
        status.setEnabled(false);
        status.setPreferredSize(new Dimension(25, 25));
        status.setOpaque(false);
        setting.setOpaque(false);
        setting.setFocusable(false);
        setting.addActionListener(this);
        setting.setPreferredSize(new Dimension(25, 25));
        pageStart.add(tokenField);
        pageStart.add(login);
        pageStart.add(logout);
        pageStart.add(status);
        pageStart.add(setting);
        this.add(pageStart, BorderLayout.PAGE_START);
        this.add(panel, BorderLayout.CENTER);
        panel.setOpaque(false);
        panel.setVisible(false);

        guild.setOpaque(false);
        guild.setFocusable(false);
        guild.addItemListener(this);
        guild.setBounds(5, 5, 200, 25);
        addGuild.setOpaque(false);
        addGuild.setFocusable(false);
        addGuild.addActionListener(this);
        addGuild.setBounds(210, 5, 25, 25);
        refreshGuild.setOpaque(false);
        refreshGuild.setFocusable(false);
        refreshGuild.addActionListener(this);
        refreshGuild.setBounds(240, 5, 25, 25);
        channel.setOpaque(false);
        channel.setFocusable(false);
        channel.addItemListener(this);
        channel.setBounds(270, 5, 200, 25);
        go.setOpaque(false);
        go.setFocusable(false);
        go.addActionListener(this);
        go.setBounds(475, 5, 45, 25);
        link.setBounds(540, 5, 350, 25);
        fileEx.setOpaque(false);
        fileEx.setBounds(895, 5, 25, 25);
        fileEx.setFocusable(false);
        fileEx.addActionListener(this);
        addFile.setOpaque(false);
        addFile.setBounds(925, 5, 25, 25);
        addFile.setFocusable(false);
        addFile.addActionListener(this);
        volume.setOpaque(false);
        volume.setFocusable(false);
        volume.addChangeListener(this);
        volume.setBounds(540, 65, 410, 25);
        scrollPane.setBounds(540, 95, 410, 370);
        list.setFocusable(false);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.addMouseListener(this);
        list.setCellRenderer(new ListCellRenderer<AudioTrack>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends AudioTrack> list, AudioTrack value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) new DefaultListCellRenderer().getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setText((index + 1) + ". " + (!value.getInfo().title.equals("Unknown title") ? value.getInfo().title : new File(value.getIdentifier()).getName()));
                label.setPreferredSize(new Dimension(WIDTH, 25));
                label.setFont(label.getFont().deriveFont(12f));
                return label;
            }
        });
        play.setOpaque(false);
        play.setFocusable(false);
        play.addActionListener(this);
        play.setBounds(540, 35, 25, 25);
        pause.setOpaque(false);
        pause.setFocusable(false);
        pause.addActionListener(this);
        pause.setBounds(570, 35, 25, 25);
        next.setOpaque(false);
        next.setFocusable(false);
        next.addActionListener(this);
        next.setBounds(600, 35, 25, 25);
        info.setBounds(630, 35, 320, 25);

        deleteOption.addActionListener(this);

        panel.add(guild);
        panel.add(addGuild);
        panel.add(refreshGuild);
        panel.add(channel);
        panel.add(go);
        panel.add(link);
        panel.add(fileEx);
        panel.add(addFile);
        panel.add(volume);
        panel.add(scrollPane);
        panel.add(play);
        panel.add(pause);
        panel.add(next);
        info.add(panel);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(login)) {
            try {
                manager = new DiscordManager(tokenField.getText());
                setGuild();
                status.setTexture(Swinger.getResource("green.png"));
                login.setEnabled(false);
                login.setVisible(false);
                logout.setVisible(true);
                logout.setEnabled(true);
                tokenField.setEnabled(false);
                panel.setVisible(true);
            } catch (LoginException | RateLimitedException | InterruptedException e1) {
                e1.printStackTrace();
                if (e1 instanceof LoginException) {
                    JOptionPane.showMessageDialog(this, instance.lang.errorLogin, instance.lang.error, JOptionPane.WARNING_MESSAGE);
                }
            }
        } else if (e.getSource().equals(logout)) {
            if (manager.channelId != null) {
                manager.disconnectChannel();
            }
            if (manager.guildId != null) {
                manager.disconnectGuild();
            }
            list.removeAll();
            volume.setValue(100);
            panel.setVisible(false);
            guild.removeAllItems();
            channel.removeAllItems();
            manager.jda.shutdown(false);
            status.setTexture(Swinger.getResource("red.png"));
            logout.setEnabled(false);
            logout.setVisible(false);
            login.setEnabled(true);
            login.setVisible(true);
            tokenField.setEnabled(true);
        } else if (e.getSource().equals(go)) {
            try {
                manager.connectChannel(((Channel) channel.getSelectedItem()).channelId);
            } catch (PermissionException e1) {
                JOptionPane.showMessageDialog(this, instance.lang.errorPermission, instance.lang.error, JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource().equals(fileEx)) {
            JFileChooser chooser = new JFileChooser(new File("."));
            chooser.setMultiSelectionEnabled(true);
            chooser.resetChoosableFileFilters();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("Multimedia", "mp3", "mp4", "flac", "aac", "mkv", "webm", "m4a", "ogg", "m3u", "pls"));
            chooser.setDialogTitle(instance.lang.chooseFileTitle);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                for (File file : chooser.getSelectedFiles()) {
                    manager.addSource(file.getPath(), false, true);
                }
            }
        } else if (e.getSource().equals(addFile)) {
            manager.addSource(link.getText(), true, true);
        } else if (e.getSource().equals(refreshGuild)) {
            this.guild.removeAllItems();
            setGuild();
        } else if (e.getSource().equals(addGuild)) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("https://discordapp.com/api/oauth2/authorize?client_id=" + manager.jda.getSelfUser().getId() + "&scope=bot&permissions=0"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (e.getSource().equals(play)) {
            manager.play();
        } else if (e.getSource().equals(pause)) {
            manager.pause();
        } else if (e.getSource().equals(next)) {
            manager.next();
        } else if (e.getSource().equals(deleteOption)) {
            manager.playList.removeAll(list.getSelectedValuesList());
        }
    }

    public void setGuild() {
        for (net.dv8tion.jda.core.entities.Guild g : manager.getGuilds()) {
            List<Channel> list = new ArrayList<>();
            for (VoiceChannel vc : g.getVoiceChannels()) {
                list.add(new Channel(vc.getName(), vc.getId()));
            }
            Guild guild = new Guild(g.getName(), g.getId(), list);
            this.guild.addItem(guild);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(guild)) {
            if (!(manager.guildId != null && manager.guildId.equals(((Guild) e.getItem()).guildId))) {
                manager.connectGuild(((Guild) e.getItem()).guildId);
                channel.removeAllItems();
                for (Channel c : ((Guild) e.getItem()).channelList) {
                    channel.addItem(c);
                }
            }
        }
    }

    public void setPlayList() {
        list.setListData(manager.playList.toArray(new AudioTrack[]{}));
        link.setText("");
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(volume)) {
            manager.setVolume(volume.getValue());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(list)) {
            if (SwingUtilities.isRightMouseButton(e)) {
                JPopupMenu menu = new JPopupMenu();
                menu.add(deleteOption);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
