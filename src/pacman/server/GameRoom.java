package pacman.server;

public class GameRoom extends Thread {

    GameRoom(Player player) {
        new Thread(new PlayerThread(player, true)).start();
        this.start();
    }

    GameRoom(Player firstPlayer, Player secondPlayer) {
        new Thread(new PlayerThread(firstPlayer, true)).start();
        new Thread(new PlayerThread(secondPlayer, false)).start();
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            // Процесс игры
        }
    }
}