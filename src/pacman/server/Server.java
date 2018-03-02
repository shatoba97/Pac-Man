package pacman.server;

import pacman.api.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(7070);
        while (true) {
            Socket socket = serverSocket.accept();
        }
    }

    public static GameInfo createTestGameInfo() {
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