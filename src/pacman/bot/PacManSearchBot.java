package pacman.bot;

import pacman.api.GameInfo;
import pacman.api.IPacManAPI;
import pacman.api.ViewProperties;

import java.awt.*;
import java.util.ArrayList;

public class PacManSearchBot extends PacManBot {

    int rd = 0;
    int ru = 0;
    int ld = 0;
    int lu = 0;

    public PacManSearchBot(IPacManAPI api) {
        super(api);
    }

    @Override
    public void run() {
        api.toRight();
        ViewProperties viewProperties = api.getViewProperties();
        while (true) {
            GameInfo gameInfo = api.getInfoAboutLeftPlayer();
            int x = (gameInfo.pacman.x + viewProperties.weightRect / 2)
                    / viewProperties.weightRect;
            int y = (gameInfo.pacman.y + viewProperties.heightRect / 2)
                    / viewProperties.heightRect;

            if (canChangeDirection(x, y, gameInfo.pacman.direction, gameInfo.map)) {
                int direction = getPathToFood(x, y, gameInfo.pacman.direction, gameInfo.map);
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
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }

    private int getPathToFood(int x, int y, int currentDirection, int[][] map) {

        ArrayList<Dimension> way = new ArrayList<>();
        way.add(new Dimension(x, y));

        int[][] markMap = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                markMap[i][j] = map[i][j];
            }
        }

        while (!way.isEmpty()) {

            int nextX = way.get(0).width;
            int nextY = way.remove(0).height;

            if (map[nextX][nextY] == 1) {
                way.clear();
                return calcDirect(nextX, nextY, x, y, currentDirection, map);
            } else {
                markMap[nextX][nextY] = -1;
                if (map[nextX - 1][nextY] != 2 && markMap[nextX - 1][nextY] >= 0) {
                    way.add(new Dimension(nextX - 1, nextY));
                }
                if (map[nextX][nextY - 1] != 2 && markMap[nextX][nextY - 1] >= 0) {
                    way.add(new Dimension(nextX, nextY - 1));
                }
                if (map[nextX + 1][nextY] != 2 && markMap[nextX + 1][nextY] >= 0) {
                    way.add(new Dimension(nextX + 1, nextY));
                }
                if (map[nextX][nextY + 1] != 2 && markMap[nextX][nextY + 1] >= 0) {
                    way.add(new Dimension(nextX, nextY + 1));
                }
            }
        }
        return 4;
    }

    private int calcDirect(int goalX, int goalY, int x, int y,
                           int currentDirection, int[][] map) {
        if (goalX == x && goalY > y) {
            int tmp = y;
            boolean isWallOnWay = false;
            while (tmp < goalY) {
                tmp = tmp + 1;
                if (map[x][tmp] == 2) {
                    isWallOnWay = true;
                    break;
                }
            }
            if (!isWallOnWay) {
                System.out.println("Ниже");
                if (map[x][y + 1] != 2) {
                    return 3;
                }
            }
        }
        if (goalX == x && goalY < y) {
            int tmp = y;
            boolean isWallOnWay = false;
            while (tmp > goalY) {
                tmp = tmp - 1;
                if (map[x][tmp] == 2) {
                    isWallOnWay = true;
                    break;
                }
            }
            if (!isWallOnWay) {
                System.out.println("Выше");
                if (map[x][y - 1] != 2) {
                    return 1;
                }
            }
        }
        if (goalX > x && goalY == y) {
            int tmp = x;
            boolean isWallOnWay = false;
            while (tmp < goalX) {
                tmp = tmp + 1;
                if (map[tmp][y] == 2) {
                    isWallOnWay = true;
                    break;
                }
            }
            if (!isWallOnWay) {
                System.out.println("Правее");
                if (map[x + 1][y] != 2) {
                    return 2;
                }
            }
        }
        if (goalX < x && goalY == y) {
            int tmp = x;
            boolean isWallOnWay = false;
            while (tmp > goalX) {
                tmp = tmp - 1;
                if (map[tmp][y] == 2) {
                    isWallOnWay = true;
                    break;
                }
            }
            if (!isWallOnWay) {
                System.out.println("Левее");
                if (map[x - 1][y] != 2) {
                    return 0;
                }
            }
        }

        if (goalX >= x && goalY >= y) {
            System.out.println("Правее и ниже: " + rd);
            rd = (rd + 1 + random.nextInt(3)) % 2;
            if (rd % 2 == 0) {
                if (map[x][y + 1] != 2) {
                    return 3;
                } else if (map[x + 1][y] != 2) {
                    return random.nextInt(4);
                }
            } else {
                if (map[x + 1][y] != 2) {
                    return 2;
                } else if (map[x][y + 1] != 2) {
                    return random.nextInt(4);
                }
            }
            if (map[x][y - 1] != 2) {
                return 1;
            }
            if (map[x - 1][y] != 2) {
                return 0;
            }
        }
        if (goalX >= x && goalY <= y) {
            System.out.println("Правее и выше: " + ru);
            ru = (ru + 1 + random.nextInt(3)) % 2;
            if (ru % 2 == 0) {
                if (map[x + 1][y] != 2) {
                    return 2;
                } else if (map[x][y - 1] != 2) {
                    return random.nextInt(4);
                }
            } else {
                if (map[x][y - 1] != 2) {
                    return 1;
                } else if (map[x + 1][y] != 2) {
                    return random.nextInt(4);
                }
            }
            if (map[x - 1][y] != 2) {
                return 0;
            }
            if (map[x][y + 1] != 2) {
                return 3;
            }
        }
        if (goalX <= x && goalY >= y) {
            System.out.println("Левее и ниже: " + ld);
            ld = (ld + 1 + + random.nextInt(3)) % 2;
            if (ld % 2 == 0) {
                if (map[x - 1][y] != 2) {
                    return 0;
                } else if (map[x][y + 1] != 2) {
                    return random.nextInt(4);
                }
            } else {
                if (map[x][y + 1] != 2) {
                    return 3;
                } else if (map[x - 1][y] != 2) {
                    return random.nextInt(4);
                }
            }
            if (map[x + 1][y] != 2) {
                return 2;
            }
            if (map[x][y - 1] != 2) {
                return 1;
            }
        }
        if (goalX <= x && goalY <= y) {
            System.out.println("Левее и выше: " + lu);
            lu = (lu + 1 + random.nextInt(3)) % 2;
            if (lu % 2 == 0) {
                if (map[x - 1][y] != 2) {
                    return 0;
                } else if (map[x][y - 1] != 2) {
                    return random.nextInt(4);
                }
            } else {
                if (map[x][y - 1] != 2) {
                    return 1;
                } else if (map[x - 1][y] != 2) {
                    return random.nextInt(4);
                }
            }
            if (map[x][y + 1] != 2) {
                return 3;
            }
            if (map[x + 1][y] != 2) {
                return 2;
            }
        }
        return -1;
    }
}