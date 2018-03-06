package pacman.server;

import com.google.gson.Gson;
import pacman.api.GameInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Player {

    private DataInputStream input;
    private DataOutputStream output;
    private Socket socket;

    private Gson gson;

    Player(Socket socket) {
        this.gson = new Gson();
        this.socket = socket;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {}
    }

    void sendResponse(GameInfo gameInfo) {
        String responseString = gson.toJson(gameInfo);
        try {
            output.writeUTF(responseString);
        } catch (IOException ignored) {}
    }

    int getRequest() {
        try {
            return input.readInt();
        } catch (IOException e) {
            return -1;
        }
    }

    Socket getSocket() {
        return socket;
    }
}