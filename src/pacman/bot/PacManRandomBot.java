package pacman.bot;

import pacman.api.Direction;
import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.ViewProperties;

public class PacManRandomBot extends PacManBot {

    public PacManRandomBot(IPacManAPI api) {
        super(api);
    }

    @Override
    public void run() {
        Direction direct;
        ViewProperties viewProperties = api.getInfoAboutLeftPlayer().viewProperties;
        while (true) {
            GameInfo gameInfo = api.getInfoAboutLeftPlayer();
            if (gameInfo == null) {
                break;
            }

            if (gameInfo.isPlaying) {

                int x = (gameInfo.pacMan.x + viewProperties.weightRect / 2)
                        / viewProperties.weightRect;
                int y = (gameInfo.pacMan.y + viewProperties.heightRect / 2)
                        / viewProperties.heightRect;

                if (canChangeDirection(x, y, gameInfo.pacMan.direction, gameInfo.map)) {
                    direct = randomDirect(x, y, gameInfo.pacMan.direction, gameInfo.map);

                    this.turn(direct);

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    private Direction randomDirect(int x, int y, Direction currentDirection, int[][] map) {
        Direction direction = getRandomDirect();
        while (true) {
            switch (direction) {
                case LEFT: {
                    if (map[x - 1][y] != 2 && currentDirection != Direction.RIGHT) {
                        return direction;
                    }
                    break;
                }
                case UP: {
                    if (map[x][y - 1] != 2 && currentDirection != Direction.DOWN) {
                        return direction;
                    }
                    break;
                }
                case RIGHT: {
                    if (map[x + 1][y] != 2 && currentDirection != Direction.LEFT) {
                        return direction;
                    }
                    break;
                }
                case DOWN: {
                    if (map[x][y + 1] != 2 && currentDirection != Direction.UP) {
                        return direction;
                    }
                    break;
                }
            }
            direction = getRandomDirect();
        }
    }

    private Direction getRandomDirect() {
        int direction = random.nextInt(4);
        switch (direction) {
            case 0: return Direction.LEFT;
            case 1: return Direction.UP;
            case 2: return Direction.RIGHT;
            case 3: return Direction.DOWN;
            default: return Direction.LEFT;
        }
    }
}