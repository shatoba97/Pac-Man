package pacman.api;

abstract class RequestCodes {
    static int SINGLE_GAME = 0;
    static int VERSUS_GAME = 1;
    static int VIEW_GAME = 2;
    static int REQUEST_INFO_FOR_LEFT_PLAYER = 3;
    static int REQUEST_INFO_FOR_RIGHT_PLAYER = 4;
    static int REQUEST_LIST_OF_GAME_ROOM = 5;
    static int NOTIFY_SERVER_ABOUT_CHOOSE_GAME_ROOM_ID = 6;
    static int DISCONNECT_WITHOUT_RESULT = 7;
    static int DISCONNECT_WITH_RESULT = 8;
    static int CMD_UP = 9;
    static int CMD_DOWN = 10;
    static int CMD_RIGHT = 11;
    static int CMD_LEFT = 123;
}