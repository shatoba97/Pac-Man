package pacman.bot;

import pacman.api.Direction;
import pacman.api.IPacManAPI;

import java.util.Random;

/**
 * @author Denis Galin
 */
public class PacManBot extends Thread {

    protected IPacManAPI api;
    Random random = new Random();

    PacManBot(IPacManAPI api) {
        this.api = api;
    }

    boolean canChangeDirection(int x, int y, Direction direction, int[][] map) {

        if (map[x - 1][y] != 2 && direction != Direction.LEFT && direction != Direction.RIGHT) {
            return true;
        }
        if (map[x][y - 1] != 2 && direction != Direction.UP && direction != Direction.DOWN) {
            return true;
        }
        if (map[x + 1][y] != 2 && direction != Direction.RIGHT && direction != Direction.LEFT) {
            return true;
        }
        if (map[x][y + 1] != 2 && direction != Direction.DOWN && direction != Direction.UP) {
            return true;
        }
        return false;
    }

    void turn(Direction direction) {
        switch (direction) {
            case LEFT: {
                api.toLeft();
                break;
            }
            case UP: {
                api.toUp();
                break;
            }
            case RIGHT: {
                api.toRight();
                break;
            }
            case DOWN: {
                api.toDown();
                break;
            }
        }
    }
}