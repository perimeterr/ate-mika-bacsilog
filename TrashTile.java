/** The TrashTile allows players to void ingredients.
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
import javax.imageio.ImageIO;

public class TrashTile extends CounterTile {

    public TrashTile(double x, double y) {
        super(x, y);

        type = "Trash Tile";
        storableItems.remove("Plate");
        storableItems.remove("Cooking Gadget");
        importSprites();
    }

    private void importSprites() {
        try {
            sprite = ImageIO.read(new File("sprites/trashtile.png"));
        } catch (IOException ex) {
            System.out.println("IOException from TrashTile importSprites()");
        }
    }

    @Override
    public void storeItem(Interactable i) {
        if (!isFull()) {
            for (String s : storableItems) {
                if (i.getType() == s) {
                    for (int x = 0; x < maxNumStoredItems; x++) {
                        if (storedItems[x] == null) {
                            storedItems[x] = i;
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isStoring() {
        return true;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        if (sprite != null)
        g2d.drawImage(sprite,(int)x1,(int)y1,(int)width,(int)height,null);
        if (inPlayerRange) {
            g2d.setColor(new Color(255,255,255,150));
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Rectangle2D.Double(x1 + 10,y1 + 10,width - 25,height - 20));
        }
    }
}
