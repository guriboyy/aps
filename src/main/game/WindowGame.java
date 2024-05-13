package main.game;

import javax.swing.*;
import java.awt.*;

public class WindowGame extends JFrame {
    public WindowGame(){
        setTitle("Recycle Rush");
        setResizable(false);
        setSize(1223,880);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GamePanel gamePanel = new GamePanel(this);
        gamePanel.setLayout(null);
        add(gamePanel);
        setVisible(true);
    }
}
