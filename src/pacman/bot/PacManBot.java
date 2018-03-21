package pacman.bot;

import com.sun.glass.ui.Size;
import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.ViewProperties;

import java.util.ArrayList;
import java.util.Random;


public class PacManBot extends Thread {

    private IPacManAPI api;
    private Random random = new Random();

    public PacManBot(IPacManAPI api) {
        this.api = api;
    }

    @Override
    public void run() {
        api.toLeft();
        ViewProperties viewProperties = api.getViewProperties();
        while (true) {

            GameInfo gameInfo = api.getInfoAboutLeftPlayer();
            int x = (gameInfo.pacman.x + viewProperties.weightRect / 2)
                    / viewProperties.weightRect;
            int y = (gameInfo.pacman.y + viewProperties.heightRect / 2)
                    / viewProperties.heightRect;

            if (canChangeDirection(x, y, gameInfo.pacman.direction, gameInfo.map)) {
                int direction = getPathToFoot(y, x, gameInfo.map);
                if (direction >= 0) {
                    switch (direction) {
                        case 0: {
                            api.toLeft();
                            break;
                        }
                        case 1: {
                            api.toUp();
                            break;
                        }
                        case 2: {
                            api.toRight();
                            break;
                        }
                        case 3: {
                            api.toDown();
                            break;
                        }
                    }
                }
            }

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {

            }
        }
    }

    private boolean canChangeDirection(int x, int y, int direction, int[][] map) {
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

    private int getPathToFoot(int x, int y, int[][] map) {
        ArrayList<Size> rects = new ArrayList<>();
        rects.add(new Size(y, x));
        int[][] markMap = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                markMap[i][j] = map[i][j];
            }
        }

        while (!rects.isEmpty()) {

            int nextX = rects.get(0).height;
            int nextY = rects.remove(0).width;

            if (map[nextY][nextX] == 1) {
                if (nextY == y && nextX > x) {
                    if (map[y][x + 1] != 2) {
                        return 3;
                    }
                }
                if (nextY == y && nextX < x) {
                    if (map[y][x - 1] != 2) {
                        return 1;
                    }
                }
                if (nextY > y && nextX == x) {
                    if (map[y + 1][x] != 2) {
                        return 2;
                    }
                }
                if (nextY < y && nextX == x) {
                    if (map[y - 1][x] != 2) {
                        return 0;
                    }
                }
                if (nextY >= y && nextX >= x) {
                    System.out.println("Правее и ниже");
                    if (map[y][x + 1] != 2) {
                        return random.nextInt(2) + 2;
                    }
                    if (map[y + 1][x] != 2) {
                        return random.nextInt(2) + 2;
                    }
                    if (map[y][x - 1] != 2) {
                        return 1;
                    }
                    if (map[y - 1][x] != 2) {
                        return 0;
                    }
                }
                if (nextY >= y && nextX <= x) {
                    System.out.println("Правее и выше");
                    if (map[y + 1][x] != 2) {
                        return random.nextInt(2) + 1;
                    }
                    if (map[y][x - 1] != 2) {
                        return random.nextInt(2) + 1;
                    }
                    if (map[y - 1][x] != 2) {
                        return 0;
                    }
                    if (map[y][x + 1] != 2) {
                        return 3;
                    }
                }
                if (nextY <= y && nextX >= x) {
                    System.out.println("Левее и ниже");
                    if (map[y - 1][x] != 2) {
                        return random.nextInt(2) * 3;
                    }
                    if (map[y][x + 1] != 2) {
                        return random.nextInt(2) * 3;
                    }
                    if (map[y + 1][x] != 2) {
                        return 2;
                    }
                    if (map[y][x - 1] != 2) {
                        return 1;
                    }
                }
                if (nextY <= y && nextX <= x) {
                    System.out.println("Левее и выше");
                    if (map[y - 1][x] != 2) {
                        return random.nextInt(2);
                    }
                    if (map[y][x - 1] != 2) {
                        return random.nextInt(2) + 1;
                    }
                    if (map[y][x + 1] != 2) {
                        return 3;
                    }
                    if (map[y + 1][x] != 2) {
                        return 2;
                    }
                }
                return -1;
            } else {
                markMap[nextY][nextX] = -1;
                if (map[nextY - 1][nextX] != 2 && markMap[nextY - 1][nextX] >= 0) {
                    rects.add(new Size(nextY - 1, nextX));
                }
                if (map[nextY][nextX - 1] != 2 && markMap[nextY][nextX - 1] >= 0) {
                    rects.add(new Size(nextY, nextX - 1));
                }
                if (map[nextY + 1][nextX] != 2 && markMap[nextY + 1][nextX] >= 0) {
                    rects.add(new Size(nextY + 1, nextX));
                }
                if (map[nextY][nextX + 1] != 2 && markMap[nextY][nextX + 1] >= 0) {
                    rects.add(new Size(nextY, nextX + 1));
                }
            }
        }
        return 4;
    }
}