import javax.swing.*;
import java.awt.*;

/***
 * Created by Reuben on 5/3/2015.
 * a custom graphics panel to render the minefield
 */
public class MyGraphicPanel extends JPanel {

    //the minefield the panel is rendering
    Minefield minefield;

    /**
    Constructs and sets preferred size based on minefield parameters
     */
    public MyGraphicPanel(Minefield mineField){
        this.minefield = mineField;
        setPreferredSize(new Dimension(minefield.getWidth(), minefield.getHeight()));
    }

    /**
    component painting method
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //runs the draw method for all the sweepers and targets on the minefield
        for(Sweeper s : minefield.getSweepers()){
            s.draw(g);
        }
        for(Target t : minefield.getTargets()){
            t.draw(g);
        }

    }


}
