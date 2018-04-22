package pacman.bot;

import pacman.api.*;
import pacman.client.BotViewWindow;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Denis Galin
 */
public class PacManSearchBotV2 extends PacManBot {

    public PacManSearchBotV2(IPacManAPI api) {
        super(api);
    }

    @Override
    public void run() {
        ViewProperties viewProperties = api.getInfoAboutLeftPlayer().viewProperties;
        ArrayList<Direction> way;
        while (true) {
            GameInfo gameInfo = api.getInfoAboutLeftPlayer();
            if (gameInfo == null) break;
            if (gameInfo.isPlaying) {
                int x = (gameInfo.pacMan.x + viewProperties.weightRect / 2)
                        / viewProperties.weightRect;
                int y = (gameInfo.pacMan.y + viewProperties.heightRect / 2)
                        / viewProperties.heightRect;
                way = getPathToFood(x, y, gameInfo.map);
                while (!way.isEmpty()) {
                    gameInfo = api.getInfoAboutLeftPlayer();
                    if (gameInfo == null) break;
                    if (gameInfo.pacMan.direction == Direction.NOTING) {
                        Direction direction = way.remove(way.size() - 1);
                        this.turn(direction);
                    }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    private ArrayList<Direction> getPathToFood(int x, int y, int[][] map) {

        ArrayList<Dimension> wayPoints = new ArrayList<>();
        wayPoints.add(new Dimension(x, y));

        int[][] markMap = new int[map.length][map[0].length];
        int nextX = 0, nextY = 0;

        while (!wayPoints.isEmpty()) {
            nextX = wayPoints.get(0).width;
            nextY = wayPoints.remove(0).height;
            if (map[nextX][nextY] == 1) {
                break;
            } else {
                if (map[nextX - 1][nextY] != 2 && markMap[nextX - 1][nextY] >= 0) {
                    markMap[nextX - 1][nextY] = -1;
                    wayPoints.add(new Dimension(nextX - 1, nextY));
                }
                if (map[nextX][nextY - 1] != 2 && markMap[nextX][nextY - 1] >= 0) {
                    markMap[nextX][nextY - 1] = -2;
                    wayPoints.add(new Dimension(nextX, nextY - 1));
                }
                if (map[nextX + 1][nextY] != 2 && markMap[nextX + 1][nextY] >= 0) {
                    markMap[nextX + 1][nextY] = -3;
                    wayPoints.add(new Dimension(nextX + 1, nextY));
                }
                if (map[nextX][nextY + 1] != 2 && markMap[nextX][nextY + 1] >= 0) {
                    markMap[nextX][nextY + 1] = -4;
                    wayPoints.add(new Dimension(nextX, nextY + 1));
                }
            }
        }

        ArrayList<Direction> way = new ArrayList<>();
        way.add(Direction.NOTING);
        switch (markMap[nextX][nextY]) {
            case -1:
                way.add(Direction.LEFT);
                nextX += 1;
                break;
            case -2:
                way.add(Direction.UP);
                nextY += 1;
                break;
            case -3:
                way.add(Direction.RIGHT);
                nextX -= 1;
                break;
            case -4:
                way.add(Direction.DOWN);
                nextY -= 1;
                break;
        }

        while (x != nextX || y != nextY) {
            if (isFork(map, way.get(way.size() - 1), nextX, nextY)) {
                switch (markMap[nextX][nextY]) {
                    case -1:
                        way.add(Direction.LEFT);
                        nextX += 1;
                        break;
                    case -2:
                        way.add(Direction.UP);
                        nextY += 1;
                        break;
                    case -3:
                        way.add(Direction.RIGHT);
                        nextX -= 1;
                        break;
                    case -4:
                        way.add(Direction.DOWN);
                        nextY -= 1;
                        break;
                }
            } else {
                switch (way.get(way.size() - 1)) {
                    case LEFT:
                        nextX += 1;
                        break;
                    case UP:
                        nextY += 1;
                        break;
                    case RIGHT:
                        nextX -= 1;
                        break;
                    case DOWN:
                        nextY -= 1;
                        break;
                }
            }
        }

        return way;
    }

    private boolean isFork(int[][] map, Direction direction, int x, int y) {
        switch (direction) {
            case LEFT: {
                if (map[x][(y - 1)] != 2) {
                    return true;
                }
                if (map[x][(y + 1)] != 2) {
                    return true;
                }
                break;
            }
            case UP: {
                if (map[x - 1][y] != 2) {
                    return true;
                }
                if (map[x + 1][y] != 2) {
                    return true;
                }
                break;
            }
            case RIGHT: {
                if (map[x][(y - 1)] != 2) {
                    return true;
                }
                if (map[x][(y + 1)] != 2) {
                    return true;
                }
                break;
            }
            case DOWN: {
                if (map[x - 1][y] != 2) {
                    return true;
                }
                if (map[x + 1][y] != 2) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        IPacManAPI api = new PacManAPI();
        if (api.connection(args[0], Integer.valueOf(args[1]), PacManAPI.GameType.SINGLE_WITHOUT_GHOST)) {
            new PacManSearchBotV2(api).start();
            new BotViewWindow(api, PacManAPI.GameType.SINGLE_WITHOUT_GHOST);
        }
    }
}