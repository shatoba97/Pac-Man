import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.bot.PacManBot;
import pacman.client.BotViewWindow;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, PacManAPI.GameType.VERSUS)) {
            new BotViewWindow(api, PacManAPI.GameType.VERSUS);
            new PacManBot(api).start();
        }
    }
}

