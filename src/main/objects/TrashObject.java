package main.objects;

import javax.swing.*;
import java.awt.*;

public abstract class TrashObject {
    public int x, y;
    public Image image;
    public int width, height;
    private final int SPEED = 1;
    public boolean isVisivel;
    public String pathImage;
    public boolean isCollided = false;
    private Timer timer;

    public TrashObject(int x, int y, String pathImage) {
        this.x = x;
        this.y = y;
        this.isVisivel = true;
        this.pathImage = pathImage;
        load();
    }

    public void load() {
        ImageIcon ref = new ImageIcon(pathImage);
        this.image = ref.getImage();

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void update(int screenHeight) {
        this.y += SPEED;
        if (this.y > screenHeight) {
            resetObject();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public boolean isVisivel() {
        return isVisivel;
    }

    public void setVisivel(boolean isVisivel) {
        this.isVisivel = isVisivel;
    }
    public boolean isForaTela(int screenHeight) {
        return y > screenHeight;
    }

    public void setTimer(int newX){
        timer = new Timer(1000, e -> {
            resetObject(newX);
            timer.stop(); // Para o timer após a primeira execução
        });
        timer.start();
    }

    public void resetObject(int... newX) {
        if (newX.length > 0) {
            x = newX[0];
        } else {
            x = (int) (Math.random() * 1207);
        }
        y = 0;
        setVisivel(true);
        isCollided = false;
    }

}