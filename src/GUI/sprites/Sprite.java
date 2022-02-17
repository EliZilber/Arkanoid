package GUI.sprites;

import biuoop.DrawSurface;
/**
 * GUI.sprites.Sprite - Interface of sprite.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public interface Sprite {
    /**
     * draw on the sprite on a given surface.
     * @param d draw-surface.
     */
    void drawOn(DrawSurface d);
    /**
     * notify the sprite that time has passed. Inside it the sprite can perform required
     * actions at when timePassed.
     */
    void timePassed();
}