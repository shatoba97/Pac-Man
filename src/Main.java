import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.bot.PacManBot2;
import pacman.client.BotViewWindow;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, PacManAPI.GameType.VERSUS)) {
            new BotViewWindow(api, PacManAPI.GameType.VERSUS);
            new PacManBot2(api).start();
        }
    }
}

