package pacman.server;

public class GameRoom extends Thread {

    public Game firstGame;
    public Game secondGame;
    public boolean firstPlayerIsReady = true;
    public boolean secondPlayerIsReady = true;

    GameRoom(Player player) {
        firstGame = GameFactory.createGame();
        firstPlayerIsReady = false;
        new PlayerThread(player, true, this).start();
    }

    GameRoom(Player firstPlayer, Player secondPlayer) {
        firstGame = GameFactory.createGame();
        secondGame = GameFactory.createGame();
        firstPlayerIsReady = false;
        secondPlayerIsReady = false;
        new PlayerThread(firstPlayer, true, this).start();
        new PlayerThread(secondPlayer, false, this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                stepGame(firstGame);
                if (secondGame != null) {
                    stepGame(secondGame);
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
    }

    private void stepGame(Game game) {

        int h = game.viewProperties.heightRect;
        int w = game.viewProperties.weightRect;

        if (game.pacMan.x % w == 0 && game.pacMan.y % h == 0) {
            switch (game.pacMan.nextDirection) {
                case 0: {
                    if (game.map[(game.pacMan.x - 1) / w][(game.pacMan.y + h / 2) / h] != 2
                            && game.pacMan.direction != 0 && game.pacMan.direction != 2) {
                        game.pacMan.direction = game.pacMan.nextDirection;
                        game.pacMan.nextDirection = 4;
                    }
                    break;
                }
                case 1: {
                    if (game.map[(game.pacMan.x + w / 2) / w][(game.pacMan.y - 1) / h] != 2
                            && game.pacMan.direction != 1 && game.pacMan.direction != 3) {
                        game.pacMan.direction = game.pacMan.nextDirection;
                        game.pacMan.nextDirection = 4;
                    }
                    break;
                }
                case 2: {
                    if (game.map[(game.pacMan.x + w) / w][(game.pacMan.y + h / 2) / h] != 2
                            && game.pacMan.direction != 0 && game.pacMan.direction != 2) {
                        game.pacMan.direction = game.pacMan.nextDirection;
                        game.pacMan.nextDirection = 4;
                    }
                    break;
                }
                case 3: {
                    if (game.map[(game.pacMan.x + w / 2) / w][(game.pacMan.y + h) / h] != 2
                            && game.pacMan.direction != 1 && game.pacMan.direction != 3) {
                        game.pacMan.direction = game.pacMan.nextDirection;
                        game.pacMan.nextDirection = 4;
                    }
                    break;
                }
            }
        }


        if (game.map[game.pacMan.x / w][game.pacMan.y / h] == 1) {
            game.map[game.pacMan.x / w][game.pacMan.y / h] = 0;
            game.pacMan.score += 10;
        }

        switch (game.pacMan.direction) {
            case 0: {
                if (game.map[(game.pacMan.x - 1) / w][(game.pacMan.y + h / 2) / h] != 2) {
                    game.pacMan.x -= 1;
                }
                break;
            }
            case 1: {
                if (game.map[(game.pacMan.x + w / 2) / w][(game.pacMan.y - 1) / h] != 2) {
                    game.pacMan.y -= 1;
                }
                break;
            }
            case 2: {
                if (game.map[(game.pacMan.x + w) / w][(game.pacMan.y + h / 2) / h] != 2) {
                    game.pacMan.x += 1;
                }
                break;
            }
            case 3: {
                if (game.map[(game.pacMan.x + w / 2) / w][(game.pacMan.y + h) / h] != 2) {
                    game.pacMan.y += 1;
                }
                break;
            }
        }

        // Логика призраков
    }
}