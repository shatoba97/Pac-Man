package pacman.server;

import pacman.api.GameInfo;
import pacman.api.GameResult;

import java.util.ArrayList;

/**
 * @author Alex Shatoba
 */
public class ResponseBuilder {

    // Singleton
    // =====================================================================
    private static ResponseBuilder sResponseBuilder = new ResponseBuilder();

    private ResponseBuilder() {}

    static ResponseBuilder getInstance() {
        return sResponseBuilder;
    }
    // =====================================================================


    GameInfo getOKResponse() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.responseCode = 200;
        return gameInfo;
    }

    GameInfo getERRORResponse() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.responseCode = 404;
        return gameInfo;
    }

    GameInfo createGameInfoResponse(Game game) {
        GameInfo gameInfo = new GameInfo();
        gameInfo.responseCode = 200;
        gameInfo.isPlaying = game.isPlaying;
        gameInfo.map = game.map;
        gameInfo.pacMan = game.pacMan;
        gameInfo.ghosts = new ArrayList<>();
        gameInfo.ghosts = game.ghosts;
        gameInfo.viewProperties = game.viewProperties;
        return gameInfo;
    }

    GameInfo createResultGameInfo(Game firstGame, Game secondGame) {
        GameInfo gameInfo = new GameInfo();
        gameInfo.gameResult = new GameResult();
        gameInfo.gameResult.myScore = firstGame.pacMan.score;
        gameInfo.gameResult.enemyScore = secondGame.pacMan.score;
        return gameInfo;
    }
}