package pacman.api;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Maxim Rozhkov
 */
public class PacManAPI implements IPacManAPI {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private Gson gson = new Gson();

    public enum GameType {SINGLE_WITH_GHOST, VERSUS_WITH_GHOST,
        SINGLE_WITHOUT_GHOST, VERSUS_WITHOUT_GHOST}

    private GameInfo sendRequestCode(int requestCode) {
        synchronized (PacManAPI.class) {
            GameInfo gameInfo;
            try {
                output.writeInt(requestCode);
                String responseString = input.readUTF();
                gameInfo = gson.fromJson(responseString, GameInfo.class);
            } catch (IOException ignored) {
                gameInfo = new GameInfo();
                gameInfo.responseCode = 404;
            }
            return gameInfo;
        }
    }

    private GameInfo checkResponseCode(GameInfo gameInfo) {
        if (gameInfo.responseCode == 200) {
            return gameInfo;
        } else {
            return null;
        }
    }

    @Override
    public boolean connection(String IP, int port, GameType gameType) {
        try {
            InetAddress ip = InetAddress.getByName(IP);
            socket = new Socket(ip, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return false;
        }

        int type;
        switch (gameType) {
            case SINGLE_WITH_GHOST: type = RequestCodes.SINGLE_GAME_WITH_GHOST; break;
            case VERSUS_WITH_GHOST: type = RequestCodes.VERSUS_GAME_WITH_GHOST; break;
            case SINGLE_WITHOUT_GHOST: type = RequestCodes.SINGLE_GAME_WITHOUT_GHOST; break;
            case VERSUS_WITHOUT_GHOST: type = RequestCodes.VERSUS_GAME_WITHOUT_GHOST; break;
            default: type = RequestCodes.SINGLE_GAME_WITHOUT_GHOST; break;
        }

        return sendRequestCode(type).responseCode == 200;
    }

    @Override
    public GameInfo disconnect(boolean isWait) {
        GameInfo gameInfo;
        try {
            if (isWait) {
                gameInfo = sendRequestCode(RequestCodes.DISCONNECT_WITH_RESULT);
                socket.close();
                return gameInfo;
            } else {
                gameInfo = sendRequestCode(RequestCodes.DISCONNECT_WITHOUT_RESULT);
                socket.close();
                return gameInfo;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GameInfo getInfoAboutLeftPlayer() {
        GameInfo gameInfo = sendRequestCode(RequestCodes.REQUEST_INFO_FOR_LEFT_PLAYER);
        return checkResponseCode(gameInfo);
    }

    @Override
    public GameInfo getInfoAboutRightPlayer() {
        GameInfo gameInfo = sendRequestCode(RequestCodes.REQUEST_INFO_FOR_RIGHT_PLAYER);
        return checkResponseCode(gameInfo);
    }

    @Override
    public boolean toUp() {
        return sendRequestCode(RequestCodes.CMD_UP).responseCode == 200;
    }

    @Override
    public boolean toDown() {
        return sendRequestCode(RequestCodes.CMD_DOWN).responseCode == 200;
    }

    @Override
    public boolean toLeft() {
        return sendRequestCode(RequestCodes.CMD_LEFT).responseCode == 200;
    }

    @Override
    public boolean toRight() {
        return sendRequestCode(RequestCodes.CMD_RIGHT).responseCode == 200;
    }
}