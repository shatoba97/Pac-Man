package pacman.client;

import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingGameWindow extends JFrame {

    private JPanel rootPanel;
    private JTextField ipText;
    private JTextField portText;
    private JComboBox comboBox1;
    private JButton startButton;
    private JButton exitButton;

    public SettingGameWindow() {

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        startButton = new JButton("Start");
        exitButton = new JButton("Exit");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingGameWindow.this.setVisible(false);
                IPacManAPI api = new PacManAPI();
                if (comboBox1.getSelectedIndex() == 0) {
                    if (api.connection(ipText.getText(), Integer.valueOf(portText.getText()),
                            PacManAPI.GameType.SINGLE_WITH_GHOST)) {
                        new PeopleViewWindow(api, PacManAPI.GameType.SINGLE_WITHOUT_GHOST);
                    } else {
                        SettingGameWindow.this.setVisible(true);
                    }
                } else {
                    if (api.connection(ipText.getText(), Integer.valueOf(portText.getText()),
                            PacManAPI.GameType.VERSUS_WITH_GHOST)) {
                        new PeopleViewWindow(api, PacManAPI.GameType.VERSUS_WITH_GHOST);
                    } else {
                        SettingGameWindow.this.setVisible(true);
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        ipText = new JTextField("127.0.0.1");
        portText = new JTextField("7070");

        comboBox1 = new JComboBox();
        comboBox1.addItem("SINGLE");
        comboBox1.addItem("VERSUS");

        rootPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(rootPanel, BoxLayout.Y_AXIS);
        rootPanel.add(ipText);
        rootPanel.add(portText);
        rootPanel.add(comboBox1);
        rootPanel.add(startButton);
        rootPanel.add(exitButton);

        setTitle("Settings");
        setContentPane(rootPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}