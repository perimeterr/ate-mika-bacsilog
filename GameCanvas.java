/** The GameCanvas class is where most of the drawing of the objects happens.
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
import javax.swing.*;

public class GameCanvas extends JComponent {
    private int width = 1280;
    private int height = 720;
    private int gameState;
    private OrderMenu om;
    private CountdownTimer cdt;
    private Player player1;
    private Player player2;
    private BufferedImage bg, menu1, menu2, endscreen;

    private FryingPan fp1, fp2;
    private RiceCooker rc;
    private Plate p1, p2, p3;

    private CounterTile[] counterTiles;
    private final int maxNumCounterTiles = 25;

    public GameCanvas(Player p1, Player p2) {
        this.setPreferredSize(new Dimension(width, height));
        gameState = 0;
        
        counterTiles = new CounterTile[maxNumCounterTiles];
        om = new OrderMenu();
        cdt = new CountdownTimer();

        om.addOrder(0);
        om.addOrder(1);
        om.addOrder(2);
    

        player1 = p1;
        player2 = p2;
        importBG();
        createCounterTiles();
        createInteractables();
    }

    private void importBG() {
        try {
            bg = ImageIO.read(new File("sprites/bg.png"));
            menu1 = ImageIO.read(new File("sprites/menu1.png"));
            menu2 = ImageIO.read(new File("sprites/menu2.png"));
            endscreen = ImageIO.read(new File("sprites/endscreen.png"));
        } catch (IOException ex) {
            System.out.println("IOException from GameCanvas importBG()");
        }
    }

    public void changeGameState(int n) {
        gameState = n;
    }

    private void createCounterTiles() {
        counterTiles[0] = new CounterTile(344, 130);
        counterTiles[1] = new CounterTile(423, 130);
        counterTiles[2] = new CounterTile(592, 211);
        counterTiles[3] = new CounterTile(592, 297);
        counterTiles[4] = new CounterTile(592, 380);
        counterTiles[5] = new CounterTile(592, 463);
        counterTiles[6] = new CounterTile(266, 563);
        counterTiles[7] = new CounterTile(344, 563);
        counterTiles[8] = new CounterTile(423, 563);
        counterTiles[9] = new CounterTile(186, 211);
        counterTiles[10] = new CounterTile(1003, 211);
        counterTiles[11] = new CounterTile(1003, 380);
        counterTiles[12] = new CounterTile(838, 563);
        counterTiles[13] = new StoveTile(266, 130);
        counterTiles[14] = new StoveTile(500, 130);
        counterTiles[15] = new IngredientTile(1003, 297, "Egg");
        counterTiles[16] = new IngredientTile(1003, 463, "Rice");
        counterTiles[17] = new IngredientTile(186, 463, "Tapa");
        counterTiles[18] = new IngredientTile(186, 380, "Bacon");
        counterTiles[19] = new IngredientTile(186, 297, "Longganisa");
        counterTiles[20] = new TrashTile(500, 563);
        counterTiles[21] = new TrashTile(680, 563);
        counterTiles[22] = new ServingTile(620, 130, om);
        counterTiles[23] = new ChoppingBoardTile(759, 563);
        counterTiles[24] = new ChoppingBoardTile(915, 563);

    }

    private void createInteractables() {
        fp1 = new FryingPan();
        fp2 = new FryingPan();
        rc = new RiceCooker();
        p1 = new Plate();
        p2 = new Plate();
        p3 = new Plate();

        counterTiles[6].storeItem(fp1);
        counterTiles[7].storeItem(p1);
        counterTiles[8].storeItem(fp2);
        counterTiles[10].storeItem(p2);
        counterTiles[11].storeItem(rc);
        counterTiles[12].storeItem(p3);
    }

    public void setGameState(int n) {
        gameState = n;
        if (gameState == 2)
        cdt.startTimer();
    }

    public int getGameState() {
        return gameState;
    }

    public CounterTile[] getCounterTiles() {
        return counterTiles;
    }   

    public CounterTile getCounterTile(int n) {
        return counterTiles[n];
    }

    public OrderMenu getOrderMenu() {
        return om;
    }

    public void updateOrderMenu(OrderMenu om) {
        this.om = om;
    }

    public CountdownTimer getCountdownTimer() {
        return cdt;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(rh);

        if (gameState == 0) {
            if (menu1 != null)
            g2d.drawImage(menu1,0,0,width,height,this);
        } else if (gameState == 1) {
            if (menu2 != null)
            g2d.drawImage(menu2,0,0,width,height,this);
        } else if (gameState == 2) {
            if (bg != null)
            g2d.drawImage(bg,0,0,width,height,this);
            for (CounterTile c : counterTiles)
            c.draw(g2d);

            player1.draw(g2d);
            player2.draw(g2d);
            om.draw(g2d);
            cdt.draw(g2d);
        } else if (gameState == 3) {
            if (endscreen != null);
            g2d.drawImage(endscreen,0,0,width,height,this);
            String score = Integer.toString(om.getCompletedOrders());
            g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 140));
            g2d.setColor(Color.WHITE);
            g2d.drawString(score, 570, 500);
        }

    }
}
