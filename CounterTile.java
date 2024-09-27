/** The CounterTile class is the main parent class for the other classes which are being interacted by the players.
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
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class CounterTile {
    protected double x1, y1, width, height;
    protected boolean inPlayerRange;
    protected String type;
    protected int numStoredItems, maxNumStoredItems;
    protected Interactable[] storedItems;
    protected ArrayList<String> storableItems;
    protected BufferedImage sprite;

    /** Constructor for initializing the tile
        @param x is the tile's x-coordinate
        @param y is the tile's y-coordinate
    **/
    public CounterTile(double x, double y) {
        x1 = x;
        y1 = y;
        width = 90;
        height = 90;
        numStoredItems = 0;
        maxNumStoredItems = 1;
        storedItems = new Interactable[maxNumStoredItems];
        storableItems = new ArrayList<String>();
        inPlayerRange = false;
        type = "Counter Tile";

        storableItems.add("Plate");
        storableItems.add("Ingredient");
        storableItems.add("Cooking Gadget");

        importSprites();
    }

    private void importSprites() {
        try {
            sprite = ImageIO.read(new File("sprites/tile.png"));
        } catch (IOException ex) {
            System.out.println("IOException from CounterTile importSprites()");
        }
    }

    public void draw (Graphics2D g2d) {
        if (sprite != null)
        g2d.drawImage(sprite,(int)x1,(int)y1,(int)width,(int)height,null);
        if (inPlayerRange) {
            g2d.setColor(new Color(255,255,255,150));
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Rectangle2D.Double(x1 + 10,y1 + 10,width - 25,height - 20));
        }
        drawStoredItem(g2d);
    }

    protected void drawStoredItem(Graphics2D g2d) {
        if (isStoring()) storedItems[numStoredItems - 1].draw(g2d);
    }

    public boolean isFull() {
        return (numStoredItems == maxNumStoredItems);
    }

    public boolean isStoring() {
       return (numStoredItems > 0);
    }

    public double getX() {
        return x1;
    }

    public double getY() {
        return y1;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getType() {
        return type;
    }

    public Interactable getStoredItem() {
        return storedItems[numStoredItems - 1];
    }

    public ArrayList<String> getStorableItems() {
        return storableItems;
    }

    public void removeItem() {
        if (isStoring()) {
            storedItems[numStoredItems - 1] = null;
            numStoredItems--;
        }
    }

    public void replaceItem(Interactable i) {
        storedItems[numStoredItems - 1] = i;
    }

    public void storeItem(Interactable i) {
        if (!isFull()) {
            for (int x = 0; x < maxNumStoredItems; x++) {
                if (storedItems[x] == null) {
                    storedItems[x] = i;
                    storedItems[x].setX(x1 + 2);
                    storedItems[x].setY(y1 + 2);
                    numStoredItems++;
                    break;
                }
            }
        }

    }

    public void checkInPlayerRange(Player p) {
        if (p.getPlayerID() == 1) {
            if (y1 == 130) {
                inPlayerRange = (x1 - 10 <= p.getX() && p.getX() <= x1 + width - 30 && p.getY() - (y1 + height) <= -90);
            } else if (x1 == 592) {
                inPlayerRange = (x1 - p.getX() - p.getWidth() <= -20 && y1 - height <= p.getY() && p.getY() <= y1 - 10);
            } else if (y1 == 563) {
                inPlayerRange = (x1 - 10<= p.getX() && p.getX() <= x1 + width - 30 && p.getY() + p.getHeight() - y1 >= 20);
            } else if (x1 == 186) {
                inPlayerRange = (x1 + 90 - p.getX() >= 20 && y1 - height <= p.getY() && p.getY() <= y1 - 10) ;
            } else {
                inPlayerRange = false;
            }
        } else {
            if (y1 == 130) {
                inPlayerRange = (x1 - 10 <= p.getX() && p.getX() <= x1 + width - 30 && p.getY() - (y1 + height) <= -90);
            } else if (x1 == 1003) {
                inPlayerRange = (p.getX() + p.getWidth() - x1 >= 20 && y1 - height <= p.getY() && p.getY() <= y1 - 10);
            } else if (y1 == 563) {
                inPlayerRange = (x1 - 10<= p.getX() && p.getX() <= x1 + width - 30 && p.getY() + p.getHeight() - y1 >= 20);
            } else if (x1 == 592) {
                inPlayerRange = (x1 + 90 - p.getX() >= 20 && y1 - height <= p.getY() && p.getY() <= y1 - 10) ;
            } else {
                inPlayerRange = false;
            }
        }
    }

}
