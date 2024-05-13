package main.endGame;

import main.game.player.Player;

import javax.swing.*;
import java.awt.*;

public class PanelEndGame extends JPanel {
    private Image image;

    public PanelEndGame(){
        ImageIcon referencia = new ImageIcon("assets\\endGame.png");
        image = referencia.getImage();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graficos = (Graphics2D) g;
        int widthWindow = getWidth();
        int heightWindow = getHeight();
        graficos.drawImage(image, 0, 0, widthWindow, heightWindow, null);
        graficos.dispose();
    }
}
