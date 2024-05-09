package main.pageFirst;

import main.game.WindowGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Button extends JButton {

    private JFrame parentFrame;

    public Button(String title, int x, int y, Color color, JFrame parentFrame, int typeAction){
        this.parentFrame = parentFrame;
        setText(title);
        setBounds(x,y,250,60);
        setBackground(color);
        setVisible(true);

        changeActionListener(typeAction);
    }

    private void changeActionListener(int typeAction){
        switch (typeAction){
            case 1:
                addActionListener(this::initGame);
                break;
            case 2:
                addActionListener(this::keyboard);
                break;
            case 3:
                addActionListener(this::rules);
                break;
        }
    }

    private void initGame(ActionEvent action){
        parentFrame.dispose();
        new WindowGame();
    }

    private void keyboard(ActionEvent action){
        System.out.println("Teclado");
    }

    private void rules(ActionEvent action){
        System.out.println("Regras");
    }

}
