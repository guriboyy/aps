package main.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite{
    private BufferedImage spriteSheet;
    private int tileWidth, tileHeight;

    public Sprite(String path, int tileWidth, int tileHeight) {
        try {
            spriteSheet = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public BufferedImage getSprite(int col, int row) {
        return spriteSheet.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
    }
}