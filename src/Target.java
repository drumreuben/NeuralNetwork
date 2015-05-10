import java.awt.*;

/**
 * Created by Reuben on 5/9/2015.
 * Collected by the sweepers. Every time a target is collected, it is replaced on the field at another random location.
 */
public class Target {

    //position of the target
    private int x;
    private int y;

    //width and height of target
    private int width = 10;
    private int height = 10;

    /*
    Gets x Position
     */
    public int getX() {
        return x;
    }

    /*
    Gets y position
     */
    public int getY() {
        return y;
    }

    /*
    Draw method for the target. Used by the custom graphics panel for animation
     */
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, width, height);
    }
}
