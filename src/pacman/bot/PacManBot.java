package pacman.bot;

import pacman.api.IPacManAPI;

import java.util.Random;

public class PacManBot extends Thread {

    protected IPacManAPI api;
    protected Random random = new Random();

    protected PacManBot(IPacManAPI api) {
        this.api = api;
    }

    protected boolean canChangeDirection(int x, int y, int direction, int[][] map) {

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