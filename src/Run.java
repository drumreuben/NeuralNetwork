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
        int collisions;

        int i = 0;
        while(true){
            collisions = 0;
            m.simulate(20000, 20000, i);
            for(Sweeper s : m.getSweepers()){
                collisions += s.getFitness();
            }
            System.out.println("total collisions : " + collisions);
            for(int j = 0; j < m.getTargets().size(); j++) {
                m.getTargets().remove(j);
                m.getTargets().add(new Target(m));
            }
            m.setSweepers(GeneticAlgorithm.makeNextGeneration(m.getSweepers(), m));
            System.out.println(i) ;
            i++;
        }

    }

}
