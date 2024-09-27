/** The FryingPan class extends the cooking gadget class.
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

public class FryingPan extends CookingGadget {

    public FryingPan() {
        super();
        width = 90;
        height = 90;
        name = "Frying Pan";

        cookableIngredients.add("Egg");
        cookableIngredients.add("Tapa");
        cookableIngredients.add("Bacon");
        cookableIngredients.add("Longganisa");

        importSprites();
    }

    public void importSprites() {
        try {
            sprite = ImageIO.read(new File("sprites/fryingpan.png"));
        } catch (IOException ex) {
            System.out.println("IOException from FryingPan importSprites()");
        }
    }

    public void draw(Graphics2D g2d) {
        if (sprite != null) {
            g2d.drawImage(sprite,(int)x1 + 3,(int)y1 - 5,(int)width,(int)height,null);
        }
        if (ingredientCooking != null) {
            ingredientCooking.setX(x1 + 5);
            ingredientCooking.setY(y1 + 5);
            ingredientCooking.draw(g2d);
            super.draw(g2d);
        }
    }

    
}

