package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;

    final int cellSize = tileSize + 16;
    final int boardCol = 8;
    final int boardRow = 8;
    final int boardWidth = boardCol * cellSize;
    final int boardHeight = boardRow * cellSize;

    final int screenWidth = boardWidth + tileSize * 6;
    final int screenHeight = boardHeight + tileSize;

    final int boardX = (screenWidth - boardWidth) / 2;
    final int boardY = (screenHeight - boardHeight) / 2;

    Color backgroundColor = new Color(0, 20, 10);

    MouseHandler mouseHandler = new MouseHandler();

    int playerX = 100;
    int playerY = 100;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(backgroundColor);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        this.setFocusable(true);
    }

    public void update() {
        if (mouseHandler.isLeftPressed) {
            playerX = mouseHandler.x;
            playerY = mouseHandler.y;
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        // Draw all the cells
        for (int row = 0; row < boardRow; row++) {
            for (int col = 0; col < boardCol; col++) {
                int x = boardX + col * cellSize;
                int y = boardY + row * cellSize;
                if ((row + col) % 2 == 0) {
                    g2d.setColor(Color.WHITE);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.fillRect(x, y, cellSize, cellSize);
            }
        }

        g2d.dispose();
    }
}
