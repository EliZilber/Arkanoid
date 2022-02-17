package levels;

import GUI.geometry.Point;
import GUI.backgrounds.Bckg1;
import other.Velocity;
import GUI.sprites.Block;
import GUI.sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 1 info.
 */
public class Level1 implements LevelInformation {
    /**
     * constructor.
     */
    public Level1() {
    }
    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();
        list.add(new Velocity(0, -5));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 33;
    }

    @Override
    public int paddleWidth() {
        return 200;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new Bckg1();
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<Block>();
        Block b = new Block(new Point(380, 150),  40, 40);
        b.setColor(Color.red);
        list.add(b);
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}
