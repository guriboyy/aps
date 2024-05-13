package main.game;

import main.endGame.WindowEndGame;
import main.game.player.Player;
import main.objects.NotRecyclable;
import main.objects.Recyclable;
import main.objects.TrashObject;
import main.pageFirst.Firstpage;
import main.tile.Tile;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    BufferedImage healthImage;

    private int timerValue = 0;

    private List<TrashObject> objects;
    private List<Integer> objectsPositions = new ArrayList<Integer>();
    private BufferedImage coin;

    String[] noRecs = {
           "pedra",
           "pizza",
           "radioativo",
           "lata"
    };
    String[] recs = {
            "agua",
            "papel",
            "omo",
            "garrafa"
    };

    private JFrame screenGame;

    public GamePanel(JFrame screenGame) {
        setFocusable(true);
        setDoubleBuffered(true);
        this.screenGame = screenGame;

        loadTiles();
        setPreferredSize(new Dimension(TILE_SIZE * MAP_WIDTH, TILE_SIZE * MAP_HEIGHT));
        createSampleMap();

        player = new Player(true, 100,680, true, tileMap);
        player2 = new Player(false, 1010, 680, false, tileMap);

        addKeyListener(new KeyboardListenerEvents());
        initObjects();
        startGameLoop();
        startTimer();
        try {
            coin = ImageIO.read(new File("assets\\coins.png"));
            healthImage = ImageIO.read(new File("assets\\health.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGameLoop() {
        Timer timer = new Timer(10, e -> {
            for (TrashObject currentObj : objects) {
                currentObj.update(getHeight());
            }
            repaint();
        });
        timer.start();
    }
    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            // Incrementa o temporizador a cada segundo
            timerValue++;
            repaint();
        });
        timer.start();
    }
    private void initObjects(){
        objects = new ArrayList<TrashObject>();
        boolean recyclable = false;
        for(int i = 0; i < 10; i++){
            int x, y;
            boolean validPosition;

            do {
                validPosition = true; // Assume que a posição é válida até prova em contrário
                x = (int) (Math.random() * 1207);
                y = (int) (Math.random() * 852);

                // Verifica se a nova posição está muito próxima de algum objeto existente
                for (TrashObject obj : objects) {
                    if (Math.abs(obj.getX() - x) < 60 && Math.abs(obj.getY() - y) < 60) {
                        validPosition = false; // Nova posição não é válida, está muito próxima de um objeto existente
                        break; // Sai do loop ao encontrar uma posição inválida
                    }
                }
            } while (!validPosition);
            objectsPositions.add(x);

            String imagePath;

            if(i == 0){
                recyclable = Math.random() < 0.5;
            }else{
                recyclable = !recyclable;
            }

            if(recyclable) {
                int randIndex = (int) (Math.random() * 4);
                imagePath = "assets/"+recs[randIndex]+".png";
                objects.add(new Recyclable(x, y, imagePath));
            } else {
                int randIndex = (int) (Math.random() * 4);
                imagePath = "assets/"+noRecs[randIndex]+".png";
                objects.add(new NotRecyclable(x, y, imagePath));
            }
        }
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
        if(player.getHealth() == 0){
            screenGame.dispose();
            new WindowEndGame(player2);
        } else if (player2.getHealth() == 0) {
            screenGame.dispose();
            new WindowEndGame(player);
        }

        // Desenha os tiles
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                int tileType = tileMap[row][col];
                BufferedImage tileImage = tiles[tileType].image;
                int tileX = col * TILE_SIZE;
                int tileY = row * TILE_SIZE;
                g2.drawImage(tileImage, tileX, tileY, null);
            }
        }

        for (TrashObject currentObj : objects) {
            if(currentObj.isVisivel()){
                g2.drawImage(currentObj.getImage(), currentObj.getX(), currentObj.getY(), this);
            }
        }

        // Atualiza e desenha o jogador
        player.update();
        player.checkCollision(objects,objectsPositions);
        player.draw(g2);
        g2.drawImage(coin, 160, 10, this);

        String points = Integer.toString(player.getPoints());
        Font font = new Font("Arial", Font.PLAIN, 20);
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics(font);
        int x = 200;
        int y = 33;
        g2.drawString(points, x, y);

        player2.update();
        player2.checkCollision(objects,objectsPositions);
        player2.draw(g2);
        g2.drawImage(coin, 980, 10, this);
        String points2 = Integer.toString(player2.getPoints());
        int xPoint2 = 960;
        int yPoint2 = 33;
        g2.drawString(points2, xPoint2, yPoint2);

        // Repinta continuamente para atualizações

        for(int i = 0; i < player.getHealth(); i++){
            g2.drawImage(healthImage, i*40, 0,this);
        }
        for(int i = 0; i < player2.getHealth(); i++){
            g2.drawImage(healthImage, 1140-(i*40), 0,this);
        }
        String timerText = Integer.toString(timerValue);
        int xTime = (getWidth() - metrics.stringWidth(timerText)) / 2;
        int yTime = 33; // Defina a posição vertical conforme necessário
        g2.drawString(timerText, xTime, yTime);
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
