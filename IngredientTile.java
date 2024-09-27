/** The IngredientTile class allows the player to get ingredients.
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

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class IngredientTile extends CounterTile {
    private Ingredient storedIngredient;
    private String name;

    public IngredientTile(double x, double y, String n) {
        super(x, y);
        name = n;

        type = "Ingredient Tile";
        storedIngredient = new Ingredient(x1 + 2, y1 + 2, name);
        
        importSprites();
    }

    private void importSprites() {
        try {
            sprite = ImageIO.read(new File("sprites/ingredienttile.png"));
        } catch (IOException ex) {
            System.out.println("IOException from IngredientTile importSprites()");
        }
    }

    protected void drawStoredItem(Graphics2D g2d) {
        storedIngredient.draw(g2d);
    }

    @Override
    public Interactable getStoredItem() {
        Ingredient temp = new Ingredient(x1 + 2, y1 + 2, name);
        return temp;
    }

    @Override
    public boolean isStoring() {
        return true;
    }
}
