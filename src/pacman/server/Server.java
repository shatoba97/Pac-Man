package pacman.server;

import com.google.gson.Gson;
import pacman.api.GameInfo;
import pacman.api.Ghost;
import pacman.api.Pacman;
import pacman.api.RequestCodes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws Exception {
        try {
            ServerSocket serverSocket = new ServerSocket(7070);
            System.out.println("Ожидание подключения клиентов");
            Socket socket = serverSocket.accept();
            System.out.println(socket.getInetAddress());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            Gson gson = new Gson();
            boolean isID = false;
            while (true) {
                int requestCode = input.readInt();
                String requestString = null;
                if (!isID) {
                    switch (requestCode) {
                        case RequestCodes.CMD_UP:
                            requestString = "CMD: to Up";
                            break;
                        case RequestCodes.CMD_DOWN:
                            requestString = "CMD: to Down";
                            break;
                        case RequestCodes.CMD_LEFT:
                            requestString = "CMD: to Left";
                            break;
                        case RequestCodes.CMD_RIGHT:
                            requestString = "CMD: to Right";
                            break;
                        case RequestCodes.SINGLE_GAME:
                            requestString = "TypeGame: Single game";
                            break;
                        case RequestCodes.VERSUS_GAME:
                            requestString = "TypeGame: Versus game";
                            break;
                        case RequestCodes.VIEW_GAME:
                            requestString = "TypeGame: View game";
                            break;
                        case RequestCodes.DISCONNECT_WITH_RESULT:
                            requestString = "Disconnect with result";
                            break;
                        case RequestCodes.DISCONNECT_WITHOUT_RESULT:
                            requestString = "Disconnect without result";
                            break;
                        case RequestCodes.REQUEST_INFO_FOR_LEFT_PLAYER:
                            requestString = "Request info for the left player";
                            break;
                        case RequestCodes.REQUEST_INFO_FOR_RIGHT_PLAYER:
                            requestString = "Request info for the right player";
                            break;
                        case RequestCodes.REQUEST_LIST_OF_GAME_ROOM:
                            requestString = "Request list of game room";
                            break;
                        case RequestCodes.NOTIFY_SERVER_ABOUT_CHOOSE_GAME_ROOM_ID:
                            isID = true;
                            requestString = "Notify server about choose game room id";
                            break;
                    }
                } else {
                    requestString = "Room ID: " + requestCode;
                    isID = false;
                }
                System.out.println("Request: " + requestString);
                if (requestCode == RequestCodes.DISCONNECT_WITHOUT_RESULT) {
                    break;
                }

                output.writeUTF(gson.toJson(createTestGameInfo()));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
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