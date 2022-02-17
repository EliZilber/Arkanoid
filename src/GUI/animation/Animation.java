package GUI.animation;

import biuoop.DrawSurface;
/**
 * Animation interface.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public interface Animation {
    /**
     * Code lines to do on every frame.
     * @param d surface to draw on.
     */
    void doOneFrame(DrawSurface d);
    /**
     * Put here conditions to stop the GUI.animation.
     * @return true - should stop. false - continue run.
     */
    boolean shouldStop();
}