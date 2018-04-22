package pacman.server;

import pacman.api.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<Player> playersQueueWithGhost = new ArrayList<>();
    private static ArrayList<Player> playersQueueWithoutGhost = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Integer.valueOf(args[0]));
        while (true) {
            Socket socket = serverSocket.accept();
            Player player = new Player(socket);
            System.out.println("Подключен новый игрок!");
            switch (player.getRequest()) {
                case RequestCodes.SINGLE_GAME_WITH_GHOST: {
                    new GameRoom(player, 4).start();
                    break;
                }
                case RequestCodes.VERSUS_GAME_WITH_GHOST: {
                    playersQueueWithGhost.add(player);
                    if (playersQueueWithGhost.size() > 1) {
                        new GameRoom(playersQueueWithGhost.remove(0),
                                playersQueueWithGhost.remove(0), 4).start();
                    }
                    break;
                }
                case RequestCodes.SINGLE_GAME_WITHOUT_GHOST: {
                    new GameRoom(player, 0).start();
                    break;
                }
                case RequestCodes.VERSUS_GAME_WITHOUT_GHOST: {
                    playersQueueWithoutGhost.add(player);
                    if (playersQueueWithoutGhost.size() > 1) {
                        new GameRoom(playersQueueWithoutGhost.remove(0),
                                playersQueueWithoutGhost.remove(0), 0).start();
                    }
                    break;
                }
            }
        }
    }
}