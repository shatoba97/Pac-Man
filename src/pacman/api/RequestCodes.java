package pacman.api;

public abstract class RequestCodes {
    public static final int SINGLE_GAME = 0;
    public static final int VERSUS_GAME = 1;
    public static final int VIEW_GAME = 2;
    public static final int REQUEST_INFO_FOR_LEFT_PLAYER = 3;
    public static final int REQUEST_INFO_FOR_RIGHT_PLAYER = 4;
    public static final int REQUEST_LIST_OF_GAME_ROOM = 5;
    public static final int NOTIFY_SERVER_ABOUT_CHOOSE_GAME_ROOM_ID = 6;
    public static final int DISCONNECT_WITHOUT_RESULT = 7;
    public static final int DISCONNECT_WITH_RESULT = 8;
    public static final int CMD_UP = 9;
    public static final int CMD_DOWN = 10;
    public static final int CMD_RIGHT = 11;
    public static final int CMD_LEFT = 12;
}