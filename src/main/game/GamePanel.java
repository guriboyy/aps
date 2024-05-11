package main.game;

import main.game.player.Player;
import main.tile.Tile;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel{

    private Tile[] tiles;
    //private BufferedImage[] tiles;                          // Array para armazenar as imagens dos tiles
    private int[][] tileMap;                                // Array bidimensional para armazenar o mapa de tiles
    private final int TILE_SIZE = 71;                       // Tamanho do tile em pixels
    private final int MAP_WIDTH = 17;                       // Largura do mapa em tiles
    private final int MAP_HEIGHT = 12;                      // Altura do mapa em tiles
    private Player player;
    private Player player2;

    public GamePanel() {
        setFocusable(true);
        setDoubleBuffered(true);

        loadTiles();
        setPreferredSize(new Dimension(TILE_SIZE * MAP_WIDTH, TILE_SIZE * MAP_HEIGHT));
        createSampleMap();

        player = new Player(100,680, true, tileMap);
        player2 = new Player(1010, 680, false, tileMap);
        addKeyListener(new KeyboardListenerEvents());
    }

    private void loadTiles() {
        //tiles = new BufferedImage[5];
        tiles = new Tile[5];
        Utility scaleImage = new Utility();

        try {
            for(int i = 0; i < tiles.length; i++){
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(new File("assets\\tile"+i+".png"));
                tiles[i].image = scaleImage.scaleImage(tiles[i].image, TILE_SIZE, TILE_SIZE);
                if(i == 2 || i == 3 || i == 4){
                    tiles[i].hitbox = new Rectangle(TILE_SIZE, TILE_SIZE);
                }
            }
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
        Graphics2D g2 = (Graphics2D) g;

        // Desenha os tiles
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                int tileType = tileMap[row][col];
                BufferedImage tileImage = tiles[tileType].image;
                int tileX = col * TILE_SIZE;
                int tileY = row * TILE_SIZE;
                g2.drawImage(tileImage, tileX, tileY, null);

                // Verifica se o tile é uma das plataformas 2, 3 ou 4 e desenha o hitbox
                if (tileType == 2 || tileType == 3 || tileType == 4) {
                    g2.setColor(Color.BLUE); // Cor azul para a hitbox das plataformas
                    g2.drawRect(tileX, tileY, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Atualiza e desenha o jogador
        player.update();
        player.draw(g2);

        player2.update();
        player2.draw(g2);

        // Repinta continuamente para atualizações
        repaint();
    }

    private class KeyboardListenerEvents extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            player2.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
            player2.keyReleased(e);
        }
    }

}
