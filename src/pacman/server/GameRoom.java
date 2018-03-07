package pacman.server;

public class GameRoom extends Thread {

    private Game game;

    GameRoom(Player player) {
        game = GameFactory.createGame();

        new PlayerThread(player, true).start();
        this.start();
    }

    GameRoom(Player firstPlayer, Player secondPlayer) {
        game = GameFactory.createGame();

        new PlayerThread(firstPlayer, true).start();
        new PlayerThread(secondPlayer, false).start();
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            // Процесс игры
        }
    }
}