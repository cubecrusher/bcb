package cube.basedcount.bot.commands;

import java.util.HashMap;
import java.util.Map;

public enum Flair {
    UNFLAIRED(0),
    GRAYCENTRIST(1),
    COLOREDCENTRIST(2),
    AUTHLEFT(3),
    AUTHCENTER(4),
    AUTHRIGHT(5),
    CENTERRIGHT(6),
    YELLOWLIBRIGHT(7),
    PURPLELIBRIGHT(8),
    LIBCENTER(9),
    LIBLEFT(10),
    CENTERLEFT(11);

    private int value;
    private static Map map = new HashMap<>();

    private Flair(int value) {
        this.value = value;
    }

    static {
        for (Flair pageType : Flair.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static Flair valueOf(int pageType) {
        return (Flair) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}