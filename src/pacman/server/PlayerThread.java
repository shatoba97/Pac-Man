package pacman.server;

import pacman.api.RequestCodes;

public class PlayerThread extends Thread {

    private Player player;
    private ResponseBuilder responseBuilder;
    private boolean isFirstPlayer;

    PlayerThread(Player player, boolean isFirstPlayer) {
        this.player = player;
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
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_DOWN: {
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_LEFT: {
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_RIGHT: {
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.REQUEST_INFO_FOR_LEFT_PLAYER: {
                    if (isFirstPlayer) {
                        player.sendResponse(responseBuilder.getTestGameInfo());
                    } else {
                        player.sendResponse(responseBuilder.getTestGameInfo());
                    }
                    break;
                }
                case RequestCodes.REQUEST_INFO_FOR_RIGHT_PLAYER: {
                    if (isFirstPlayer) {
                        player.sendResponse(responseBuilder.getTestGameInfo());
                    } else {
                        player.sendResponse(responseBuilder.getTestGameInfo());
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