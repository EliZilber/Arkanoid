package collisions.listeners;

import GUI.sprites.Ball;
import GUI.sprites.Block;

/**
 * Hit-listener.
 */
public interface HitListener {
    // This method is called whenever the beingHit object is hit.
    // The hitter parameter is the Ball that's doing the hitting.

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit .
     * @param hitter .
     */
    void hitEvent(Block beingHit, Ball hitter);
}
