package GUI.animation.screens;

import GUI.animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import other.Counter;

/**
 * End-screen. the screen get closed when pressing space.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean gameOver; //false if user finished all levels. true if died.
    private Counter score;
    /**
     * Constructor.
     * @param k keyboard.
     * @param gameOver .
     * @param score the score amount when game paused.
     */
    public EndScreen(KeyboardSensor k, boolean gameOver, Counter score) {
        this.keyboard = k;
        this.gameOver = gameOver;
        this.score = score;
    }

    /**
     * draws the screen and checking if space has been hit.
     * @param d surface to draw on.
     */
    public void doOneFrame(DrawSurface d) {
        if (gameOver) {
            d.drawText(150, d.getHeight() / 2 - 100, "Game Over. Your score is " + score.getValue(), 32);
        } else {
            d.drawText(150, d.getHeight() / 2 - 100, "You Win! Your score is " + score.getValue(), 32);
        }
        d.drawText(220, d.getHeight() / 2 +100, "press space to exit", 32);
    }
    /**
     * should stop.
     * @return this.stop boolean.
     */
    public boolean shouldStop() {
        return false; //the real should stop is in the decorator KeyPressStoppableAnimation
    }
}
