package GUI.animation.screens;

import GUI.animation.Animation;
import biuoop.DrawSurface;
import collections.SpriteCollection;
import GUI.defines.MyDefines;

import java.awt.Color;

/**
 *  The CountdownAnimation will display the given gameScreen,
 *  for numOfSeconds seconds, and on top of them it will show
 *  a countdown from countFrom back to 1, where each number will
 *  appear on the screen for (numOfSeconds / countFrom) seconds, before
 *  it is replaced with the next one.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class CountdownAnimation implements Animation {
    private final double timeSpan; //time span between to numbers (numOfSeconds / countFrom)
    private final SpriteCollection gameScreen;
    private Integer currentCount;
    private int framesCounter = 0;
    /**
     * constructor.
     * @param numOfSeconds to run the count down.
     * @param countFrom the number to start from.
     * @param gameScreen need to add the count down to the sprite collection cause
     *                   that screen is on top of game.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.timeSpan = numOfSeconds / countFrom;
        this.currentCount = countFrom; //on beginning current count is equals to countFrom.
        this.gameScreen = gameScreen;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        //print all game GUI.sprites, and background
        gameScreen.drawAllOn(d);
        d.setColor(Color.white);
        d.drawText(d.getWidth() / 2, d.getHeight() / 2, this.currentCount.toString(), 32);
        framesCounter += 1;
        if (framesCounter >= MyDefines.FPS * timeSpan) { //if time span is passed.
            currentCount -= 1;
            framesCounter = 0;
        }
    }
    @Override
    public boolean shouldStop() {
        return currentCount <= 0;
    }
}
