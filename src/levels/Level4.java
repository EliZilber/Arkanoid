package levels;

import GUI.defines.MyColors;
import GUI.geometry.Point;
import GUI.backgrounds.Bckg4;
import other.GameLevel;
import other.Velocity;
import GUI.sprites.Block;
import GUI.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;
/**
 * Level 4 info.
 */
public class Level4 implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();
        list.add(Velocity.fromAngleAndSpeed(10, 7));
        list.add(Velocity.fromAngleAndSpeed(-10, 7));
        list.add(Velocity.fromAngleAndSpeed(0, 7));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 33;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        return new Bckg4();
    }

    @Override
    public List<Block> blocks() {
        //local variables
        int border = GameLevel.BORDER_THICKNESS;
        double blockWidth = (800 - border * 2) / 15.0;
        List<Block> list = new ArrayList<Block>();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 15; j++) {
                //x,y for block.rect upperLeft
                double x = j * blockWidth + GameLevel.BORDER_THICKNESS;
                double y = 100 + i * 20;
                Block b = new Block(new Point(x, y), blockWidth, 20);
                b.setColor(MyColors.colorByNumber(i));
                list.add(b);
            }
        }
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 15 * 7;
    }
}
