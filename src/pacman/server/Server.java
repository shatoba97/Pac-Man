package pacman.server;

import com.google.gson.Gson;
import pacman.api.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(7070);
        while (true) {
            Socket socket = serverSocket.accept();
            Player newPlayer = new Player(socket);
            // Передача игрока игровому диспетчеру...
            // Распределение по очередям в зависимости от типа игры...
            // Создание комнаты и ответ игроку
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

class Player extends Thread {

    public PacManAPI.GameType gameType;
    public DataInputStream input;

    private DataOutputStream output;
    private Gson gson;

    private GameRoom gameRoom;

    public Player(Socket socket) {
        try {
            gson = new Gson();
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            int type = input.readInt();
            switch (type) {
                case RequestCodes.SINGLE_GAME: gameType = PacManAPI.GameType.SINGLE; break;
                case RequestCodes.VERSUS_GAME: gameType = PacManAPI.GameType.VERSUS; break;
                case RequestCodes.VIEW_GAME: gameType = PacManAPI.GameType.VIEW; break;
            }
        } catch (IOException ignore) {}
    }

    public void sendResponse(GameInfo gameInfo) {
        try {
            output.writeUTF(gson.toJson(gameInfo));
        } catch (IOException ignored) {};
    }

    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // обработка запросов игрока и отправка ответов
                int inputRequest = input.readInt();
                switch (inputRequest) {
                    case 0: sendResponse(gameRoom.getGameInfoForLeftPlayer()); break;
                    case 1: sendResponse(gameRoom.getGameInfoForRightPlayer()); break;
                    case 2: break;
                    default:
                }
            } catch (IOException ignored) {}
        }
    }
}

class GameRoom {
    public GameInfo getGameInfoForLeftPlayer() {
        return null;
    }

    public GameInfo getGameInfoForRightPlayer() {
        return null;
    }
}