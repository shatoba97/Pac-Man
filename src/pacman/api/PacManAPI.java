package pacman.api;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class PacManAPI implements IPacManAPI {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private Gson gson = new Gson();

    public enum GameType {SINGLE, VERSUS, VIEW};

    private GameInfo sendRequestCode(int requestCode) {
        synchronized (this) {
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

        int type = 0;
        switch (gameType) {
            case SINGLE: type = RequestCodes.SINGLE_GAME; break;
            case VERSUS: type = RequestCodes.VERSUS_GAME; break;
            case VIEW: type = RequestCodes.VIEW_GAME; break;
            default: type = RequestCodes.SINGLE_GAME; break;
        }

        return sendRequestCode(type).responseCode == 200;
    }

    @Override
    public GameInfo disconnect(boolean isWait) {
        GameInfo gameInfo;
        if (isWait) {
            gameInfo = sendRequestCode(RequestCodes.DISCONNECT_WITH_RESULT);
        } else {
            gameInfo = sendRequestCode(RequestCodes.DISCONNECT_WITHOUT_RESULT);
        }
        try {
            socket.close();
            return gameInfo;
        } catch (IOException e) {
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
    public GameInfo getPlayingGameRoomList() {
        GameInfo gameInfo = sendRequestCode(RequestCodes.REQUEST_LIST_OF_GAME_ROOM);
        return checkResponseCode(gameInfo);
    }

    @Override
    public boolean chooseGameRoom(int id) {
        if(sendRequestCode(RequestCodes.NOTIFY_SERVER_ABOUT_CHOOSE_GAME_ROOM_ID).responseCode == 200) {
            return sendRequestCode(id).responseCode == 200;
        } else {
            return false;
        }
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

    @Override
    public ViewProperties getViewProperties() {
        ViewProperties viewProperties;
        try {
            output.writeInt(RequestCodes.REQUEST_VIEW_PROPERTIES);
            String responseString = input.readUTF();
            viewProperties = gson.fromJson(responseString, ViewProperties.class);
        } catch (IOException ignored) {
            return null;
        }
        return viewProperties;
    }
}