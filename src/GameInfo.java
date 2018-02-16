import java.util.ArrayList;

public class GameInfo {
    public int[][] map;
    public ArrayList<Ghost> ghosts;
    public Pacman pacman;

    boolean gameState;

    ArrayList<GameRoom> gameRoomsList;

    // Карта
    // Информацию о призаках (координаты и направление движения)
    // Информация о пакмане (координаты и направления, число очков)

    // Состояние игры (игра идёт/игра закончилась)

    // Лист доступных для просмотра игровых комнат

}

class GameRoom {
    public int id;
    public int gameType;
}

abstract class Entity {
    public int x;
    public int y;
    public int direction;
}

class Ghost extends Entity {}

class Pacman extends Entity {
    public int score;
}