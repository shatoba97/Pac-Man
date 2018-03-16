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
                }
                sleep(10);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private void stepGame(Game game) {

        if (game.pacman.x % 20 == 0 && game.pacman.y % 20 == 0) {
            game.pacman.direction = game.pacman.nextDirection;
        }


        if (game.map[game.pacman.x / 20][game.pacman.y / 20] == 1) {
            game.map[game.pacman.x / 20][game.pacman.y / 20] = 0;
            game.pacman.score += 10;
        }

        switch (game.pacman.direction) {
            case 0: {
                if (game.map[(game.pacman.x - 1) / 20][(game.pacman.y + 10) / 20] != 2) {
                    game.pacman.x -= 1;
                } else {
                    game.pacman.direction = 4;
                }
                break;
            }
            case 1: {
                if (game.map[(game.pacman.x + 10) / 20][(game.pacman.y - 1) / 20] != 2) {
                    game.pacman.y -= 1;
                } else {
                    game.pacman.direction = 4;
                }
                break;
            }
            case 2: {
                if (game.map[(game.pacman.x + 20) / 20][(game.pacman.y + 10) / 20] != 2) {
                    game.pacman.x += 1;
                } else {
                    game.pacman.direction = 4;
                }
                break;
            }
            case 3: {
                if (game.map[(game.pacman.x + 10) / 20][(game.pacman.y + 20) / 20] != 2) {
                    game.pacman.y += 1;
                } else {
                    game.pacman.direction = 4;
                }
                break;
            }
        }
    }
}