package main.pageAboutKeyboard;

import javax.swing.*;
import java.awt.*;

public class PanelKeyboard extends JPanel {
    private Image image;

    public PanelKeyboard(){
        ImageIcon referencia = new ImageIcon("assets\\sobreTeclado.png");
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
