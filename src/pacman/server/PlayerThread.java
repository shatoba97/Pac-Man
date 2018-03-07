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
                        gameRoom.firstGame.pacman.direction = 1;
                    } else {
                        gameRoom.secondGame.pacman.direction = 1;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_DOWN: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacman.direction = 3;
                    } else {
                        gameRoom.secondGame.pacman.direction = 3;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_LEFT: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacman.direction = 0;
                    } else {
                        gameRoom.secondGame.pacman.direction = 0;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_RIGHT: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacman.direction = 2;
                    } else {
                        gameRoom.secondGame.pacman.direction = 2;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.REQUEST_INFO_FOR_LEFT_PLAYER: {
                    if (isFirstPlayer) {
                        GameInfo gameInfo = responseBuilder.createResponse(gameRoom.firstGame);
                        player.sendResponse(gameInfo);
                    } else {
                        GameInfo gameInfo = responseBuilder.createResponse(gameRoom.secondGame);
                        player.sendResponse(gameInfo);
                    }
                    break;
                }
                case RequestCodes.REQUEST_INFO_FOR_RIGHT_PLAYER: {
                    if (isFirstPlayer) {
                        GameInfo gameInfo = responseBuilder.createResponse(gameRoom.secondGame);
                        player.sendResponse(gameInfo);
                    } else {
                        GameInfo gameInfo = responseBuilder.createResponse(gameRoom.firstGame);
                        player.sendResponse(gameInfo);
                    }
                    break;
                }
                default: {
                    player.sendResponse(responseBuilder.getERRORResponse());
                    break;
                }
            }
        }
    }
}