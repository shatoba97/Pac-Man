package pacman.api;

public abstract class RequestCodes {
    public static int SINGLE_GAME = 0;
    public static int VERSUS_GAME = 1;
    public static int VIEW_GAME = 2;
    public static int REQUEST_INFO_FOR_LEFT_PLAYER = 3;
    public static int REQUEST_INFO_FOR_RIGHT_PLAYER = 4;
    public static int REQUEST_LIST_OF_GAME_ROOM = 5;
    public static int NOTIFY_SERVER_ABOUT_CHOOSE_GAME_ROOM_ID = 6;
    public static int DISCONNECT_WITHOUT_RESULT = 7;
    public static int DISCONNECT_WITH_RESULT = 8;
    public static int CMD_UP = 9;
    public static int CMD_DOWN = 10;
    public static int CMD_RIGHT = 11;
    public static int CMD_LEFT = 123;
}