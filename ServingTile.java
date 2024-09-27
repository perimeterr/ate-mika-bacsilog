/** The ServingTile class is where the completed dishes are served.
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

public class ServingTile extends CounterTile {
    private int numServedDishes;
    private OrderMenu om;

    public ServingTile(double x, double y, OrderMenu om) {
        super(x,y);
        width = 460;
        numServedDishes = 0;
        this.om = om;

        type = "Serving Tile";
        storableItems.remove("Ingredient");
        storableItems.remove("Cooking Gadget");
        importSprites();
    }

    @Override
    public void draw (Graphics2D g2d) {
        if (sprite != null)
        g2d.drawImage(sprite,(int)x1,(int)y1,(int)width,(int)height,null);
        if (inPlayerRange) {
            g2d.setColor(new Color(255,255,255,150));
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Rectangle2D.Double(x1 + 10,y1 + 10,width - 25,height - 20));
        }
    }

    private void importSprites() {
        try {
            sprite = ImageIO.read(new File("sprites/servingtile.png"));
        } catch (IOException ex) {
            System.out.println("IOException from StoveTile importSprites()");
        }
    }

    public boolean isServable(Plate p) {
        Ingredient[] food = p.getFood();
        if (p.isFull()) {
            for (Order o : om.getOrderList()) {
                if (o.getName() == food[2].getName()) {
                    om.completeOrder(o.getName());
                    return true;
                }
            }
            /* return (food[0].getName() == "Rice" &&
            food[1].getName() == "Egg" &&
            (food[2].getName() == "Tapa" ||
            food[2].getName() == "Bacon" ||
            food[2].getName() == "Longganisa")); */
        }
        return false;
    }

    public Plate servePlate(Plate p) {
        if (isServable(p))  {
            p.removeFood();
            numServedDishes++;
        }
        return p;
    }

    public int getNumServedDishes() {
        return numServedDishes;
    }

    @Override
    public boolean isStoring() {
        return true;
    }

    public OrderMenu getOrderMenu() {
        return om;
    }
}
