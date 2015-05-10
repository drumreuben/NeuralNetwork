import java.util.List;

/***
 * Created by Reuben on 5/9/2015.
 * The field in which the sweepers and targets exist
 */
public class Minefield {

    //targets on the field
    private List<Target> targets;

    //sweepers on the field
    private List<Sweeper> sweepers;

    //dimension of the minefield
    private int width;
    private int height;

    //controls speed of the animation by causing the thread to sleep for a set amount of milliseconds
    private final int ANIMATION_SPEED = 2;

    /***
    gets the list of targets
     */
    public List<Target> getTargets() {
        return targets;
    }

    /***
    Gets the list of sweepers
     */
    public List<Sweeper> getSweepers() {
        return sweepers;
    }

    /***
    Gets width
    */
    public int getWidth() {
        return width;
    }

    /***
    Gets height
     */
    public int getHeight() {
        return height;
    }

    /***
    simulates a single generation of sweepers
     */
    public void simulate(int ticks, int drawDelay, int generation){
        int i = 0;
        while(i < ticks){
            for(Sweeper s : sweepers) {
                //update the position of the sweeper
                fixPosition(s);
            }
        }
    }

    /***
    fixes the position of each sweeper on the field so they do not go out of bounds
     */
    public void fixPosition(Sweeper s){
        if(s.getX() > width){
            s.setX(0);
        }
        if(s.getX() < 0){
            s.setX(width);
        }
        if(s.getY() > height){
            s.setY(0);
        }
        if(s.getY() < 0){
            s.setY(height);
        }
    }
}
