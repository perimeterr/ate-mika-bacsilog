/** The ChoppingBoardTile class is where the player slices the ingredients.
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
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class ChoppingBoardTile extends CounterTile {
    private boolean slicing;
    private Ellipse2D.Double progress;
    private Ingredient ingredientSlicing;
    private Timer slicingTimer;

    /** Constructor for initializing the tile
        @param x is the tile's x-coordinate
        @param y is the tile's y-coordinate
    **/
    public ChoppingBoardTile(double x, double y) {
        super(x, y);

        type = "Chopping Board Tile";
        storableItems.remove("Cooking Gadget");
        storableItems.remove("Plate");
        slicing = false;
        importSprites();
    }

    // Imports the required images 
    private void importSprites() {
        try {
            sprite = ImageIO.read(new File("sprites/choppingboard.png"));
        } catch (IOException ex) {
            System.out.println("IOException from ChoppingBoardTile importSprites()");
        }
    }

    // Draws the current state of the slicing
    public void draw(Graphics2D g2d) {
        progress = new Ellipse2D.Double(x1 - 5, y1 - 20, 20, 20);
        if (isStoring()) {
            if (ingredientSlicing.isSliced()) {
                g2d.setColor(Color.GREEN);
                g2d.fill(progress);
            } else if (!slicing) {
                g2d.setColor(Color.GRAY);
                g2d.fill(progress);
            } else if (slicing) {
                g2d.setColor(Color.ORANGE);
                g2d.fill(progress);
            }
        }
        super.draw(g2d);
    }

    // Draws the ingredient being sliced
    @Override
    protected void drawStoredItem(Graphics2D g2d) {
        if (isStoring()) ingredientSlicing.draw(g2d);
    }

    /**
        Checks if ingredient can be sliced
        @param i is the ingredient
    **/
    public boolean isIngredientAddable(Ingredient i) {
        if (!isStoring()) {
            return (i.getName() == "Tapa" ||
            i.getName() == "Bacon" ||
            i.getName() == "Longganisa");
        }
        return false;
    }

    /**
        Assigns the ingredient to be sliced to the tile
        @param i is the ingredient to be sliced
    **/
    @Override
    public void storeItem(Interactable i) {
        if (isIngredientAddable((Ingredient)i)) {
            ingredientSlicing = (Ingredient) i;
            ingredientSlicing.scaleDown(15);
            ingredientSlicing.setX(x1 + 6);
            ingredientSlicing.setY(y1 + 7);
        }
    }

    // Removes ingredient from the tile
    @Override
    public void removeItem() {
        ingredientSlicing = null;
        stopSlicing();
    }

    // Draws the white outline but also stops and starts the slicing timer based on the player's distance
    @Override
    public void checkInPlayerRange(Player p) {
        super.checkInPlayerRange(p);

        if (!inPlayerRange) {
            if (slicing)
            stopSlicing();
        } else {
            if (!slicing)
            slice();
        } 
   
    }

    /**
        Gets the ingredient being sliced
        @return ingredient
    **/
    @Override
    public Ingredient getStoredItem() {
        if (isStoring()) return ingredientSlicing; else return null;
    }

    /**
        Checks if an ingredient is being sliced
        @return true if ingredient is being sliced, false if not
    **/
    @Override
    public boolean isStoring() {
        return (ingredientSlicing != null);
    }

    // Method for creating a timer when slicing the ingredient
    public void slice() {
        int delay = 3000;
        if (slicing) {
            slicingTimer.cancel();
            slicing = false;
        } else {
            slicingTimer = new Timer();
            TimerTask sliceIngredient = new TimerTask() {
                @Override
                public void run() {
                    if (ingredientSlicing != null)
                    ingredientSlicing.setSliced();
                }
            };
            slicingTimer.schedule(sliceIngredient, delay);
            slicing = true;
        }
    }    

    // Method for stopping the slicing
    public void stopSlicing() {
        slicing = false;
        slicingTimer.cancel();
    }

}