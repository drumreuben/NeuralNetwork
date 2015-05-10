import java.util.ArrayList;
import java.util.List;

/**
 * Miro Furtado
 * 5/9/2015
 */
public class NeuronLayer {
    private List<Neuron> neurons;
    private int numNeurons;

    /**
     * Constructs a NeuronLayer of numNeurons consisting of Neurons with numInputs number of inputs
     */
    public NeuronLayer(int numNeurons, int numInputs){
        for(int i = 0; i < numNeurons; i++)
            neurons.add(new Neuron(numInputs));
    }

    /**
     * Sets the neuronlayer to have these neurons
     */
    public void setNeurons(List<Neuron> n) {
        neurons = n;
    }

    /**
     * Adds neuron to NeuronLayer
     */
    public void addNeuron(Neuron n) {
        neurons.add(n);
    }

    /**
     * Gets neuron of number num
     */
    public Neuron getNeuron(int num) {
        return neurons.get(num);
    }

    /**
     * Returns an array of the outputs from all Neurons in the NeuronLayer
     */
    public List<Double> processLayer(List<Double> input) {
        List<Double> outputs = new ArrayList<Double>();
        for(Neuron n : neurons) {
            outputs.add(n.calcOutput(input));
        }
        return outputs;
    }
}
