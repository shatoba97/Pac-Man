package pacman.api;

public interface IPacManAPI {

    boolean connection(String IP, int port, int gameType);
    GameInfo disconnect(boolean isWait);

    GameInfo sendRequest(int requestCode);

    void toUp();
    void toDown();
    void toLeft();
    void toRight();
}