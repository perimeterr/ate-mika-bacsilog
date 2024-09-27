/** The OrderMenu class displays the list of all the orders.
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

public class OrderMenu {
    private Rectangle2D.Double base;
    private Order[] orderList;
    private int numOrders;
    private int maxNumOrders;
    private int completedOrders;

    public OrderMenu() {
        base = new Rectangle2D.Double(0,0,320,100); 
        numOrders = 0;
        maxNumOrders = 3;
        orderList = new Order[maxNumOrders];
        completedOrders = 0;
    }
    
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(66,12,9,180));
        g2d.fill(base);
        g2d.setColor(Color.BLACK);
        g2d.draw(base);
        for (Order o : orderList) {
            if (o != null) o.draw(g2d);
        }
    }
    
    public void addOrder(int n) {
        Order newOrder = new Order(n);
        for (int i = 0; i < maxNumOrders; i++) {
            if (orderList[i] == null) {
                orderList[i] = newOrder;
                numOrders++;
                newOrder.setX(20 + (i * 100));
                newOrder.setY(10);
                break;
            }
        }
    }

    public Order[] getOrderList() {
        return orderList;
    }


    public void completeOrder(String n) {
        for (int i = 0; i < orderList.length; i++) {
            if (orderList[i].getName() == n);
            orderList[i] = null;
            break;
        }
        completedOrders++;
        System.out.println(completedOrders);
        
    }

 

    public int getNumOrders() {
        return numOrders;
    }

    public int getMaxNumOrders() {
        return maxNumOrders;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }
}
