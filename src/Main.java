import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.client.PeoplePlayerViewWindow;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, PacManAPI.GameType.SINGLE)) {
            new PeoplePlayerViewWindow(api);
        }
    }
}