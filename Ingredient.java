/** The Ingredient class can be Tapa, Longganisa, and Bacon, and can be cooked or sliced.
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
import java.io.*;
import javax.imageio.ImageIO;

public class Ingredient extends Interactable {
    protected boolean cooked;
    protected boolean sliced;
    private BufferedImage spriteRawEgg, spriteCookedEgg;
    private BufferedImage spriteRawRice, spriteCookedRice;
    private BufferedImage spriteRawTapa, spriteSlicedTapa, spriteCookedTapa;
    private BufferedImage spriteRawBacon, spriteSlicedBacon, spriteCookedBacon;
    private BufferedImage spriteRawLong, spriteSlicedLong, spriteCookedLong;

    public Ingredient(double x, double y, String n) {
        super(x, y, n);
        type = "Ingredient";
        width = 60;
        height = 60;
        cooked = false;
        sliced = false;
        importSprites();
        
        switch (name) {
            case "Egg":
                sprite = spriteRawEgg;
                break;
            case "Rice":
                sprite = spriteRawRice;
                break;
            case "Tapa":
                sprite = spriteRawTapa;
                break;
            case "Bacon":
                sprite = spriteRawBacon;
                break;
            case "Longganisa":
                sprite = spriteRawLong;
                break;
        }
    }

    public void importSprites() {
        try {
            spriteRawEgg = ImageIO.read(new File("sprites/rawegg.png"));
            spriteCookedEgg = ImageIO.read(new File("sprites/cookedegg.png"));
            spriteRawRice = ImageIO.read(new File("sprites/rawrice.png"));
            spriteCookedRice = ImageIO.read(new File("sprites/cookedrice.png"));
            spriteRawTapa = ImageIO.read(new File("sprites/rawtapa.png"));
            spriteSlicedTapa = ImageIO.read(new File("sprites/slicedtapa.png"));
            spriteCookedTapa = ImageIO.read(new File("sprites/cookedtapa.png"));
            spriteRawBacon = ImageIO.read(new File("sprites/rawbacon.png"));
            spriteSlicedBacon = ImageIO.read(new File("sprites/slicedbacon.png"));
            spriteCookedBacon = ImageIO.read(new File("sprites/cookedbacon.png"));
            spriteRawLong = ImageIO.read(new File("sprites/rawlongganisa.png"));
            spriteSlicedLong = ImageIO.read(new File("sprites/slicedlongganisa.png"));
            spriteCookedLong = ImageIO.read(new File("sprites/cookedlongganisa.png"));
        } catch (IOException ex) {
            System.out.println("IOException from Ingredient importSprites()");
        }
    }

    public void draw(Graphics2D g2d) {
        if (sprite != null) g2d.drawImage(sprite,(int)x1 + 13,(int)y1 + 15,(int)width,(int)height,null);
    }

    public boolean isCooked() {
        return cooked;
    }

    public void setCooked() {
        cooked = true;
        switch (name) {
            case "Egg":
                sprite = spriteCookedEgg;
                break;
            case "Rice":
                sprite = spriteCookedRice;
                break;
            case "Tapa":
                sprite = spriteCookedTapa;
                break;
            case "Bacon":
                sprite = spriteCookedBacon;
                break;
            case "Longganisa":
                sprite = spriteCookedLong;
                break;
        }
    }
    
    public boolean isSliced() {
        return sliced;
    }

    public void setSliced() {
        sliced = true;
        switch (name) {
            case "Tapa":
                sprite = spriteSlicedTapa;
                break;
            case "Bacon":
                sprite = spriteSlicedBacon;
                break;
            case "Longganisa":
                sprite = spriteSlicedLong;
                break;
        }
    }
}
