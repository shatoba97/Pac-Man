import pacman.api.IBot;
import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.api.RequestCodes;
import pacman.bot.MyBot;

public class Main {
    public static void main(String[] args) {
        IPacManAPI pacManAPI = new PacManAPI();
        if (pacManAPI.connection("127.0.0.1", 7070, RequestCodes.SINGLE_GAME)) {
            IBot bot = new MyBot();
            bot.exec(pacManAPI);
        }
    }
}