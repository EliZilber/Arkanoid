package other;

import GUI.animation.Animation;
import GUI.animation.AnimationRunner;
import GUI.animation.screens.EndScreen;
import GUI.animation.screens.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import levels.LevelInformation;

import java.util.List;

/**
 * GameFlow class - runs the levels according to user input.
 */
public class GameFlow {
    private Counter score = new Counter(0); //declare here to keep score across different levels
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboard;
    private boolean gameOver = false; //let you know if user won or lose
    /**
     * Constructor.
     * @param ar GUI.animation runner.
     * @param key keyboard.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor key) {
        this.animationRunner = ar;
        this.keyboard = key;
    }
    /**
     * run levels in order of the given list.
     * @param levels to run.
     */
    public void runLevels(List<LevelInformation> levels) {
        // ...
        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.animationRunner, keyboard, this.score);

            level.initialize();

            while (!level.shouldStop()) {
                level.run();
            }

            if (level.getRemainingBalls().getValue() == 0) {
                this.gameOver = true;
                break;
            }

        }
        endScreen(this.gameOver);
    }

    /**
     * end screen actions here.
     * @param gameO game over or win boolean.
     */
    private void endScreen(Boolean gameO) {
        Animation es = new EndScreen(this.keyboard, gameO, this.score);
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, es));

        animationRunner.getGui().close(); //close gui after game have finished
    }
}
