package pacman.api;

public abstract class RequestCodes {
    public static final int SINGLE_GAME_WITH_GHOST = 0;
    public static final int SINGLE_GAME_WITHOUT_GHOST = 1;
    public static final int VERSUS_GAME_WITH_GHOST = 2;
    public static final int VERSUS_GAME_WITHOUT_GHOST = 3;
    public static final int REQUEST_INFO_FOR_LEFT_PLAYER = 4;
    public static final int REQUEST_INFO_FOR_RIGHT_PLAYER = 5;
    public static final int DISCONNECT_WITHOUT_RESULT = 6;
    public static final int DISCONNECT_WITH_RESULT = 7;
    public static final int CMD_UP = 8;
    public static final int CMD_DOWN = 9;
    public static final int CMD_RIGHT = 10;
    public static final int CMD_LEFT = 11;
    public static final int READY = 12;
}