import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.bot.PacManSearchBotV2;
import pacman.bot.PacManRandomBot;
import pacman.client.BotViewWindow;
import pacman.client.PeopleViewWindow;
import pacman.client.SettingGameWindow;

public class Main {
    public static void main(String[] args) throws Exception {
        new SettingGameWindow();
    }

    private static void randomBot(PacManAPI.GameType gameType) {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, gameType)) {
            new BotViewWindow(api, gameType);
            new PacManRandomBot(api).start();
        }
    }

    private static void searchBotV2(PacManAPI.GameType gameType) {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, gameType)) {
            new BotViewWindow(api, gameType);
            new PacManSearchBotV2(api).start();
        }
    }

    private static void people(PacManAPI.GameType gameType) {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, gameType)) {
            new PeopleViewWindow(api, gameType);
        }
    }
}