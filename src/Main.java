import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;
import pacman.api.ViewProperties;
import pacman.client.BotViewWindow;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        IPacManAPI api = new PacManAPI();
        if (api.connection("127.0.0.1", 7070, PacManAPI.GameType.SINGLE)) {
            new BotViewWindow(api);
            new PacManBot(api).start();
        }
    }
}

class PacManBot extends Thread {

    private IPacManAPI api;
    private Random random = new Random();

    PacManBot(IPacManAPI api) {
        this.api = api;
    }

    @Override
    public void run() {
        api.toUp();
        int direct;
        ViewProperties viewProperties = api.getViewProperties();
        while (true) {
            GameInfo gameInfo = api.getInfoAboutLeftPlayer();
            int x = (gameInfo.pacman.x + viewProperties.weightRect / 2)
                    / viewProperties.weightRect;
            int y = (gameInfo.pacman.y + viewProperties.heightRect / 2)
                    / viewProperties.heightRect;

            if (changeDirection(x, y, gameInfo.pacman.direction, gameInfo.map)) {
                direct = randomDirect(x, y, gameInfo.pacman.direction, gameInfo.map);
                switch (direct) {
                    case 0:
                        api.toLeft();
                        System.out.println("Влево");
                        break;
                    case 1:
                        api.toUp();
                        System.out.println("Вверх");
                        break;
                    case 2:
                        api.toRight();
                        System.out.println("Вправо");
                        break;
                    case 3:
                        System.out.println("Вниз");
                        api.toDown();
                        break;
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
    }

    private int randomDirect(int x, int y, int prevDirect, int[][] map) {
        int direct = random.nextInt(4);
        while (true) {
            switch (direct) {
                case 0: {
                    if (map[x - 1][y] != 2 && prevDirect != 2) {
                        return direct;
                    }
                    break;
                }
                case 1: {
                    if (map[x][y - 1] != 2 && prevDirect != 3) {
                        return direct;
                    }
                    break;
                }
                case 2: {
                    if (map[x + 1][y] != 2 && prevDirect != 0) {
                        return direct;
                    }
                    break;
                }
                case 3: {
                    if (map[x][y + 1] != 2 && prevDirect != 1) {
                        return direct;
                    }
                    break;
                }
            }
            direct = (direct + 1) % 4;
        }
    }

    private boolean changeDirection(int x, int y, int direction, int[][] map) {
            if (map[x - 1][y] != 2 && direction != 0 && direction != 2) {
                return true;
            }
            if (map[x][y - 1] != 2 && direction != 1 && direction != 3) {
                return true;
            }
            if (map[x + 1][y] != 2 && direction != 2 && direction != 0) {
                return true;
            }
            if (map[x][y + 1] != 2 && direction != 3 && direction != 1) {
                return true;
            }
        return false;
    }
}