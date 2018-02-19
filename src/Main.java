import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, PacManAPI.GameType.SINGLE)) {
            if (api.toUp()) {
                System.out.println("Команда отправлена");
            } else {
                System.out.println("Команда не отправлена");
            }

            GameInfo gameInfo = api.getInfoAboutLeftPlayer();
            System.out.println(gameInfo.pacman.score);
            System.out.println(gameInfo.ghosts.get(0).x);

            api.disconnect(false);
        }
    }
}