package collisions;

import collisions.listeners.HitListener;
import other.Counter;
import other.GameLevel;
import GUI.sprites.Ball;
import GUI.sprites.Block;
/**
 * holds ball remove code.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * constructor.
     * @param gameLevel .
     * @param remainingBalls .
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * balls that are touching the floor - should be removed from the game.
     * @param beingHit .
     * @param hitter .
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.remainingBalls.decrease(1);
        hitter.removeHitListener(this);
    }
}
