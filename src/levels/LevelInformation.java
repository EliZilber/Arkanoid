package levels;

import other.Velocity;
import GUI.sprites.Block;
import GUI.sprites.Sprite;

import java.util.List;
/**
 * LevelInformation interface contains all the basic info a level has.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public interface LevelInformation {
    /**
     * number of the balls in this game.
     * @return int.
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     * <p>Note that initialBallVelocities().size() == numberOfBalls() </p>
     * @return velocities list.
     */
    List<Velocity> initialBallVelocities();
    /**
     * @return paddle speed.
     */
    int paddleSpeed();
    /**
     * @return paddle speed.
     */
    int paddleWidth();
    /**
     * the level name will be displayed at the top of the screen.
     * @return name of level.
     */
    String levelName();
    /**
     * Returns a sprite with the background of the level.
     * @return background.
     */
    Sprite getBackground();
    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return list of the blocks.
     */
    List<Block> blocks();
    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * <p>This number should be <= blocks.size();</p>
     * @return numberOfBlocksToRemove.
     */
    int numberOfBlocksToRemove();
}

