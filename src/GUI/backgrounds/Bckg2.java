package GUI.backgrounds;

import biuoop.DrawSurface;
import GUI.defines.MyColors;
import GUI.sprites.Sprite;

/**
 * background for level 2.
 */
public class Bckg2 implements Sprite {
    @Override
    public void drawOn(DrawSurface d) {
        //center point of the sun
        int x = 150;
        int y = 150;
        d.setColor(MyColors.YELLOW);
        for (int i = 1; i < 80; i++) {
            d.drawLine(x, y, i * 10, y + 100);
        }
        for (int i = 2; i >= 0; i--) {
            d.setColor(MyColors.colorByNumber(i % 2));
            d.fillCircle(x, y, 20 + i * 20);
        }
    }

    @Override
    public void timePassed() {

    }
}
