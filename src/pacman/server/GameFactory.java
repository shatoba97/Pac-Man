package pacman.server;

/**
 * @author Alex Shatoba
 */
abstract class GameFactory {
    static Game createGame(int ghostCount) {
        return new Game("Map.txt", ghostCount);
    }
}