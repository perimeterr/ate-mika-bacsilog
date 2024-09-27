/** The CookingGadget class is where the player cooks ingredients.
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
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

public class CookingGadget extends Interactable {
    protected Ingredient ingredientCooking;
    protected boolean cooking;
    protected Ellipse2D.Double progress;
    protected ArrayList<String> cookableIngredients;
    private Timer cookingTimer;
    
    // Constructor for creating the cooking gadget 
    public CookingGadget() {
        ingredientCooking = null;
        cooking = false;

        cookableIngredients = new ArrayList<String>();
        
        type = "Cooking Gadget";
    }

    // Draws the object, also takes into consideration its current state
    public void draw(Graphics2D g2d) {
        progress = new Ellipse2D.Double(x1 - 5, y1 - 20, 20, 20);
        if (isFull()) {
            if (assignedTile != null && assignedTile.getType() == "Stove Tile") {
                if (!ingredientCooking.isCooked()) {
                    g2d.setColor(Color.ORANGE);
                    g2d.fill(progress);
                } else {
                    g2d.setColor(Color.GREEN);
                    g2d.fill(progress);
                }
            } else {
                if (!ingredientCooking.isCooked()) {
                    g2d.setColor(Color.GRAY);
                    g2d.fill(progress);
                } else {
                    g2d.setColor(Color.GREEN);
                    g2d.fill(progress);
                }
            }
            
        }
    }

    // Cooks the ingredient
    public void cook() {
        int delay = 5000;
        if (cooking) {
            cookingTimer.cancel();
            cooking = false;
        } else {
            cookingTimer = new Timer();
            TimerTask cookIngredient = new TimerTask() {
                @Override
                public void run() {
                    if (ingredientCooking != null);
                    ingredientCooking.setCooked();
                }
            };
            cookingTimer.schedule(cookIngredient, delay);
            cooking = true;
        }
    }    

    // Stops cooking the ingredient
    public void stopCooking() {
        cooking = false;
        cookingTimer.cancel();
    }

    // Places food insdie the cooking gadget
    public void addFood(Ingredient i) {
        if (isIngredientAddable(i)) {
            ingredientCooking = i;
            ingredientCooking.scaleDown(10);
        }
    }

    /** Checks if given ingredient can be added to the cooking gadget
        @return if the ingredient is addable
    **/
    public boolean isIngredientAddable(Ingredient i) {
        if (!isFull()) {
            for (String s : cookableIngredients) {
                if (s == i.getName()) {
                    if (s == "Tapa" || s == "Bacon" || s == "Longganisa") {
                        return (i.isSliced());
                    } 
                    return true;
                }
            }
        }
        return false;
    }

    // Removes food from the cooking gadget
    public void removeFood() {
        ingredientCooking = null;
    }

    /** Gets the food when it is cooked
        @return is cooked ingredient
    **/
    public Ingredient getFood() {
        if (ingredientCooking.isCooked()) return ingredientCooking;
        else return null;
    }

    /** Gets the food when it is cooking
        @param is the ingredient cooking
    **/
    public Ingredient getCookingIngredient() {
        if (isFull()) return ingredientCooking; else return null;
    }

    /** Checks if the cooking gadget has food
        @param 
    **/
    public boolean isFull() {
        return (ingredientCooking != null);
    }
    
    // Moves to the right
    @Override
    public void moveRight(double x) {
        x1 += x;
        if (isFull())
        ingredientCooking.moveRight(x);
    }

    // Moves to the left
    @Override
    public void moveLeft(double x) {
        x1 -= x;
        if (isFull())
        ingredientCooking.moveLeft(x);
    }

    // Moves up
    @Override
    public void moveUp(double y) {
        y1 -= y;
        if (isFull())
        ingredientCooking.moveUp(y);
        
    }

    // Moves down
    @Override
    public void moveDown(double y) {
        y1 += y;
        if (isFull())
        ingredientCooking.moveDown(y);
    }

    /** Checks if given ingredient can be added to the cooking gadget
        @return if ingredient is cooked true, false if not
    **/
    public boolean isIngredientCooked() {
        if (isFull()) {
            if (ingredientCooking.isCooked()) return true;
        }
        return false;
    }

    /** Checks if currently cooking
        @return if ingredient is being cooked true, false if not
    **/
    public boolean isCooking() {
        return cooking;
    }

}
