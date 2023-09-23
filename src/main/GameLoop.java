package main;

public class GameLoop implements Runnable {

    GamePanel gamePanel;

    private static final int TARGET_UPS = 60;
    private static final int OPTIMAL_UPDATE_TIME = 1000000000 / TARGET_UPS;
    private static  final int MAX_FRAME_SKIPS = 5;

    private boolean isRunning = false;
    private Thread gameThread;
    private long lastUpdateTime;
    private long lastRenderTime;
    private long updateCount;
    private long frameCount;
    private long lastStatsTime;

    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void stop() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        lastUpdateTime = System.nanoTime();
        lastRenderTime = System.nanoTime();
        lastStatsTime = System.nanoTime();

        while (isRunning) {
            long currentTime = System.nanoTime();
            long elapsedUpdateTime = currentTime - lastUpdateTime;
            long elapsedRenderTime = currentTime - lastRenderTime;

            if (elapsedUpdateTime >= OPTIMAL_UPDATE_TIME) {
                gamePanel.update();
                lastUpdateTime = currentTime;
                updateCount++;

                if (elapsedRenderTime >= OPTIMAL_UPDATE_TIME) {
                    gamePanel.repaint();
                    lastRenderTime = currentTime;
                    frameCount++;
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int frameSkips = 0;
                while (currentTime - lastUpdateTime >= OPTIMAL_UPDATE_TIME && frameSkips < MAX_FRAME_SKIPS) {
                    gamePanel.update();
                    lastUpdateTime += OPTIMAL_UPDATE_TIME;
                    frameSkips++;
                }
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Print FPS and UPS
            if (currentTime - lastStatsTime >= 1000000000) {
                System.out.println(String.format("FPS: %s, UPS: %s", frameCount, updateCount));
                frameCount = 0;
                updateCount = 0;
                lastStatsTime = currentTime;
            }
        }
    }
}
