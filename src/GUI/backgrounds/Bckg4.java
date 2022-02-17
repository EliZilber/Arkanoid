package GUI.backgrounds;

import biuoop.DrawSurface;
import GUI.defines.MyColors;
import GUI.sprites.Sprite;

import java.awt.Color;


/**
 * background for level 4.
 */
public class Bckg4 implements Sprite {
    @Override
    public void drawOn(DrawSurface d) {
        //bckg color
        d.setColor(MyColors.BLUE);
        d.fillRectangle(0, 0, 800, 600);
        int x = -100;
        int y = 400;
        for (int i = 1; i < 4; i++) {
            x += 250;
            d.setColor(Color.gray);
            d.fillCircle(x, y, 40);
            d.setColor(Color.darkGray);
            d.fillCircle(x + 30, y, 40);
            d.setColor(Color.lightGray);
            d.fillCircle(x + 60, y, 40);
            d.fillCircle(x, y + 30, 40);
            d.setColor(Color.gray);
            d.fillCircle(x + 40, y + 30, 40);
        }

    }

    @Override
    public void timePassed() {

    }
}
