package tile;

import core.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Ajusta el tamaño según cuántos tiles tengas
        tile = new Tile[20];

        // Ajusta el tamaño de la matriz según tu mundo
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        // Cambia la ruta y el nombre del archivo si tu imagen tiene otro nombre
        loadMap("/maps/map50def.png");
    }

    /**
     * Carga las imágenes de cada tile en un arreglo.
     * Ajusta la colisión (collision) según lo que desees.
     */
    public void getTileImage() {
        try {
            // Tile 0: grass01
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tile/grass01.png"));
            tile[0].collision = false;

            // Tile 1: path01
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tile/path01.png"));
            tile[1].collision = false;

            // Tile 2: path02
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tile/path02.png"));
            tile[2].collision = false;

            // Tile 3: path03
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tile/floor.png"));
            tile[3].collision = false;

            // Tile 4: earth01
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tile/WallHoriz.png"));
            tile[4].collision = true;

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tile/WallHorizDown.png"));
            tile[11].collision = true;

            // Tile 5: earth02
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tile/WallVertiLeft.png"));
            tile[5].collision = true;

            // Tile 5: earth02
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tile/WallVertiRight.png"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tile/WallRightUp.png"));
            tile[7].collision = true;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tile/WallRightDown.png"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tile/WallLeftUp.png"));
            tile[9].collision = true;

            // Tile 5: earth02
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tile/WallLeftDown.png"));
            tile[10].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga el mapa desde una imagen y asigna a cada píxel un número de tile según su color.
     * Debes usar EXACTAMENTE estos colores al pintar tu mapa.
     */
    public void loadMap(String filePath) {
        try {
            BufferedImage mapImage = ImageIO.read(getClass().getResourceAsStream(filePath));
            int width = mapImage.getWidth();
            int height = mapImage.getHeight();

            // Verifica que la imagen tenga las dimensiones esperadas
            if (width != gp.maxWorldCol || height != gp.maxWorldRow) {
                System.out.println("La imagen del mapa debe ser de " + gp.maxWorldCol + " x " + gp.maxWorldRow);
                return;
            }

            // Mapeo de color (en hex) a índice de tile
            // Ajusta los colores según tus preferencias. Todos están en formato 0xRRGGBB.
            HashMap<Integer, Integer> colorToNumber = new HashMap<>();
            colorToNumber.put(0xFF0000, 0);   // grass01
            colorToNumber.put(0x00FF00, 1);   // path01
            colorToNumber.put(0x0000FF, 2);   // path02
            colorToNumber.put(0xFF8000, 3);   // floor
            colorToNumber.put(0x00C040, 4);  // wallHorizUp
            colorToNumber.put(0xB80670, 11);  // wallHorizDown
            colorToNumber.put(0x00C0FF, 5);  // wallVertiLeft
            colorToNumber.put(0x800000, 6);  // wallVertiRight
            colorToNumber.put(0x808080, 7);  // wallRightUp
            colorToNumber.put(0xFFFF00, 8);  // WallRightDown
            colorToNumber.put(0xC0C0C0, 9);  // WallLeftUp
            colorToNumber.put(0x800080, 10); // WallLeftDown


            // Recorremos cada píxel de la imagen y asignamos el índice de tile
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Tomamos el color del píxel (sin alfa)
                    int rgb = mapImage.getRGB(x, y) & 0xFFFFFF;
                    // Obtenemos el índice de tile correspondiente
                    int tileIndex = colorToNumber.getOrDefault(rgb, -1);
                    if (tileIndex == -1) {
                        System.out.println("Color no mapeado en (" + x + "," + y + "): " + String.format("#%06X", rgb));
                        // Puedes decidir qué hacer aquí: dejar -1, poner un tile por defecto, etc.
                    }
                    mapTileNum[x][y] = tileIndex;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dibuja los tiles en pantalla según la posición del jugador.
     */
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Solo dibuja los tiles que están dentro de la "ventana" del jugador
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
