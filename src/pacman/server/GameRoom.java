package pacman.server;

import pacman.api.Direction;
import pacman.api.Entity;
import pacman.api.Ghost;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Maxim Rozhkov
 */
public class GameRoom extends Thread {

    Game firstGame;
    Game secondGame;

    boolean firstPlayerIsReady = true;
    boolean secondPlayerIsReady = true;

    private PlayerThread firstPlayerThread;
    private PlayerThread secondPlayerThread;

    GameRoom(Player player, int ghostCount) {
        firstGame = GameFactory.createGame(ghostCount);
        firstPlayerIsReady = false;
        firstPlayerThread = new PlayerThread(player, true, this);
        firstPlayerThread.start();
    }

    GameRoom(Player firstPlayer, Player secondPlayer, int ghostCount) {
        firstGame = GameFactory.createGame(ghostCount);
        secondGame = GameFactory.createGame(ghostCount);
        firstPlayerIsReady = false;
        secondPlayerIsReady = false;
        firstPlayerThread = new PlayerThread(firstPlayer, true, this);
        secondPlayerThread = new PlayerThread(secondPlayer, false, this);
        firstPlayerThread.start();
        secondPlayerThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (firstGame.isPlaying) {
                    stepGame(firstGame);
                }
                if (secondGame != null) {
                    if (secondGame.isPlaying) {
                        stepGame(secondGame);
                    }
                    if (!firstGame.isPlaying && !secondGame.isPlaying) {
                        break;
                    }
                } else {
                    if (!firstGame.isPlaying) {
                        break;
                    }
                }
                sleep(500 / firstGame.viewProperties.heightRect);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
        }

        try {
            firstPlayerThread.join();
            if (secondPlayerThread != null) {
                secondPlayerThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Игровая комната закрыта!");
    }

    private void stepGame(Game game) {
        stoppingEntityAtFork(game.pacMan, game);
        appleEntityDirection(game.pacMan, game);
        checkEating(game);

        moveEntity(game.pacMan, game);
        moveEntity(game.pacMan, game);

        if (game.ghosts != null) {
            for (Ghost ghost : game.ghosts) {
                moveEntity(ghost, game);
                stoppingEntityAtFork(ghost, game);
                if (isCollision(game.pacMan, ghost)) {
                    game.pacMan.deathCounter += 1;
                    game.pacMan.score -= 100;
                    if (game.pacMan.score < 0) {
                        game.pacMan.score = 0;
                    }
                    ghost.spawn();
                }
                calcGhostsDirection(ghost, game);
                appleEntityDirection(ghost, game);
            }
        }

        checkEndGame(game);
    }

    private void calcGhostsDirection(Ghost ghost, Game game) {
        int w = game.viewProperties.weightRect;
        int h = game.viewProperties.heightRect;
        if (ghost.direction == Direction.NOTING) {
            ArrayList<Direction> way
                    = getPathToPacMan(ghost.x / w, ghost.y / h, game.map,
                    game.pacMan.x / w, game.pacMan.y / h);
            if (way.size() != 0) {
                ghost.nextDirection = way.get(way.size() - 1);
            } else {
                ghost.spawn();
            }
        }
    }

    private ArrayList<Direction> getPathToPacMan(int x, int y, int[][] map, int pacmanX, int pacmanY) {

        ArrayList<Dimension> wayPoints = new ArrayList<>();
        wayPoints.add(new Dimension(x, y));

        int[][] markMap = new int[map.length][map[0].length];
        int nextX = 0, nextY = 0;

        while (!wayPoints.isEmpty()) {
            nextX = wayPoints.get(0).width;
            nextY = wayPoints.remove(0).height;
            if (nextX == pacmanX && nextY == pacmanY) {
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

    private void stoppingEntityAtFork(Entity entity, Game game) {
        int w = game.viewProperties.weightRect;
        int h = game.viewProperties.heightRect;
        if (entity.x % w == 0 && entity.y % h == 0) {
            switch (entity.direction) {
                case LEFT: {
                    if (game.map[(entity.x + w / 2) / w][(entity.y - 1) / h] != 2) {
                        entity.nextDirection = Direction.NOTING;
                    }
                    if (game.map[(entity.x + w / 2) / w][(entity.y + h) / h] != 2) {
                        entity.nextDirection = Direction.NOTING;
                    }
                    break;
                }
                case UP: {
                    if (game.map[(entity.x - 1) / w][(entity.y + h / 2) / h] != 2) {
                        entity.nextDirection = Direction.NOTING;
                    }
                    if (game.map[(entity.x + w) / w][(entity.y + h / 2) / h] != 2) {
                        entity.nextDirection = Direction.NOTING;
                    }
                    break;
                }
                case RIGHT: {
                    if (game.map[(entity.x + w / 2) / w][(entity.y - 1) / h] != 2) {
                        entity.nextDirection = Direction.NOTING;
                    }
                    if (game.map[(entity.x + w / 2) / w][(entity.y + h) / h] != 2) {
                        entity.nextDirection = Direction.NOTING;
                    }
                    break;
                }
                case DOWN: {
                    if (game.map[(entity.x - 1) / w][(entity.y + h / 2) / h] != 2) {
                        entity.nextDirection = Direction.NOTING;
                    }
                    if (game.map[(entity.x + w) / w][(entity.y + h / 2) / h] != 2) {
                        entity.nextDirection = Direction.NOTING;
                    }
                    break;
                }
            }
        }
    }

    private void appleEntityDirection(Entity entity, Game game) {
        int w = game.viewProperties.weightRect;
        int h = game.viewProperties.heightRect;
        if (entity.x % w == 0 && entity.y % h == 0) {
            switch (entity.nextDirection) {
                case LEFT: {
                    if (game.map[(entity.x - 1) / w][(entity.y + h / 2) / h] != 2) {
                        entity.direction = entity.nextDirection;
                    }
                    break;
                }
                case UP: {
                    if (game.map[(entity.x + w / 2) / w][(entity.y - 1) / h] != 2) {
                        entity.direction = entity.nextDirection;
                    }
                    break;
                }
                case RIGHT: {
                    if (game.map[(entity.x + w) / w][(entity.y + h / 2) / h] != 2) {
                        entity.direction = entity.nextDirection;
                    }
                    break;
                }
                case DOWN: {
                    if (game.map[(entity.x + w / 2) / w][(entity.y + h) / h] != 2) {
                        entity.direction = entity.nextDirection;
                    }
                    break;
                }
                case NOTING: {
                    entity.direction = Direction.NOTING;
                    break;
                }
            }
        }
    }

    private void moveEntity(Entity entity, Game game) {
        int w = game.viewProperties.weightRect;
        int h = game.viewProperties.heightRect;
        switch (entity.direction) {
            case LEFT: {
                if (game.map[(entity.x - 1) / w][(entity.y + h / 2) / h] != 2) {
                    entity.x -= 1;
                }
                break;
            }
            case UP: {
                if (game.map[(entity.x + w / 2) / w][(entity.y - 1) / h] != 2) {
                    entity.y -= 1;
                }
                break;
            }
            case RIGHT: {
                if (game.map[(entity.x + w) / w][(entity.y + h / 2) / h] != 2) {
                    entity.x += 1;
                }
                break;
            }
            case DOWN: {
                if (game.map[(entity.x + w / 2) / w][(entity.y + h) / h] != 2) {
                    entity.y += 1;
                }
                break;
            }
        }
    }

    private void checkEating(Game game) {
        int w = game.viewProperties.weightRect;
        int h = game.viewProperties.heightRect;
        if (game.map[game.pacMan.x / w][game.pacMan.y / h] == 1) {
            game.map[game.pacMan.x / w][game.pacMan.y / h] = 0;
            game.pacMan.score += 10;
            game.eat += 1;
        }
    }

    private boolean isCollision(Entity firstEntity, Entity secondEntity) {
        return Math.abs(firstEntity.x - secondEntity.x) < 5
                && Math.abs(firstEntity.y - secondEntity.y) < 5;
    }

    private void checkEndGame(Game game) {
        if (game.eat == 244) {
            game.isPlaying = false;
        }
    }
}