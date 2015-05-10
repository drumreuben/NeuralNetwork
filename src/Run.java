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
        Minefield m = new Minefield(800, 800, 10, 3);

        int i = 0;
        while(true){
            m.simulate(5000, 15, i);
            int collisions = 0;
            for(Sweeper s : m.getSweepers()){
                collisions += s.getFitness();
                m.getSweepers().set(0, new Sweeper(m));
            }
            for(Target t : m .getTargets()) {
                t = new Target(m);
            }
            System.out.println("total collisions : " + collisions);
            List<Sweeper> nextGen = GeneticAlgorithm.makeNextGeneration(m.getSweepers(), m);
            m.setSweepers(nextGen);
            /*m.setSweepers(nextGen);*/
            System.out.println(i) ;
            i++;
        }

    }

}
