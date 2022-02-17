package collisions.listeners;

import other.Counter;
import GUI.sprites.Ball;
import GUI.sprites.Block;
/**
 * class with collisions.listeners for score events tracking.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    /**
     * constructor.
     * @param scoreCounter .
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    /**
     * in case user hit a block - add 5 scores.
     * @param beingHit .
     * @param hitter .
     */
    public void hitEvent(Block beingHit, Ball hitter) {
       this.currentScore.increase(5);
    }
}
