import GUI.animation.AnimationRunner;
import biuoop.GUI;
import GUI.defines.MyDefines;
import levels.Level1;
import levels.Level2;
import levels.Level3;
import levels.Level4;
import levels.LevelInformation;
import other.GameFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Ass6Game - running the game.
 *
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class Ass6Game {
    private static List<LevelInformation> levels = new ArrayList<LevelInformation>();
    /**
     * main -- creates the game and run it.
     * <p></p>
     * @param args parsed to integers value that creates a point, dx, dy for ner drawAnimation.
     */
    public static void main(String[] args) {
//        for (String s: args) {
//            try {
//                addToList(Integer.parseInt(s));
//            } catch (Exception e) {
//               continue;
//            }
//        }
        if (levels.isEmpty()) {
            levels = List.of(new Level1(), new Level2(), new Level3(), new Level4());
        }
        GUI gui = new GUI("Arkanoid", MyDefines.GUI_WIDTH, MyDefines.GUI_HEIGHT);
        AnimationRunner ar = new AnimationRunner(gui);
        GameFlow gameFlow = new GameFlow(ar, gui.getKeyboardSensor());
        gameFlow.runLevels(levels);
    }

    /**
     * add a level to the game according to its number.
     * @param num - number of the level.
     */
    private static void addToList(int num) {
        switch (num) {
            case 1:
                levels.add(new Level2()); break;
            case 2:
                levels.add(new Level1()); break;
            case 3:
                levels.add(new Level3()); break;
            case 4:
                levels.add(new Level4()); break;
            default:
                levels.add(new Level2()); break;
        }
    }
}
