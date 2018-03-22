package pacman.bot;

import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.ViewProperties;

public class PacManRandomBot extends PacManBot {

    public PacManRandomBot(IPacManAPI api) {
        super(api);
    }

    @Override
    public void run() {
        int direct;
        ViewProperties viewProperties = api.getViewProperties();
        while (true) {
            GameInfo gameInfo = api.getInfoAboutLeftPlayer();
            int x = (gameInfo.pacman.x + viewProperties.weightRect / 2)
                    / viewProperties.weightRect;
            int y = (gameInfo.pacman.y + viewProperties.heightRect / 2)
                    / viewProperties.heightRect;

            if (canChangeDirection(x, y, gameInfo.pacman.direction, gameInfo.map)) {
                direct = randomDirect(x, y, gameInfo.pacman.direction, gameInfo.map);
                switch (direct) {
                    case 0:
                        api.toLeft();
                        break;
                    case 1:
                        api.toUp();
                        break;
                    case 2:
                        api.toRight();
                        break;
                    case 3:
                        api.toDown();
                        break;
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}
        }
    }

    private int randomDirect(int x, int y, int currentDirection, int[][] map) {
        int direct = random.nextInt(4);
        while (true) {
            switch (direct) {
                case 0: {
                    if (map[x - 1][y] != 2 && currentDirection != 2) {
                        return direct;
                    }
                    break;
                }
                case 1: {
                    if (map[x][y - 1] != 2 && currentDirection != 3) {
                        return direct;
                    }
                    break;
                }
                case 2: {
                    if (map[x + 1][y] != 2 && currentDirection != 0) {
                        return direct;
                    }
                    break;
                }
                case 3: {
                    if (map[x][y + 1] != 2 && currentDirection != 1) {
                        return direct;
                    }
                    break;
                }
            }
            direct = (direct + 1) % 4;
        }
    }
}
