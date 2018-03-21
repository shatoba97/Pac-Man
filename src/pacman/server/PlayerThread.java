package pacman.server;

import pacman.api.GameInfo;
import pacman.api.RequestCodes;

public class PlayerThread extends Thread {

    private Player player;
    private ResponseBuilder responseBuilder;
    private boolean isFirstPlayer;

    private final GameRoom gameRoom;

    PlayerThread(Player player, boolean isFirstPlayer, GameRoom gameRoom) {
        this.player = player;
        this.gameRoom = gameRoom;
        this.isFirstPlayer = isFirstPlayer;
        responseBuilder = ResponseBuilder.getInstance();
    }

    @Override
    public void run() {
        player.sendResponse(responseBuilder.getOKResponse());
        while(true) {
            int request = player.getRequest();

            switch (request) {
                case RequestCodes.CMD_UP: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacman.nextDirection = 1;
                    } else {
                        gameRoom.secondGame.pacman.nextDirection = 1;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_DOWN: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacman.nextDirection = 3;
                    } else {
                        gameRoom.secondGame.pacman.nextDirection = 3;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_LEFT: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacman.nextDirection = 0;
                    } else {
                        gameRoom.secondGame.pacman.nextDirection = 0;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_RIGHT: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacman.nextDirection = 2;
                    } else {
                        gameRoom.secondGame.pacman.nextDirection = 2;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.REQUEST_INFO_FOR_LEFT_PLAYER: {
                    if (isFirstPlayer) {
                        GameInfo gameInfo = responseBuilder.createGameInfoResponse(gameRoom.firstGame);
                        player.sendResponse(gameInfo);
                    } else {
                        GameInfo gameInfo = responseBuilder.createGameInfoResponse(gameRoom.secondGame);
                        player.sendResponse(gameInfo);
                    }
                    break;
                }
                case RequestCodes.REQUEST_INFO_FOR_RIGHT_PLAYER: {
                    if (isFirstPlayer) {
                        GameInfo gameInfo = responseBuilder.createGameInfoResponse(gameRoom.secondGame);
                        player.sendResponse(gameInfo);
                    } else {
                        GameInfo gameInfo = responseBuilder.createGameInfoResponse(gameRoom.firstGame);
                        player.sendResponse(gameInfo);
                    }
                    break;
                }
                case RequestCodes.REQUEST_VIEW_PROPERTIES: {
                    player.sendResponse(gameRoom.firstGame.viewProperties);
                    break;
                }
                case RequestCodes.READY: {
                    if (isFirstPlayer) {
                        gameRoom.firstPlayerIsReady = true;
                    } else {
                        gameRoom.secondPlayerIsReady = true;
                    }
                }
                case RequestCodes.DISCONNECT_WITHOUT_RESULT: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.isPlaying = false;
                    } else {
                        gameRoom.secondGame.isPlaying = false;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                default: {
                    player.sendResponse(responseBuilder.getERRORResponse());
                    break;
                }
            }

            if (isFirstPlayer) {
                if (!gameRoom.firstGame.isPlaying) {
                    break;
                }
            } else {
                if (!gameRoom.secondGame.isPlaying) {
                    break;
                }
            }
        }
    }
}