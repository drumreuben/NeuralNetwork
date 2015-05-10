import java.util.List;

/**
 * Miro Furtado
 * 5/9/2015
 */
public class Neuron {
    //Defining weights, inputs, and activation as per http://www.ai-junkie.com/ann/evolved/nnt3.html
    private List<Double> weights;
    private List<Double> inputs;
    private double activation = 0;
    private double output = 0;

    /**
     * Sets input list for Neuron
     */
    public void setInputs(List<Double> inp) {
        inputs = inp;
    }

    /**
     * Sets weight list for Neuron
     */
    public void setWeights(List<Double> weig) {
        weights = weig;
    }

    /**
     * Gets output from neuron
     */
    public double getOutput(){
        return output;
    }

    /**
     * Calculates activation and output
     */
    public void calcOutput(){
        for(int i = 0; i < weights.size(); i++) {
            activation += weights.get(i) * inputs.get(i);
        }
        output = 1/(1+Math.pow(Math.E, -activation));
    }
    /**
     * Calculates activation and output for a given set of inputs
     */
    public void calcOutput(List<Double> inp) {
        setInputs(inp);
        calcOutput();
    }
}
