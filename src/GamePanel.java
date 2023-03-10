import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Length and width of one square on the board
    public static final int SQUARE_SIZE = 100;
    // Number of squares on the board
    public static final int BOARD_SIZE = 8;
    // Length and width of the board
    public static final int BOARD_WIDTH = SQUARE_SIZE * BOARD_SIZE;
    public static final int BOARD_HEIGHT = SQUARE_SIZE * BOARD_SIZE;

    // Mouse
    public int mouseX, mouseY;
    public boolean mousePressed;

    public BoardManager bm = new BoardManager(this);
    private final MouseListener ml = new MouseListener();

    // Thread for the game
    private Thread thread;


    public GamePanel() {
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addMouseListener(ml);
        this.setFocusable(true);
        startGame();
    }

    void startGame() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        // Game Speed
        int FPS = 60;
        double timePerFrame = 1000000000.0 / FPS;
        int UPS = 60;
        double timePerUpdate = 1000000000.0 / UPS;


        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (thread != null) {

            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }

    }
    void update() {

        mouseX = ml.mouseX;
        mouseY = ml.mouseY;
        mousePressed = ml.mousePressed;

        System.out.println(mousePressed);

        bm.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        // Draw the game

        // Drawing Chess Board
        // Color A = 239, 217, 183
        // Color B = 181, 136, 99
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.setColor(new Color(239, 217, 183));
                } else {
                    g2d.setColor(new Color(181, 136, 99));
                }
                g2d.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }





        bm.draw(g2d);

        g2d.dispose();
    }
}
