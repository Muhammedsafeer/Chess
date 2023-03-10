import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class BoardManager {
    GamePanel gp;
    Piece[] piece;

    int[][] map ={
            {0, 1, 2, 3, 4, 2, 1, 0},
            {5, 5, 5, 5, 5, 5, 5, 5},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {11, 11, 11, 11, 11, 11, 11, 11},
            {6, 7, 8, 9, 10, 8, 7, 6}
    };


    // Chess Pieces Image
    BufferedImage mainImg;

    public BoardManager(GamePanel gp) {

        this.gp = gp;

        piece = new Piece[12];

        getPiecesImage();
    }

    void getPiecesImage() {
        // Get the image of the pieces
        try {

            // Get Main Image
            InputStream is = getClass().getResourceAsStream("/pieces.png");
            mainImg = ImageIO.read(is);

            // Get Pieces Image
            piece[0] = new Piece();
            piece[0].image = mainImg.getSubimage(0, 0, 132, 132);

            piece[1] = new Piece();
            piece[1].image = mainImg.getSubimage(132, 0, 132, 132);

            piece[2] = new Piece();
            piece[2].image = mainImg.getSubimage(264, 0, 132, 132);

            piece[3] = new Piece();
            piece[3].image = mainImg.getSubimage(396, 0, 132, 132);

            piece[4] = new Piece();
            piece[4].image = mainImg.getSubimage(528, 0, 132, 132);

            piece[5] = new Piece();
            piece[5].image = mainImg.getSubimage(660, 0, 132, 132);

            piece[6] = new Piece();
            piece[6].image = mainImg.getSubimage(0, 132, 132, 132);

            piece[7] = new Piece();
            piece[7].image = mainImg.getSubimage(132, 132, 132, 132);

            piece[8] = new Piece();
            piece[8].image = mainImg.getSubimage(264, 132, 132, 132);

            piece[9] = new Piece();
            piece[9].image = mainImg.getSubimage(396, 132, 132, 132);

            piece[10] = new Piece();
            piece[10].image = mainImg.getSubimage(528, 132, 132, 132);

            piece[11] = new Piece();
            piece[11].image = mainImg.getSubimage(660, 132, 132, 132);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void update() {

        if (gp.mousePressed) {
            int x = gp.mouseX / GamePanel.SQUARE_SIZE;
            int y = gp.mouseY / GamePanel.SQUARE_SIZE;
        }
    }

    public void draw(Graphics2D g2d) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != -1) {
                    g2d.drawImage(piece[map[i][j]].image, j * GamePanel.SQUARE_SIZE, i * GamePanel.SQUARE_SIZE, GamePanel.SQUARE_SIZE, GamePanel.SQUARE_SIZE, null);
                }
            }
        }
    }
}
