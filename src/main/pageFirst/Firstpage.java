package main.pageFirst;

import javax.swing.*;
import java.awt.*;

public class Firstpage extends JFrame {
    public Firstpage(){
        setTitle("Recycle Rush");
        setResizable(false);
        setSize(1223,880);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new Button("Jogar",200, 330, new Color(112, 178, 73), this,1));
        add(new Button("Teclado",200, 430, new Color(112, 178, 73), this,2));
        add(new Button("Regras",200, 530, new Color(112, 178, 73), this, 3));
        add(new Backgroung());
        setVisible(true);
    }

}