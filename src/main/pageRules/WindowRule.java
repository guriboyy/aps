package main.pageRules;


import main.utils.BackButton;

import javax.swing.*;
import java.awt.*;

public class WindowRule extends JFrame {
    public WindowRule(){
        setTitle("Recycle Rush");
        setResizable(false);
        setSize(1223,880);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new BackButton(0,0, this));
        add(new Panel());
        setVisible(true);
    }
}
