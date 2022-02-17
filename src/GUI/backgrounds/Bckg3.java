package GUI.backgrounds;

import biuoop.DrawSurface;
import GUI.defines.MyColors;
import GUI.sprites.Sprite;

/**
 * background for level 3.
 */
public class Bckg3 implements Sprite {
    @Override
    public void drawOn(DrawSurface d) {
        int x1 = 100;
        int y1 = 300;
        int y2 = 380;
        //bckg color
        d.setColor(MyColors.ORANGE.brighter());
        d.fillRectangle(0, 0, 800, 600);
        for (int i = 1; i < 4; i++) {
            if (i == 2) {
                x1 = 300;
                y1 = 100;
                y2 = 180;
            }
            if (i == 3) {
                x1 = 640;
                y1 = 200;
                y2 = 280;
            }
            //butterfly body
            d.setColor(MyColors.PURPLE);
            d.fillCircle(x1, y1, 70);
            d.fillCircle(x1 + 100, y1, 70);
            d.fillCircle(x1, y2, 60);
            d.fillCircle(x1 + 100, y2, 60);
            //inner circles
            d.setColor(MyColors.colorByNumber(i));
            d.fillCircle(x1, y1, 40);
            d.fillCircle(x1 + 100, y1, 40);
            d.setColor(MyColors.YELLOW);
            d.fillCircle(x1, y2, 20);
            d.fillCircle(x1 + 100, y2, 20);
        }

        y1 = 100;
        y2 = 180;
    }

    @Override
    public void timePassed() {

    }
}
