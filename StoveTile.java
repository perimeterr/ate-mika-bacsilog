/** The StoveTile class is where the cooking gadgets are placed for them to cook.
    This is a template for a Java file.
    @author Mikaela C. Paderna (234696)
    @version May 14, 2024
**/
/*
    I have not discussed the Java language code in my program 
    with anyone other than my instructor or the teaching assistants 
    assigned to this course.
    I have not used Java language code obtained from another student, 
    or any other unauthorized source, either modified or unmodified.
    If any Java language code or documentation used in my program 
    was obtained from another source, such as a textbook or website, 
    that has been clearly noted with a proper citation in the comments 
    of my program.
*/
import java.io.*;
import javax.imageio.ImageIO;

public class StoveTile extends CounterTile {
    public StoveTile(double x, double y) {
        super(x,y);

        type = "Stove Tile";
        storableItems.remove("Plate");
        storableItems.remove("Ingredient");

        importSprites();
    }

    private void importSprites() {
        try {
            sprite = ImageIO.read(new File("sprites/stovetile.png"));
        } catch (IOException ex) {
            System.out.println("IOException from StoveTile importSprites()");
        }
    }
}

