/** The CountdownTimer class creates a timer for the game.
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.swing.Timer;
import java.text.DecimalFormat;

public class CountdownTimer {
    private double x, y, width, height;
    private Rectangle2D.Double base;
    private Timer timer;
    private int second, minute;
    private String ddSecond, ddMinute;
    private DecimalFormat dFormat = new DecimalFormat("00");
    private String display;
    private boolean end;

    // Constructor for the the specifics of the timer
    public CountdownTimer() {
        minute = 1;
        second = 0;
        x = 1030;
        y = 0;
        width = 250;
        height = 100;
        base = new Rectangle2D.Double(x, y, width, height);
        display = "";
        end = false;
    }

    // Draws the timer
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(66,12,9,180));
        g2d.fill(base);
        g2d.setColor(Color.BLACK);
        g2d.draw(base);
        g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 80));
        g2d.setColor(Color.WHITE);
        g2d.drawString(display, (int) x + 15, (int) y + 80);
    }

    // Method for counting down 
    public void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                second--;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                display = String.format( ddMinute + ":" + ddSecond);
                if (second == -1) {
                    second = 59;
                    minute--;
                    ddSecond = dFormat.format(second);
                    ddMinute = dFormat.format(minute);
                    display = String.format( ddMinute + ":" + ddSecond);
                }
                if (minute == 0 && second == 0) {
                    timer.stop();
                    end = true;
                }
            }
        });
        timer.start();
    }

    public boolean hasTimerEnded() {
        return end;
    }
}
