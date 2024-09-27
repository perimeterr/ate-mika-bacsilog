/** The Order class is the representation of the orders to be fulfilled by the players.
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
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Order {
    private double x1, y1;
    private double width, height;
    private Rectangle2D.Double base;
    private String name;
    private BufferedImage tapsilog, bacsilog, longsilog;

    public Order(int n) {
        switch (n) {
            case 0:
            name = "Tapa";
            break;
            case 1:
            name = "Bacon";
            break;
            case 2:
            name = "Longganisa";
            break;
        }
        x1 = 0;
        y1 = 0;
        width = 80;
        height = 80;
        importSprites();

    }

    public void importSprites() {
        try {
            tapsilog = ImageIO.read(new File("sprites/tapsilog.png"));
            bacsilog = ImageIO.read(new File("sprites/bacsilog.png"));
            longsilog = ImageIO.read(new File("sprites/longsilog.png"));
        } catch (IOException ex) {
            System.out.println("IOException from Order importSprites()");
        }
    }

    public void draw(Graphics2D g2d) {
        base = new Rectangle2D.Double(x1, y1, width, height);
        g2d.setColor(new Color(222, 144, 80, 50));
        g2d.fill(base);
        switch (name) {
            case "Tapa":
            if (tapsilog != null) g2d.drawImage(tapsilog,(int)x1,(int)y1,(int)width,(int)height,null);
            break;
            case "Bacon":
            if (bacsilog != null) g2d.drawImage(bacsilog,(int)x1,(int)y1,(int)width,(int)height,null);
            break;
            case "Longganisa":
            if (longsilog != null) g2d.drawImage(longsilog,(int)x1,(int)y1,(int)width,(int)height,null);
            break;
        }
    }

    public void setX(double x) {
        x1 = x;
    }

    public void setY(double y) {
        y1 = y; 
    }

    public String getName() {
        return name;
    }
}
