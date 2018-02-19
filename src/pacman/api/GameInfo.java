package pacman.api;

import java.util.ArrayList;

public class GameInfo {

    public int[][] map;
    public ArrayList<Ghost> ghosts;
    public Pacman pacman;

    public boolean isPlaying;

    public ArrayList<GameRoom> gameRoomsList;
}

