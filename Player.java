/** The Player class is the character being controlled by the client.
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
import javax.imageio.ImageIO;

public class Player {
    private double x1, y1, width, height;
    private Rectangle2D.Double hitbox;
    private BufferedImage sprite, spriteUp, spriteDown, spriteLeft, spriteRight;
    private String direction;
    private int playerID;
    public Interactable heldItem;
    
    public Player(double x, double y, int pID) {
        x1 = x;
        y1 = y;
        width = 175;
        height = 175;
        playerID = pID;
        hitbox = new Rectangle2D.Double();
        heldItem = null;
        importSprites();
        sprite = spriteDown;
        direction = "down";
    }

    private void importSprites() {
        if (playerID == 1) {
            try {
                spriteUp = ImageIO.read(new File("sprites/chefup.png"));
                spriteDown = ImageIO.read(new File("sprites/chefdown.png"));
                spriteLeft = ImageIO.read(new File("sprites/chefleft.png"));
                spriteRight = ImageIO.read(new File("sprites/chefright.png"));
            } catch (IOException ex) {
                System.out.println("IOException from Player importSprites()");
            }
        } else {
            try {
                spriteUp = ImageIO.read(new File("sprites/chefup.png"));
                spriteDown = ImageIO.read(new File("sprites/chefdown.png"));
                spriteLeft = ImageIO.read(new File("sprites/chefleft.png"));
                spriteRight = ImageIO.read(new File("sprites/chefright.png"));
            } catch (IOException ex) {
                System.out.println("IOException from Player importSprites()");
            }
        }
    }

    public void draw(Graphics2D g2d) {
        hitbox.setRect(x1 + 40, y1 + 5, width - 85, height - 5);
        if (sprite == spriteUp) {
            if (isHoldingItem()) drawHeldItem(g2d);
            if (sprite != null)
            g2d.drawImage(sprite,(int)x1,(int)y1,(int)width,(int)height,null);
        } else {
            if (sprite != null)
            g2d.drawImage(sprite,(int)x1,(int)y1,(int)width,(int)height,null);
            if (isHoldingItem()) drawHeldItem(g2d);
        }
    }

    public void drawHeldItem(Graphics2D g2d) {
        heldItem.setX(x1 + 45);
        heldItem.setY(y1 + 60);
        heldItem.draw(g2d);
    }

    public void faceRight() {
        sprite = spriteRight;
        direction = "right";
    }

    public void faceLeft() {
        sprite = spriteLeft;
        direction = "left";
    }

    public void faceDown() {
        sprite = spriteDown;
        direction = "down";
    }

    public void faceUp() {
        sprite = spriteUp;
        direction = "up";
    }

    public void moveRight(double x) {
        if (playerID == 1) {
            if (!(x1 + width >= 660)) 
            x1 += x;
        } else {
            if (!(x1 >= 900))
            x1 += x;
        }
        faceRight();
    }

    public void moveLeft(double x) {
        if (playerID == 1) {
            if (!(x1 <= 210)) 
            x1 -= x;
        } else {
            if (!(x1 <= 610)) 
            x1 -= x;
        }
        faceLeft();
    }

    public void moveUp(double y) {
        if (!(y1 <= 110)) 
        y1 -= y;
        faceUp();
    }

    public void moveDown(double y) {
        if (!(y1 + 110 >= 530)) 
        y1 += y;
        faceDown();
    }

    public String getDirection() {
        return direction;
    }

    public double getX() {
        return hitbox.getX();
    }

    public double getY() {
        return hitbox.getY();
    }

    public void setX(double x) {
        x1 = x - 40;
    }

    public void setY(double y) {
        y1 = y - 5;
    }

    public double getWidth() {
        return hitbox.getWidth();
    }

    public double getHeight() {
        return hitbox.getHeight();
    }

    public int getPlayerID() {
        return playerID;
    }

    public void holdItem(Interactable i) {
        heldItem = i;
    }

    public boolean isHoldingItem() {
        if (heldItem != null) return true; else return false;
    }

    public Interactable getHeldItem() {
        return heldItem;
    }

    public void doAction(CounterTile c) {
        if (!isHoldingItem() && c.isStoring())
            grabItem(c);
        else if (isHoldingItem() && !c.isStoring()) {
            switch (c.getType()) {
                case "Counter Tile":
                    placeItem(c);
                    break;
                case "Stove Tile":
                    if (heldItem.getType() == "Cooking Gadget") {
                        CookingGadget tempCG = (CookingGadget) heldItem;
                        placeItem(c);
                        if (tempCG.isFull())
                        cookFood(c,tempCG);
                    }
                    break;
                case "Chopping Board Tile":
                    sliceFood((ChoppingBoardTile)c);
                    break;
                    
            }
        } else if (isHoldingItem() && c.isStoring()) {
            switch (c.getType()) {
                case "Counter Tile":
                    addFood(c);
                    break;
                case "Stove Tile":
                    if (c.getStoredItem().getType() == "Cooking Gadget") {
                        CookingGadget tempCG = (CookingGadget) c.getStoredItem();
                        addFood(c);
                        if (!tempCG.isIngredientCooked())
                        cookFood(c,tempCG);
                    }
                    break;
                case "Trash Tile":
                    throwTrash(c);
                    break;
                case "Serving Tile":
                    serveFood((ServingTile)c);
                    break;
            }
        }
    }

    private void placeItem(CounterTile c) {
        for (String s : c.getStorableItems()) {
            if (s == heldItem.getType()) {
                heldItem.setTile(c);
                c.storeItem(heldItem);
                heldItem = null;
                break;
            }
        }

    }

    private void grabItem(CounterTile c) {
        if (c.getType() == "Ingredient Tile") {
            heldItem = c.getStoredItem();
        } else if (c.getType() == "Chopping Board Tile") {
            if (((Ingredient)c.getStoredItem()).isSliced()) {   
                Ingredient i = new Ingredient(c.getStoredItem().getX(), c.getStoredItem().getY(),
                c.getStoredItem().getName());
                i.setSliced();
                heldItem = i;
                c.removeItem();
            }
        } else if (c.getType() != "Trash Tile" && c.getType() != "Serving Tile") {
            if (c.getStoredItem().getType() == "Cooking Gadget") {
                CookingGadget tempCG = (CookingGadget) c.getStoredItem();
                if (tempCG.isCooking()) tempCG.stopCooking();
            }
            heldItem = c.getStoredItem();
            c.removeItem();
        }
    }

    private void throwTrash(CounterTile c) {
        switch (heldItem.getType()) {
            case "Ingredient":
                heldItem = null;
                break;
            case "Plate":
                Plate tempPlate = (Plate) heldItem;
                tempPlate.removeFood();
                heldItem = tempPlate;
                break;
            case "Cooking Gadget":
                CookingGadget tempCG = (CookingGadget) heldItem;
                tempCG.removeFood();
                heldItem = tempCG;
                break;
        }  
    }

    private void addFood(CounterTile c) {
        switch (heldItem.getType()) {
            case "Cooking Gadget":
                CookingGadget tempCG = (CookingGadget) heldItem;
                if (c.getStoredItem().getType() == "Ingredient") {
                    tempCG.addFood((Ingredient)c.getStoredItem());
                    heldItem = tempCG;
                    c.removeItem();
                } else if (c.getStoredItem().getType() == "Plate") {
                    Plate tempPlate = (Plate) c.getStoredItem();
                    if (tempPlate.isIngredientAddable(tempCG.getCookingIngredient())) {
                        tempPlate.addFood(tempCG.getCookingIngredient());
                        tempCG.removeFood();
                        c.replaceItem(tempPlate);
                        heldItem = tempCG;
                    }
                }
                break;
            case "Plate":
                Plate tempPlate = (Plate) heldItem;
                if (c.getStoredItem().getType() == "Cooking Gadget") {
                    tempCG = (CookingGadget) c.getStoredItem();
                    if (tempCG.isFull()) {
                        if (tempPlate.isIngredientAddable(tempCG.getCookingIngredient())) {
                            tempPlate.addFood(tempCG.getCookingIngredient());
                            tempCG.removeFood();
                            c.replaceItem(tempCG);
                            heldItem = tempPlate;
                        }
                    }
                }
                break;
            case "Ingredient":
                if (c.getStoredItem().getType() == "Cooking Gadget") {
                    tempCG = (CookingGadget) c.getStoredItem();
                    tempCG.addFood((Ingredient)heldItem);
                    if (tempCG.isFull()) {
                        c.replaceItem(tempCG);
                        heldItem = null;
                    }
                }
                break;
        }
    } 

    private void cookFood(CounterTile c, CookingGadget cg) {
        cg.setTile(c);
        cg.cook();
        c.replaceItem(cg);
    }

    private void sliceFood(ChoppingBoardTile c) {
        if (heldItem.getType() == "Ingredient") {
                Ingredient tempIngredient = (Ingredient) heldItem;
                if (c.isIngredientAddable(tempIngredient)) {
                    c.storeItem(heldItem);
                    heldItem = null;
                    c.slice();
                }
        }
    }

    private void serveFood(ServingTile c) {
        if (heldItem.getType() == "Plate") {
            heldItem = c.servePlate((Plate)heldItem);
        }
    }
}
