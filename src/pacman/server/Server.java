package pacman.server;

import pacman.api.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<Player> playersQueue = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(7070);
        while (true) {
            Socket socket = serverSocket.accept();
            Player player = new Player(socket);
            switch (player.getRequest()) {
                case RequestCodes.SINGLE_GAME: {
                    new GameRoom(player).start();
                    break;
                }
                case RequestCodes.VERSUS_GAME: {
                    playersQueue.add(player);
                    if (playersQueue.size() > 1) {
                        new GameRoom(playersQueue.remove(0),
                                playersQueue.remove(0)).start();
                    }
                    break;
                }
                case RequestCodes.VIEW_GAME: {
                    new ViewerThread(player).start();
                    break;
                }
            }
        }
    }
}