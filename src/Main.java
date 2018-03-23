import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.bot.PacManRandomBot;
import pacman.bot.PacManSearchBot;
import pacman.client.BotViewWindow;
import pacman.client.PeopleViewWindow;

public class Main {
    public static void main(String[] args) throws Exception {
        //randomBot(PacManAPI.GameType.VERSUS);
        //searchBot(PacManAPI.GameType.SINGLE);
        people(PacManAPI.GameType.SINGLE);
    }

    private static void randomBot(PacManAPI.GameType gameType) {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, gameType)) {
            new BotViewWindow(api, gameType);
            new PacManRandomBot(api).start();
        }
    }

    private static void searchBot(PacManAPI.GameType gameType) {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, gameType)) {
            new BotViewWindow(api, gameType);
            new PacManSearchBot(api).start();
        }
    }

    private static void people(PacManAPI.GameType gameType) {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, gameType)) {
            new PeopleViewWindow(api, gameType);
        }
    }
}