package GUI.animation.screens;

import GUI.animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * This class handles all cases of stopping GUI.animation with a key-press.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private Animation decorated;
    private String key;
    private Boolean stop = false;
    private boolean isAlreadyPressed = true; //for bug fix of ass6

    /**
     * constructor.
     * @param sensor keyboard.
     * @param key string.
     * @param animation to stop.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboard = sensor;
        this.key = key;
        this.decorated = animation;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.decorated.doOneFrame(d);
        if (!this.keyboard.isPressed(key)) {
            this.isAlreadyPressed = false;
        }
        if (this.keyboard.isPressed(key) && !isAlreadyPressed) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
