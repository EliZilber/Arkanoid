package GUI.backgrounds;

import biuoop.DrawSurface;
import GUI.sprites.Sprite;

import java.awt.Color;
/**
 * background for level 1.
 */
public class Bckg1 implements Sprite {
    @Override
    public void drawOn(DrawSurface d) {
        //center point of the single rect
        int x = 400;
        int y = 170;
        //bckg color
        d.setColor(Color.black);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.blue);
        for (int i = 1; i < 4; i++) {
            d.drawCircle(x, y, 50 + i * 30);
        }
        d.drawLine(x + 30, y, x + 200, y);
        d.drawLine(x - 30, y, x - 200, y);
        d.drawLine(x, y + 30, x, y + 200);
        d.drawLine(x, y - 30, x, y - 200);
    }

    @Override
    public void timePassed() {

    }
}
