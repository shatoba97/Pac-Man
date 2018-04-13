package pacman.server;

abstract class GameFactory {
    static Game createGame(int ghostCount) {
        return new Game("Map.txt", ghostCount);
    }
}