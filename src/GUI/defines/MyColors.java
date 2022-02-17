package GUI.defines;

import java.awt.Color;

/**
 * GUI.defines.MyColors - here i store GUI.defines of my colors, to let me choose identical colors easily and fast,
 * and to let me change colors easily inside a single class.
 * colors pallete source: https://coolors.co/ffbe0b-fb5607-ff006e-8338ec-3a86ff
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class MyColors {
    public static final java.awt.Color YELLOW = new Color(255, 190, 11);
    public static final java.awt.Color ORANGE = new Color(251, 86, 7);
    public static final java.awt.Color PINK = new Color(255, 0, 110);
    public static final java.awt.Color PURPLE = new Color(131, 56, 236);
    public static final java.awt.Color BLUE = new Color(58, 134, 255);

    /**
     * let you get a color fast in a for loop, by number.
     * @param num .
     * @return color matches to index.
     */
    public static java.awt.Color colorByNumber(int num) {
        switch (num) {
            case 1:
                return ORANGE;
            case 2:
                return PINK;
            case 3:
                return PURPLE;
            case 4:
                return BLUE;
                //from here there are colors not in the original fan. this for convenience when need more colors.
            case 5:
                return Color.GREEN;
            case 6:
                return Color.cyan;
            default:
                return YELLOW;
        }
    }
}
