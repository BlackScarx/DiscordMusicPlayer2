package net.blackscarx.discordmusicplayer.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackScarx on 19-04-17. BlackScarx All right reserved
 */
public class Alpha {

    public static List<String> list = new ArrayList<>();

    static {

        String[] alpha = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        for (String s : alpha) {
            list.add(s);
        }

    }

}
