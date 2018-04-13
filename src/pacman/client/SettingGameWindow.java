package pacman.client;

import pacman.api.IPacManAPI;
import pacman.api.PacManAPI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingGameWindow extends JFrame {

    private JPanel panel1;
    private JTextField ipText;
    private JTextField a7070TextField;
    private JComboBox comboBox1;
    private JButton startButton;
    private JButton exitButton;

    public SettingGameWindow() {
        setTitle("Settings");
        setContentPane(panel1);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingGameWindow.this.setVisible(false);
                IPacManAPI api = new PacManAPI();
                if (comboBox1.getSelectedIndex() == 0) {
                    if (api.connection(ipText.getText(), Integer.valueOf(a7070TextField.getText()),
                            PacManAPI.GameType.SINGLE_WITH_GHOST)) {
                        new PeopleViewWindow(api, PacManAPI.GameType.SINGLE_WITHOUT_GHOST);
                    } else {
                        SettingGameWindow.this.setVisible(true);
                    }
                } else {
                    if (api.connection(ipText.getText(), Integer.valueOf(a7070TextField.getText()),
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

        comboBox1.addItem("SINGLE");
        comboBox1.addItem("VERSUS");

        setVisible(true);
    }
}