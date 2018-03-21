package pacman.server;

import pacman.api.GameInfo;
import pacman.api.Ghost;
import pacman.api.Pacman;
import pacman.api.ViewProperties;

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

    public GameInfo createGameInfoResponse(Game game) {
        GameInfo gameInfo = new GameInfo();
        gameInfo.responseCode = 200;
        gameInfo.isPlaying = game.isPlaying;
        gameInfo.map = game.map;
        gameInfo.pacman = game.pacman;
        return gameInfo;
    }
}