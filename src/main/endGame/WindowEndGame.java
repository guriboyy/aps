package main.endGame;

import main.game.player.Player;
import main.utils.BackButton;

import javax.swing.*;
import java.awt.*;

public class WindowEndGame extends JFrame {
    public WindowEndGame(Player winnerPlayer){
        String text = winnerPlayer.isMan() ? "1": "2";
        setTitle("Recycle Rush");
        setResizable(false);
        setSize(1223,880);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new BackButton(0,0, this));


        JLabel label = new JLabel("O Player " + text + " é o vencedor");
        Font font = new Font("Arial", Font.BOLD, 32);
        label.setFont(font);
        label.setBounds(100,340, 500,100);
        add(label);


        JLabel labelCoin = new JLabel("Acumulou "+ winnerPlayer.getPoints()+ " pontos.");
        Font fontCoin = new Font("Arial", Font.BOLD, 32);
        labelCoin.setFont(fontCoin);
        labelCoin.setBounds(100,440, 500,100);
        add(labelCoin);


        add(new PanelEndGame());
        setVisible(true);
    }
}
