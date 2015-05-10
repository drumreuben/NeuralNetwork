import java.util.ArrayList;
import java.util.List;

/***
 * Miro Furtado
 * 5/9/2015
 * Implementation of an individual Neuron, which takes input values and returns an output based on weights.
 */
public class Neuron {
    //Defining weights, inputs, and activation as per http://www.ai-junkie.com/ann/evolved/nnt3.html
    private List<Double> weights = new ArrayList<Double>();
    private List<Double> inputs = new ArrayList<Double>();
    private int numInputs;
    private double output;

    /**
     * Constructor for a neuron with numInputs number of inputs.
     * Creates one more weight than input as the threshold value
     */
    public Neuron(int in) {
        for(int i = 0; i <= in; i++) {
            weights.add(Math.random());
        }
        numInputs = in;
    }

    /**
     * Constructor for a neuron with numInputs number of inputs and weights
     */
    public Neuron(int nIn, List<Double> w) {
        nIn = numInputs;
        weights = w;
    }

    /**
     * Sets input list for Neuron
     * Creates one more input of value -1 as the threshold value
     */
    public void setInputs(List<Double> inp) {
        inputs = inp;
        //inputs.add(-1.0);
    }

    /**
     * Sets weight list for Neuron
     */
    public void setWeights(List<Double> weig) {
        weights = weig;
    }

    /**
     * Get functions below
     */
    public List<Double> getInputs(){
        return inputs;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public double getOutput(){
        return output;
    }

    public int getNumInputs(){
        return numInputs;
    }

    /**
     * Calculates activation and output
     */
    public double calcOutput(){
        double activation = 0;
        for(int i = 0; i < weights.size(); i++) {
            activation += weights.get(i) * inputs.get(i);
        }
        output = 1/(1+Math.pow(Math.E, -activation));
        return output;
    }
    /**
     * Calculates activation and output for a given set of inputs
     */
    public double calcOutput(List<Double> inp) {
        //System.out.println(weights.size());
        setInputs(inp);
        return calcOutput();
    }
}
