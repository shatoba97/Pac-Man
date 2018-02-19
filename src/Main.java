import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, PacManAPI.GameType.SINGLE)) {
            api.toDown();
            api.toLeft();
            api.toRight();
            api.toUp();
            api.chooseGameRoom(5);
            api.getInfoAboutLeftPlayer();
            api.getInfoAboutRightPlayer();
            api.getPlayingGameRoomList();
            api.disconnect(false);
        }
    }
}