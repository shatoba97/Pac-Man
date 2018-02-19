package pacman.api;

import java.util.ArrayList;

public class GameInfo {

    int responseCode;

    public boolean isPlaying;

    public int[][] map;
    public ArrayList<Ghost> ghosts;
    public Pacman pacman;

    public ArrayList<GameRoom> gameRoomsList;
}

