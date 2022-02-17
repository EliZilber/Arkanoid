package levels;

import GUI.defines.MyColors;
import GUI.geometry.Point;
import GUI.backgrounds.Bckg2;
import other.GameLevel;
import other.Velocity;
import GUI.sprites.Block;
import GUI.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;
/**
 * Level 2 info.
 */
public class Level2 implements LevelInformation {
    private int guiWidth = 800;
    /**
     * constructor.
     */
    public Level2() {
    }
    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<Velocity>();
        for (int i = 0; i < 5; i++) {
            list.add(Velocity.fromAngleAndSpeed(11 + i * 10, 9.6));
            list.add(Velocity.fromAngleAndSpeed(-11 - i * 10, 9.6));
        }
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 33;
    }

    @Override
    public int paddleWidth() {
        return 600;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        return new Bckg2();
    }

    @Override
    public List<Block> blocks() {
        //local variables
        int border = GameLevel.BORDER_THICKNESS;
        double blockWidth = (this.guiWidth - border * 2) / 15.0;
        List<Block> list = new ArrayList<Block>();

        for (int i = 0; i < 7; i++) {
            int group = 2;
            int jump = 2 * i;
            if (i == 3) {
                group++;
            }
            if (i >= 4) {
                jump++;
            }
            for (int j = 0; j < group; j++) {
                //x,y for block.rect upperLeft
                double x = (jump + j) * blockWidth + GameLevel.BORDER_THICKNESS;
                double y = 250;
                Block b = new Block(new Point(x, y), blockWidth, 20);
                b.setColor(MyColors.colorByNumber(i));
                list.add(b);
            }
        }
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 15;
    }
}
