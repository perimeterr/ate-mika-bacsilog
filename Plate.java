/** The Plate class is an Interactable class that can store food and what is being served by the player.
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

public class Plate extends Interactable {
    private int numIngredients;
    private int maxNumIngredients;
    private Ingredient[] ingredients;

    public Plate() {
        width = 65;
        height = 65;

        numIngredients = 0;
        maxNumIngredients = 3;

        ingredients = new Ingredient[maxNumIngredients];
        type = "Plate";

        importSprites();
    }

    public void importSprites() {
        try {
            sprite = ImageIO.read(new File("sprites/plate.png"));
        } catch (IOException ex) {
            System.out.println("IOException from Plate importSprites()");
        }
    }

    public void draw(Graphics2D g2d) {
        if (sprite != null) g2d.drawImage(sprite,(int)x1 + 10,(int)y1 + 10,(int)width,(int)height,null);
        drawFood(g2d);
    }

    protected void drawFood(Graphics2D g2d) {
        for (Ingredient i : ingredients) {
            if (i != null) {
                switch (i.getName()) {
                    case "Egg":
                        i.setX(x1 + 8);
                        i.setY(y1 + 8);
                        break;
                    case "Rice":
                        i.setX(x1 + 12);
                        i.setY(y1 + 18);
                        break;
                    case "Tapa":
                    case "Bacon":
                    case "Longganisa":
                        i.setX(x1 + 20);
                        i.setY(y1 + 5);
                        break;
                }
                i.draw(g2d);
            }
        }
    }
    
    public void addFood(Ingredient i) {
        int type = checkIngredientType(i);
        if (type != 3) {
            if (!isFull() && i.isCooked()) {
                i.scaleDown(15);
                ingredients[type] = i;
                numIngredients++;
            }
        }
    }

    public void removeFood() {
        for (int x = 0; x < maxNumIngredients; x++) {
            ingredients[x] = null;
        }
        numIngredients = 0;
    }

    public int checkIngredientType(Ingredient i) {
        if (i != null) {
            int type = 0;
            switch (i.getName()) {
                case "Rice":
                    type = 0;
                    break;
                case "Egg":
                    type = 1;
                    break;
                case "Tapa":
                case "Bacon":
                case "Longganisa":
                    type = 2;
                    break;
            }
            if (ingredients[type] == null) return type;
        }
        return 3;
    }

    public boolean isIngredientAddable(Ingredient i) {
        if (i != null) {
            int type = checkIngredientType(i);
            if (i.isCooked()) {
                if (type != 3) {
                    if (ingredients[type] == null)
                    return true;
                }
            }
        }
        return false;
    }

    public Ingredient[] getFood() {
        return ingredients;
    }

    public boolean hasFood() {
        return (numIngredients > 0);
    }

    public boolean isFull() {
        return (numIngredients == maxNumIngredients);
    }

    @Override
    public void moveRight(double x) {
        x1 += x;
        if (hasFood())
        ingredients[numIngredients-1].moveRight(x);
    }

    @Override
    public void moveLeft(double x) {
        x1 -= x;
        if (hasFood())
        ingredients[numIngredients-1].moveLeft(x);
    }

    @Override
    public void moveUp(double y) {
        y1 -= y;
        if (hasFood())
        ingredients[numIngredients-1].moveUp(y);
        
    }

    @Override
    public void moveDown(double y) {
        y1 += y;
        if (hasFood())
        ingredients[numIngredients-1].moveDown(y);
    }

    public int getNumIngredients() {
        return numIngredients;
    }

    public int getMaxNumIngredients() {
        return maxNumIngredients;
    }

    public String getName() {
        if (isFull()) {
            name = ingredients[2].getName();
        }
        return name;
    }
    
}
