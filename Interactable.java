/** The Interactable class is an interactable class for all the objects being held or manipulated by the player.
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
import java.awt.image.*;

public abstract class Interactable {
    protected double x1, y1, width, height;
    protected String name;
    protected String type;
    protected BufferedImage sprite;
    protected CounterTile assignedTile;

    public Interactable() {

    }

    public Interactable(double x, double y, String n) {
        x1 = x;
        y1 = y;
        name = n;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setX(double x) {
        x1 = x;
    }

    public void setY(double y) {
        y1 = y;
    }

    public double getX() {
        return x1;
    }

    public double getY() {
        return y1;
    }

    public void setTile(CounterTile c) {
        assignedTile = c;
    }

    public void moveRight(double x) {
        x1 += x;
    }

    public void moveLeft(double x) {
        x1 -= x;
    }

    public void moveUp(double y) {
        y1 -= y;
    }

    public void moveDown(double y) {
        y1 += y;
    }

    public void scaleDown(double n) {
        width -= n;
        height -= n;
    }

    public void importSprites() {}

    public BufferedImage getSprite() {
        return sprite;
    }

    public void changeSprite(BufferedImage bi) {
        sprite = bi;
    }

    public void draw(Graphics2D g2d) {

    }
}

