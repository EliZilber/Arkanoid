package GUI.animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import GUI.defines.MyDefines;
/**
 * Animation.AnimationRunner - has a run method to run given GUI.animation. The gui to draw on is defined in constructor.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper = new Sleeper();
    /**
     * constructor.
     * @param gui to attach the GUI.animation to.
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = MyDefines.FPS;
    }
    /**
     * getter.
     * @return gui.
     */
    public GUI getGui() {
        return this.gui;
    }
    /**
     * run GUI.animation.
     * @param animation runs this given GUI.animation.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d);

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
