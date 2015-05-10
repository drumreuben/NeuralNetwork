import java.util.ArrayList;
import java.util.List;

/***
 * Miro Furtado
 * 5/9/2015
 * Implementation of a network of Neurons which interact with each other.
 */
public class NeuralNet {
    //Basic parameter variables for a NeuralNet
    int numInputs;
    int numOutputs;
    int numHiddenLayers;
    int numNeuronsPerHiddenLayer;
    List<NeuronLayer> layers = new ArrayList<NeuronLayer>();
    List<Double> outputs = new ArrayList<Double>();

    /***
     * Constructor for NeuralNet using above parameters
     */
    public NeuralNet(int nIn, int numOut, int numHiddenL, int numNeuronsPerHiddenL) {
        numInputs = nIn;
        numOutputs = numOut;
        numHiddenLayers = numHiddenL;
        numNeuronsPerHiddenLayer = numNeuronsPerHiddenL;
        //First add the first hidden layer
        layers.add(new NeuronLayer(numNeuronsPerHiddenLayer, numInputs));
        //Add the rest of the hidden layers
        for(int i = 1; i < numHiddenLayers; i++) {
            layers.add(new NeuronLayer(numNeuronsPerHiddenLayer, numNeuronsPerHiddenLayer));
        }
        //Add output layer
        layers.add(new NeuronLayer(numOutputs, numNeuronsPerHiddenLayer));
    }

    /***
     * Processes NeuralNet from input and returns output
     */
    public List<Double> processNet(List<Double> i) {
        List<Double> input = i;
        List<Double> output = null;
        for(NeuronLayer l : layers) {
            output = l.processLayer(input);
            input = output;
        }
        outputs = output;
        return output;
    }

    public List<Double> getOutputs() {
        return outputs;
    }

    /***
     * Returns entire "genome"- ie. all the weights for all the neurons in the NeuralNet
     */
    public List<Double> getAllWeights(){
        List<Double> allWeights = new ArrayList<Double>();
        for(NeuronLayer l : layers)
            for(Neuron n : l.getNeurons())
                for(Double weight : n.getWeights())
                    allWeights.add(weight);
        return allWeights;
    }

    /***
     * Pushes new genome into NeuralNet
     */
    public void pushAllWeights(List<Double> genome){
        int startingInt = 0;
        for(NeuronLayer l : layers)
            for(Neuron n : l.getNeurons()){
                //System.out.println(n.getNumInputs());
                n.setWeights(genome.subList(startingInt, startingInt+n.getNumInputs()));
                startingInt += n.getNumInputs();
            }

    }

    /***
     * Returns 1-class deep copy of NeuralNet
     */
    public NeuralNet getCopy() {
        NeuralNet toReturn = new NeuralNet(numInputs, numOutputs, numHiddenLayers, numNeuronsPerHiddenLayer);
        return toReturn;
    }

    /***
     * Returns NeuralNet's layers
     */
    public List<NeuronLayer> getLayers() {
        return layers;
    }
}
