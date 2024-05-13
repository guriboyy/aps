package main.utils;

import main.pageFirst.Firstpage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BackButton extends JButton {
    private JFrame screenParent;

    public BackButton(int x, int y, JFrame screenParent){
        this.screenParent = screenParent;
        setText("Voltar");
        setBounds(x,y,250,60);
        setBackground(Color.red);
        setBackground(new Color(112, 178, 73));
        setVisible(true);
        addActionListener(this::action);
    }

    private void action(ActionEvent e){
        screenParent.dispose();
        new Firstpage();
    }
}
