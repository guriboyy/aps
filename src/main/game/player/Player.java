package main.game.player;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    public int x,y,dx,dy;

    public Player() {
        x = 100;
        y = 100;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fillRect(x,y,100,100);
    }


    public void update(){
        x += dx;
        y += dy;
    }

    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            dy = -1;
        }
        if (code == KeyEvent.VK_S) {
            dy = 1;
        }
        if (code == KeyEvent.VK_A) {
            dx = -1;
        }
        if (code == KeyEvent.VK_D) {
            dx = 1;
        }
    }

    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();


        if (code == KeyEvent.VK_W) {
            dy = 0;
        }
        if (code == KeyEvent.VK_S) {
            dy = 0;
        }
        if (code == KeyEvent.VK_A) {
            dx = 0;
        }
        if (code == KeyEvent.VK_D) {
            dx = 0;
        }
    }
}
