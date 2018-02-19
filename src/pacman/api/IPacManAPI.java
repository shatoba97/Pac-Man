package pacman.api;

public interface IPacManAPI {

    boolean connection(String IP, int port, PacManAPI.GameType gameType);

    boolean chooseGameRoom(int id);

    boolean toUp();
    boolean toDown();
    boolean toLeft();
    boolean toRight();

    GameInfo disconnect(boolean isWait);
    GameInfo getInfoAboutLeftPlayer();
    GameInfo getInfoAboutRightPlayer();
    GameInfo getPlayingGameRoomList();
}