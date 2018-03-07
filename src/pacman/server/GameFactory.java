package pacman.server;

public abstract class GameFactory {
    public static Game createGame() {
        return new Game();
    }
}