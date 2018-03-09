package pacman.server;

public class GameRoom extends Thread {

    public Game firstGame;
    public Game secondGame;

    GameRoom(Player player) {
        firstGame = GameFactory.createGame();
        new PlayerThread(player, true, this).start();
    }

    GameRoom(Player firstPlayer, Player secondPlayer) {
        firstGame = GameFactory.createGame();
        secondGame = GameFactory.createGame();
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
        switch (game.pacman.direction) {
            case 0: {
                if (game.map[(game.pacman.x) / 20][(game.pacman.y + 8) / 20] != 2) {
                    game.pacman.x -= 1;
                }
                break;
            }
            case 1: {
                if (game.map[(game.pacman.x + 8) / 20][(game.pacman.y) / 20] != 2) {
                    game.pacman.y -= 1;
                }
                break;
            }
            case 2: {
                if (game.map[(game.pacman.x + 16) / 20][(game.pacman.y + 8) / 20] != 2) {
                    game.pacman.x += 1;
                }
                break;
            }
            case 3: {
                if (game.map[(game.pacman.x + 8) / 20][(game.pacman.y + 16) / 20] != 2) {
                    game.pacman.y += 1;
                }
                break;
            }
        }
    }
}