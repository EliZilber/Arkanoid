package levels;

import GUI.defines.MyColors;
import GUI.geometry.Point;

import GUI.backgrounds.Bckg3;
import other.GameLevel;
import other.Velocity;
import GUI.sprites.Block;
import GUI.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;
/**
 * Level 3 info.
 */
public class Level3 implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();
            list.add(Velocity.fromAngleAndSpeed(10, 9));
            list.add(Velocity.fromAngleAndSpeed(-10, 9));
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
        return "Butterflies 3";
    }

    @Override
    public Sprite getBackground() {
        return new Bckg3();
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new ArrayList<Block>();

        int guiWidth = 800;
        int blocksNum = 10; //biggest line length
        int bWidth = 50;
        int bHeight = 20; //blocks dimensions

        //create rest of blocks
        for (int i = 0; i < blocksNum - 5; i++) {
            for (int j = 0; j < blocksNum - i; j++) {
                //x,y for block.rect upperLeft
                double x = guiWidth - GameLevel.BORDER_THICKNESS - ((j + 1) * bWidth);
                double y = 100 + i * bHeight;
                Block b = new Block(new Point(x, y), bWidth, bHeight);
                b.setColor(MyColors.colorByNumber(i));
                list.add(b);
            }
        }
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 40;
    }
}
