package main.pageAboutKeyboard;

import main.utils.BackButton;

import javax.swing.*;

public class WindowKeyboard extends JFrame {
    public WindowKeyboard(){
        setTitle("Recycle Rush");
        setResizable(false);
        setSize(1223,880);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new BackButton(0,0, this));
        add(new PanelKeyboard());
        setVisible(true);
    }
}
