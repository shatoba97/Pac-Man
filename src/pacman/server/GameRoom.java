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
                sleep(25);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private void stepGame(Game game) {
        switch (game.pacman.direction) {
            case 0: {
                game.pacman.x -= 1;
                break;
            }
            case 1: {
                game.pacman.y -= 1;
                break;
            }
            case 2: {
                game.pacman.x += 1;
                break;
            }
            case 3: {
                game.pacman.y += 1;
                break;
            }
        }
    }
}