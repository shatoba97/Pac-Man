// Среда в 12:00 в 420

import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, PacManAPI.GameType.SINGLE)) {
            api.toRight();
            api.toLeft();
            api.toUp();
            api.toDown();

            api.getInfoAboutLeftPlayer();

            api.disconnect(false);
        }
    }
}