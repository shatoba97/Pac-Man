package pacman.server;

import pacman.api.Direction;
import pacman.api.GameInfo;
import pacman.api.RequestCodes;

/**
 * @author Maxim Rozhkov
 */
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
                        gameRoom.firstGame.pacMan.nextDirection = Direction.UP;
                    } else {
                        gameRoom.secondGame.pacMan.nextDirection = Direction.UP;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_DOWN: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacMan.nextDirection = Direction.DOWN;
                    } else {
                        gameRoom.secondGame.pacMan.nextDirection = Direction.DOWN;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_LEFT: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacMan.nextDirection = Direction.LEFT;
                    } else {
                        gameRoom.secondGame.pacMan.nextDirection = Direction.LEFT;
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    break;
                }
                case RequestCodes.CMD_RIGHT: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.pacMan.nextDirection = Direction.RIGHT;
                    } else {
                        gameRoom.secondGame.pacMan.nextDirection = Direction.RIGHT;
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
                case RequestCodes.READY: {
                    if (isFirstPlayer) {
                        gameRoom.firstPlayerIsReady = true;
                    } else {
                        gameRoom.secondPlayerIsReady = true;
                    }
                    break;
                }
                case RequestCodes.DISCONNECT_WITHOUT_RESULT: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.isPlaying = false;
                        System.out.println("Первый игрок завершил игру");
                    } else {
                        gameRoom.secondGame.isPlaying = false;
                        System.out.println("Второй игрок завершил игру");
                    }
                    player.sendResponse(responseBuilder.getOKResponse());
                    Thread.currentThread().stop();
                    break;
                }
                case RequestCodes.DISCONNECT_WITH_RESULT: {
                    if (isFirstPlayer) {
                        gameRoom.firstGame.isPlaying = false;
                    } else {
                        gameRoom.secondGame.isPlaying = false;
                    }

                    if (isFirstPlayer) {
                        if (gameRoom.secondGame == null) {
                            player.sendResponse(responseBuilder
                                    .createResultGameInfo(gameRoom.firstGame, gameRoom.firstGame));
                            System.out.println("Первый игрок завершил игру");
                            Thread.currentThread().stop();
                        }
                        while (true) {
                            if (!gameRoom.secondGame.isPlaying) {
                                player.sendResponse(responseBuilder
                                        .createResultGameInfo(gameRoom.firstGame, gameRoom.secondGame));
                                System.out.println("Первый игрок завершил игру");
                                Thread.currentThread().stop();
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ignored) {break;}
                        }
                    } else {
                        while (true) {
                            if (!gameRoom.firstGame.isPlaying) {
                                player.sendResponse(responseBuilder
                                        .createResultGameInfo(gameRoom.secondGame, gameRoom.firstGame));
                                System.out.println("Второй игрок завершил игру");
                                Thread.currentThread().stop();
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ignored) {break;}
                        }
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