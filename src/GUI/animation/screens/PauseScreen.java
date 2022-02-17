package GUI.animation.screens;

import GUI.animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * Pause-screen. the screen get closed when pressing space.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;

    /**
     * constructor.
     * @param k keyboard.
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
    }

    /**
     * draws the screen and checking if space has been hit.
     * @param d surface to draw on.
     */
    public void doOneFrame(DrawSurface d) {
        d.drawText(130, d.getHeight() / 2, "paused -- press space to continue", 32);
    }
    /**
     * should stop.
     * @return this.stop boolean.
     */
    public boolean shouldStop() {
        return false; //the real should stop is in the decorator KeyPressStoppableAnimation
    }
}
