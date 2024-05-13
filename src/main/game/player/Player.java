package main.game.player;
import java.util.List;
import main.game.Sprite;
import main.objects.NotRecyclable;
import main.objects.Recyclable;
import main.objects.TrashObject;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player {
    public int x,y,dx,dy;
    public boolean isJump;
    public Rectangle hitbox;
    private int[][] tileMap;                                // Array bidimensional para armazenar o mapa de tiles
    private final int TILE_SIZE = 71;
    private final int MAP_WIDTH = 17;                       // Largura do mapa em tiles
    private final int MAP_HEIGHT = 12;
    private long jumpStartTime = 0;  // Tempo de início do pulo
    private final long MAX_JUMP_DURATION = 430;  // Duração máxima do pulo em milissegundos (500ms)

    private Sprite sprite;
    private BufferedImage currentFrame;
    private int frameIndex;
    private long lastFrameTime;
    private int animationSpeed = 100; // tempo em milissegundos
    private int health = 3;
    private int points = 0;

    private boolean playerIsMoving = false;
    private boolean facingRight, isMovingRight, isMovingLeft = false;

    private Sprite spriteStop;
    private BufferedImage stopImg;

    private long idleTime = System.currentTimeMillis();
    private final long MAX_IDLETIME = 200;

    private boolean isLeftKeyboard;
    private int w;
    private int a;
    private int d;

    private boolean isMan;

    public Player(boolean isMan,int x,int y,boolean isLeftKeyboard,int[][] tileMap) {
        this.x = x;
        this.y = y;
        this.isLeftKeyboard = isLeftKeyboard;
        this.w = isLeftKeyboard ? KeyEvent.VK_W : KeyEvent.VK_UP;
        this.a = isLeftKeyboard ? KeyEvent.VK_A : KeyEvent.VK_LEFT;
        this.d = isLeftKeyboard ? KeyEvent.VK_D : KeyEvent.VK_RIGHT;

        this.isMan = isMan;


        hitbox = new Rectangle(x,y,110,109);
        this.tileMap = tileMap;
        String sexo = isMan ?  "Homem" : "Mulher";
        sprite = new Sprite("assets\\Run"+ sexo +".png", 200, 109); // Supondo que cada frame tem 71x100 pixels
        currentFrame = sprite.getSprite(0, 0); // Começa com o primeiro frame

        spriteStop = new Sprite("assets\\Idle"+ sexo +".png", 200, 109); // Supondo que cada frame tem 71x100 pixels
        stopImg = spriteStop.getSprite(0, 0); // Começa com o primeiro frame
    }

    public void draw(Graphics2D g2){
        if(!playerIsMoving && System.currentTimeMillis() - idleTime > MAX_IDLETIME){
            g2.drawImage(stopImg, x, y, 200, 109, null);

        }else {
            if (facingRight) {
                g2.drawImage(currentFrame, x, y, 200, 109, null);
            } else {
                // Desenha a imagem invertida horizontalmente
                g2.drawImage(currentFrame, x + 200, y, -200, 109, null);
            }
        }
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime > animationSpeed) {
            frameIndex++;
            if (frameIndex >= 8) frameIndex = 0; // Supondo que tem 5 frames por ação
            currentFrame = sprite.getSprite(frameIndex, 0); // atualiza o frame baseado no índice
            lastFrameTime = currentTime;
        }
        long jumpDuration = System.currentTimeMillis() - jumpStartTime;
        int windowWidth = 1207;
        int windowHeight = 852;

        // Calcula nova posição potencial
        int newX = x + dx;
        int newY = y + dy;

        Rectangle potentialHitbox = new Rectangle(newX, newY, hitbox.width, hitbox.height);

        // Verifica colisão com as plataformas
        boolean collideVertical = false;

        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                int tileType = tileMap[row][col];
                if (tileType == 2 || tileType == 3 || tileType == 4) {
                    Rectangle tileRect = new Rectangle(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if (potentialHitbox.intersects(tileRect)) {
                        // Determina a natureza da colisão
                        if (Math.abs(potentialHitbox.y + hitbox.height - tileRect.y) < 5) {
                            y-=1;
                            collideVertical = true;
                        }
                    }
                }
            }
        }

        // Aplica movimento horizontal se não houver colisão horizontal
        if (newX >= -90 && newX + hitbox.width+3 <= windowWidth) {
            x = newX;
        }


        // Aplica movimento vertical se não houver colisão vertical
        if (!collideVertical) {
            if (newY >= 0 && newY + hitbox.height <= windowHeight) {
                y = newY;
            }
        } else {
            dy = 0; // Impede movimento vertical contínuo em caso de colisão vertical
        }

        // Lógica de pular e gravidade
        if (isJump && jumpDuration < MAX_JUMP_DURATION) {
            if (y > 0) {
                y -= 1;
            } else {
                isJump = false;
            }
        } else {
            if (y < 680) {
                y += 1;
            }
        }

        if (isMovingRight) {
            hitbox.setLocation(x+20, y);
            facingRight = true; // Estado para rastrear a direção do sprite
        } else if (isMovingLeft) {
            hitbox.setLocation(x+70, y);
            facingRight = false; // Atualiza a direção do sprite
        }else {
            if(facingRight){
                hitbox.setLocation(x+20, y);
            }else{
                hitbox.setLocation(x+70, y);
            }
        }

    }

    public void loseHealth(){
        this.health -= 1;
    }
    public int getHealth(){
        return health;
    }

    public void addPoints(){
        this.points += 1;
    }
    public int getPoints(){
        return points;
    }
    public void checkCollision(List<TrashObject> objects, List<Integer> objectsPositions) {
        Rectangle playerRect = new Rectangle(x, y, 110, 109); // Use as dimensões do hitbox do jogador

        for (TrashObject obj : objects) {
            if(obj.isCollided){
                continue;
            }
            Rectangle objRect = new Rectangle(obj.getX(), obj.getY(), obj.width, obj.height);
            if (playerRect.intersects(objRect)) {
                if (obj instanceof Recyclable) {
                    addPoints(); // Ganha pontos se for reciclável
                } else if (obj instanceof NotRecyclable) {
                    loseHealth(); // Perde vida se não for reciclável
                }
                obj.setVisivel(false); // Definir visibilidade do objeto como falso
                obj.isCollided = true;// Remove o objeto do jogo
                int newPosition = getValidPosition(objectsPositions, 100); // Limite de 100 tentativas
                if(newPosition == -1){
                    newPosition = (int) (Math.random() * 1207);
                }
                obj.setTimer(newPosition);
            }
        }
    }

    public int getValidPosition(List<Integer> objectsPositions, int maxTries) {
        int x;
        boolean validPosition;
        int tries = 0;

        do {
            validPosition = true; // Assume que a posição é válida até prova em contrário
            x = (int) (Math.random() * 1207);

            // Verifica se a nova posição está muito próxima de alguma posição já ocupada
            for (Integer pos : objectsPositions) {
                if (Math.abs(pos - x) < 40) {
                    validPosition = false; // Nova posição não é válida, está muito próxima de uma posição ocupada
                    break; // Sai do loop ao encontrar uma posição inválida
                }
            }

            tries++;
        } while (!validPosition && tries < maxTries);

        if (!validPosition) {
            // Se não encontrou posição válida após o número máximo de tentativas, retorna -1 ou outro valor indicando falha
            return -1;
        }

        objectsPositions.add(x); // Adiciona a nova posição à lista de posições ocupadas

        return x; // Retorna a posição válida
    }



    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        if (code == w) {
            playerIsMoving = true;
            if(jumpStartTime == 0){
                jumpStartTime = System.currentTimeMillis();
            }
            isJump = true;
            idleTime = 0;
        }
        if (code == a) {
            playerIsMoving = true;
            isMovingLeft = true;
            dx = -1;
            idleTime = 0;
        }
        if (code == d) {
            playerIsMoving = true;
            isMovingRight = true;
            dx = 1;
            idleTime = 0;
        }
    }

    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();

        if (code == w) {
            playerIsMoving = false;
            jumpStartTime = 0;
            isJump = false;
            if(idleTime == 0){
                idleTime = System.currentTimeMillis();
            }
        }
        if (code == a) {
            playerIsMoving = false;
            isMovingLeft = false;
            dx = 0;
            if(idleTime == 0){
                idleTime = System.currentTimeMillis();
            }
        }
        if (code == d) {
            playerIsMoving = false;
            isMovingRight = false;
            dx = 0;
            if(idleTime == 0){
                idleTime = System.currentTimeMillis();
            }
        }
    }

    public boolean isMan(){
        return isMan;
    }
}
