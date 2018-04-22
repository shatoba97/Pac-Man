import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.client.SettingGameWindow;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070,
                PacManAPI.GameType.SINGLE_WITHOUT_GHOST)) {
            while (true) {
                GameInfo gameInfo = api.getInfoAboutLeftPlayer();
                if (gameInfo.pacMan.score != 500) {
                    api.toDown();
                } else {
                    api.disconnect(false);
                    System.exit(0);
                }
            }
        }
    }
}