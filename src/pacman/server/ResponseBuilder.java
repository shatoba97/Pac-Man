package pacman.server;

import pacman.api.GameInfo;
import pacman.api.Ghost;
import pacman.api.Pacman;

import java.util.ArrayList;

public class ResponseBuilder {

    // Singleton
    // =====================================================================
    private static ResponseBuilder sResponseBuilder = new ResponseBuilder();

    private ResponseBuilder() {}

    public static ResponseBuilder getInstance() {
        return sResponseBuilder;
    }
    // =====================================================================


    public GameInfo getOKResponse() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.responseCode = 200;
        return gameInfo;
    }

    public GameInfo getERRORResponse() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.responseCode = 404;
        return gameInfo;
    }

    public GameInfo createResponse(Game game) {
        GameInfo gameInfo = new GameInfo();
        gameInfo.responseCode = 200;
        gameInfo.isPlaying = game.isPlaying;
        gameInfo.map = game.map;
        gameInfo.pacman = game.pacman;
        return gameInfo;
    }

    public GameInfo getTestGameInfo() {
        GameInfo gameInfo = new GameInfo();

        gameInfo.responseCode = 200;

        gameInfo.isPlaying = true;

        gameInfo.map = new int[2][2];
        gameInfo.map[0][0] = 1;
        gameInfo.map[0][1] = 2;
        gameInfo.map[1][0] = 3;
        gameInfo.map[1][1] = 4;

        gameInfo.pacman = new Pacman();
        gameInfo.pacman.x = 10;
        gameInfo.pacman.y = 15;
        gameInfo.pacman.direction = 1;
        gameInfo.pacman.score = 255;

        gameInfo.gameRoomsList = null;

        gameInfo.ghosts = new ArrayList<>();
        Ghost ghost = new Ghost();
        ghost.x = 50;
        ghost.y = 60;
        ghost.direction = 3;
        gameInfo.ghosts.add(ghost);

        return gameInfo;
    }
}