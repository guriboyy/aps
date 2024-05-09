package main.game;

import main.game.player.Player;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel{

    private BufferedImage[] tiles;                          // Array para armazenar as imagens dos tiles
    private int[][] tileMap;                                // Array bidimensional para armazenar o mapa de tiles
    private final int TILE_SIZE = 71;                       // Tamanho do tile em pixels
    private final int MAP_WIDTH = 17;                       // Largura do mapa em tiles
    private final int MAP_HEIGHT = 12;                      // Altura do mapa em tiles
    private Player player;

    public GamePanel() {
        setFocusable(true);
        setDoubleBuffered(true);

        loadTiles();
        setPreferredSize(new Dimension(TILE_SIZE * MAP_WIDTH, TILE_SIZE * MAP_HEIGHT));
        createSampleMap();

        player = new Player();
        addKeyListener(new KeyboardListenerEvents());
    }

    private void loadTiles() {
        tiles = new BufferedImage[5];
        Utility scaleImage = new Utility();

        try {
            tiles[0] = ImageIO.read(new File("assets\\tile0.png"));
            tiles[0]  = scaleImage.scaleImage(tiles[0], TILE_SIZE, TILE_SIZE);

            tiles[1] = ImageIO.read(new File("assets\\tile1.png"));
            tiles[1]  = scaleImage.scaleImage(tiles[1], TILE_SIZE, TILE_SIZE);

            tiles[2] = ImageIO.read(new File("assets\\tile2.png"));
            tiles[2]  = scaleImage.scaleImage(tiles[2], TILE_SIZE, TILE_SIZE);

            tiles[3] = ImageIO.read(new File("assets\\tile3.png"));
            tiles[3]  = scaleImage.scaleImage(tiles[3], TILE_SIZE, TILE_SIZE);

            tiles[4] = ImageIO.read(new File("assets\\tile4.png"));
            tiles[4]  = scaleImage.scaleImage(tiles[4], TILE_SIZE, TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSampleMap() {
        tileMap = new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 4, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 2, 3, 4, 1, 1, 1, 1, 1, 1, 1, 2, 3, 4, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                int tileType = tileMap[row][col];
                if(tileType == 0){
                    BufferedImage tileImage = tiles[tileType];
                    g.drawImage(tileImage, col * TILE_SIZE, row * TILE_SIZE, null);
                }
                if(tileType == 1){
                    BufferedImage tileImage = tiles[tileType];
                    g.drawImage(tileImage, col * TILE_SIZE, row * TILE_SIZE, null);
                }
                if(tileType == 2){
                    BufferedImage tileImage = tiles[tileType];
                    g.drawImage(tileImage, col * TILE_SIZE, row * TILE_SIZE, null);
                }
                if(tileType == 3){
                    BufferedImage tileImage = tiles[tileType];
                    g.drawImage(tileImage, col * TILE_SIZE, row * TILE_SIZE, null);
                }
                if(tileType == 4){
                    BufferedImage tileImage = tiles[tileType];
                    g.drawImage(tileImage, col * TILE_SIZE, row * TILE_SIZE, null);
                }
            }
        }
        Graphics2D g2 = (Graphics2D) g;
        player.update();
        player.draw(g2);
        repaint();
    }

    private class KeyboardListenerEvents extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }

}
