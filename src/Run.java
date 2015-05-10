import java.util.ArrayList;
import java.util.List;

/***
 * Created by Reuben on 5/9/2015.
 * Driver method for the program. Initializes the minefield and prompts it to run each generation,
 * then applies the genetic algorithm methods to create the new generation.
 */
public class Run {

    //number of generations to simulate without drawing
    private int generations;

    //how long to run each generation
    private int tick;

    public static void main(String[] args){
        //int sweeperCount = Integer.parseInt(args[0]);
        //int mineCount = Integer.parseInt(args[1]);
        Minefield m = new Minefield(800, 800, 10, 6);
        int oldCollisions = 0;
        int currentCollisions;
        List<Sweeper> currentGen;
        List<Sweeper> oldGen = m.getSweepers();

        int i = 0;
        while(true){
            currentGen = GeneticAlgorithm.makeNextGeneration(oldGen, m);
            currentCollisions = 0;
            m.setSweepers(currentGen);
            m.simulate(10000, 500, i);
            for(Sweeper s : currentGen){
                currentCollisions += s.getFitness();
                //m.getSweepers().set(0, new Sweeper(m));
            }
            for(int j = 0; j < m.getTargets().size(); j++) {
                m.getTargets().remove(j);
                m.getTargets().add(new Target(m));
            }
            if(currentCollisions < oldCollisions){
                m.setSweepers(oldGen);
            } else {
                oldGen = currentGen;
                oldCollisions = currentCollisions;
            }
            System.out.println("total collisions : " + currentCollisions);
            System.out.println(i) ;
            i++;
        }

    }

}
