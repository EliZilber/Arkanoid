package collections;

import biuoop.DrawSurface;
import GUI.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * collections.SpriteCollection - holds a collection of GUI.sprites.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class SpriteCollection {
    //fields
    private List<Sprite> sprites = new ArrayList<>();

    /**
     * add a sprite to the collection.
     * @param s sprite to add.
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }
    /**
     * remove the sprite from the GUI.sprites list.
     * @param s .
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
    /**
     * call timePassed() on all GUI.sprites.
     */
    public void notifyAllTimePassed() {
        // Make a copy of the GUI.sprites before iterating over them.
        List<Sprite> copy = new ArrayList<Sprite>(this.sprites);
        for (Sprite s:copy) {
            s.timePassed();
        }
    }
    /**
     * call drawOn(d) on all GUI.sprites.
     * @param d the surface to draw on it all GUI.sprites.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s:sprites) {
            s.drawOn(d);
        }
    }
}