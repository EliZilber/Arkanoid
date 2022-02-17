package other;

import collisions.listeners.ScoreTrackingListener;
import GUI.animation.Animation;
import GUI.animation.AnimationRunner;
import GUI.animation.screens.CountdownAnimation;
import GUI.animation.screens.KeyPressStoppableAnimation;
import GUI.animation.screens.PauseScreen;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collections.GameEnvironment;
import collections.SpriteCollection;
import collisions.BallRemover;
import collisions.BlockRemover;
import collisions.Collidable;
import GUI.defines.MyColors;
import GUI.defines.MyDefines;
import GUI.geometry.Point;
import GUI.geometry.Rectangle;
import levels.LevelInformation;
import GUI.sprites.Ball;
import GUI.sprites.Block;
import GUI.sprites.Paddle;
import GUI.sprites.Sprite;
import GUI.sprites.InfoIndicator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * other.Game - Class that creating a new window with a game.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class GameLevel implements Animation {
    //fields
    private final SpriteCollection sprites = new SpriteCollection();
    private final GameEnvironment environment = new GameEnvironment();
    public static final Integer BORDER_THICKNESS = 20;
    private final Counter remainingBlocks = new Counter(0);
    private final Counter remainingBalls = new Counter(0);
    private Counter score;
    private final biuoop.KeyboardSensor keyboard;
    private AnimationRunner runner;
    private boolean running = false;
    private LevelInformation levelInfo;

    /**
     * constructor.
     * @param levelInfo info of this level.
     * @param ar runner.
     * @param key keyboard.
     * @param score conter.
     */
    public GameLevel(LevelInformation levelInfo, AnimationRunner ar,
                     KeyboardSensor key, Counter score) {
        this.runner = ar;
        this.keyboard = key;
        this.levelInfo = levelInfo;
        this.score = score;
    }
    /**
     * getter.
     * @return GUI.sprites collection
     */
    public SpriteCollection getSprites() {
        return this.sprites;
    }
    /**
     * getter.
     * @return game environment
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }
    /**
     * getter.
     * @return blocksNum - counter of how many blocks there are currently in Game.
     */
    public Counter getRemainingBlocks() {
        return this.remainingBlocks;
    }
    /**
     * getter.
     * @return remainingballs.
     */
    public Counter getRemainingBalls() {
        return this.remainingBalls;
    }
    /**
     * let us add a sprite to the other.Game class.
     * @param s the sprite to add.
     * */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }
    /**
     * remove the given collidable from the environment.
     * @param c .
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }
    /**
     * remove the sprite from the GUI.sprites list.
     * @param s .
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }
    /**
     *  initializes all the balls required for game on game beginning.
     */
    private void initBalls() {
        Point startP1 = new Point(MyDefines.GUI_WIDTH / 2, 540.2); //not whole point is better.
        List<Velocity> vList = new ArrayList<Velocity>();
        vList = levelInfo.initialBallVelocities();
        if (vList != null) {
            for (Velocity v : vList) {
                Ball ball = new Ball((int) startP1.getX(), (int) startP1.getY(), 6, Color.darkGray);
                ball.setGameEnvironment(this.environment); //give the ball a gameEnvironment
                ball.setVelocity(v);
                ball.addToGame(this);
                remainingBalls.increase(1);
            }
        }
    }

    /**
     * creates the 4 Blocks on the frame. add them to this game environment.
     * @param ballRemover .
     */
    private void createBorders(BallRemover ballRemover) {
        Block[] arr = new Block[4];
        arr[0] = new Block(new Point(0, 0), this.BORDER_THICKNESS, MyDefines.GUI_HEIGHT); //left
        arr[1] = new Block(new Point(MyDefines.GUI_WIDTH - this.BORDER_THICKNESS, 0),
                this.BORDER_THICKNESS, MyDefines.GUI_HEIGHT); //right
        arr[2] = new Block(new Point(0, 20), MyDefines.GUI_WIDTH, this.BORDER_THICKNESS); //up
        arr[3] = new Block(new Point(0, MyDefines.GUI_HEIGHT + this.BORDER_THICKNESS),
                MyDefines.GUI_WIDTH, this.BORDER_THICKNESS); //death zone- down
        //add all the borders to this gameEnvironment, and to GUI.sprites collection.
        for (Block block:arr) {
            block.setColor(Color.orange);
            block.addToGame(this);
        }
        //death zone specifications
        arr[3].addHitListener(ballRemover);
    }
    /**
     *  initializes all the blocks required for game on game beginning.
     *  <p>creates the frame blocks in assistance method createBorders()
     *  that also add them to this.environment</p>
     */
    private void initBlocks() {
        //collisions.listeners
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        BallRemover ballRemover = new BallRemover(this, remainingBalls);
        ScoreTrackingListener sTL = new ScoreTrackingListener(score);

        createBorders(ballRemover);
        List<Block> bList = new ArrayList<Block>();
        bList = levelInfo.blocks();
        if (bList != null) {
            for (Block b : bList) {
                b.addToGame(this);
                remainingBlocks.increase(1);
                //add collisions.listeners
                b.addHitListener(blockRemover);
                b.addHitListener(sTL);
            }
        }
        }

    /**
     * initScoreIndicator. block on top of screen displaying game info.
     */
    private void initScoreIndicator() {
        //background. notice: - i created a block even tough this isn't a collider
        Block bckg = new Block(new Point(0, 0), MyDefines.GUI_WIDTH, 20);
        bckg.setColor(MyColors.PURPLE);
        bckg.addToGame(this);
        //text
        InfoIndicator scoreIndicator = new InfoIndicator(this, this.levelInfo, score);
        scoreIndicator.addToGame(this);
    }
    /**
     * init paddle.
     */
    private void initPaddle() {
        int pHeight = 50; //notice: paddle height must be > ballR+BorderThickness+v.dy
        Point pStart = new Point(MyDefines.GUI_WIDTH / 2 - this.levelInfo.paddleWidth() / 2,
                MyDefines.GUI_HEIGHT - pHeight - 10);
        Rectangle pRect = new Rectangle(pStart, this.levelInfo.paddleWidth(), pHeight);
        Paddle p = new Paddle(pRect, this.levelInfo.paddleSpeed(), this.keyboard);
        p.addToGame(this);
    }
    /**
     * Initialize a new game: create the Blocks and GUI.sprites.Ball (and GUI.sprites.Paddle) and add them to the game.
     */
    public void initialize() {
        this.sprites.addSprite(levelInfo.getBackground()); //init background
        initBalls();
        initBlocks();
        initPaddle();
        initScoreIndicator();
    }
    /**
     * run -- handle the game GUI.animation (timing,drawing) for all GUI.sprites in GUI.sprites collection.
     */
    public void run() {
        this.runner.run(new CountdownAnimation(2, 3, this.sprites)); // countdown before turn starts.
        this.running = true;
        // use our runner to run the current GUI.animation -- which is one turn of the game.
        this.runner.run(this);
        finishLevel();
    }

    /**
     * put here a block of code to call when level should end.
     */
    private void finishLevel() {
        //add score if user removed all blocks
        if (this.remainingBlocks.getValue() <= 0) {
            this.score.increase(100);
        }
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (this.keyboard.isPressed("p")) {
            Animation ps = new PauseScreen(this.keyboard);
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, ps));
        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
    }

    @Override
    public boolean shouldStop() {
        //create a list of conditions.
        List<Boolean> conditions = new ArrayList<>();
        conditions.add(this.remainingBalls.getValue() == 0);
        int blocksLimit = this.levelInfo.blocks().size() - this.levelInfo.numberOfBlocksToRemove();
        conditions.add(this.remainingBlocks.getValue() == blocksLimit);
        //iterate the conditions to find if any of the stop condition is taking place.
        for (Boolean condition: conditions) {
            if (condition) {
                this.running = false; //the game shouldn't run anymore.
                //this.runner.getGui().close();
                return true;
            }
        }
        //if the game should run return false.
        return false;
    }
}
