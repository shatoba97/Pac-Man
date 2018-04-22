package pacman.api;

/**
 * @author Maxim Rozhkov
 */
public interface IPacManAPI {

    boolean connection(String IP, int port, PacManAPI.GameType gameType);

    boolean toUp();
    boolean toDown();
    boolean toLeft();
    boolean toRight();

    GameInfo disconnect(boolean isWait);
    GameInfo getInfoAboutLeftPlayer();
    GameInfo getInfoAboutRightPlayer();
}