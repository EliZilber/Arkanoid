package GUI.sprites;

import biuoop.DrawSurface;
import GUI.defines.MyDefines;
import levels.LevelInformation;
import other.Counter;
import other.GameLevel;

/**
 * class to show scores and more info as text on gui.
 */
public class InfoIndicator implements Sprite {
    //fields
    private int guiWidth;
    private Counter score;
    private LevelInformation info;
    /**
     * constructor.
     * @param gameLevel get the game to know the dimensions of the gui.
     * @param info of level.
     * @param score counter.
     */
    public InfoIndicator(GameLevel gameLevel, LevelInformation info, Counter score) {
        this.guiWidth = MyDefines.GUI_WIDTH;
        this.score = score;
        this.info = info;
    }
    /**
     * draws the bckg rect, and the updated score text.
     * @param d draw-surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        //draw score
        int fontSize = 18;
        d.drawText((this.guiWidth / 2) - 30, 16, "Score: " + this.score.getValue(), fontSize);
        //draw rest of info
        d.drawText((this.guiWidth / 2) + 150, 16, "Level: " + this.info.levelName(), fontSize);
    }
    /**
     * empty.
     */
    @Override
    public void timePassed() { }
    /**
     * add this item to game.
     * @param gameLevel to add to.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
}
