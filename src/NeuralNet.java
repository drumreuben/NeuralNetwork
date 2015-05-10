import java.util.List;

/**
 * Miro Furtado
 * 5/9/2015
 */
public class NeuralNet {
    //Basic parameter variables for a NeuralNet
    int numInputs;
    int numOutputs;
    int numHiddenLayers;
    int numNeuronsPerHiddenLayer;
    List<NeuronLayer> layers;

    /**
     * Constructor for NeuralNet using above parameters
     */
    public NeuralNet(int numInputs, int numOutputs, int numHiddenLayers, int numNeuronsPerHiddenLayer) {
        //First add the first hidden layer
        layers.add(new NeuronLayer(numNeuronsPerHiddenLayer, numInputs));
        //Add the rest of the hidden layers
        for(int i = 1; i < numHiddenLayers; i++) {
            layers.add(new NeuronLayer(numNeuronsPerHiddenLayer, numNeuronsPerHiddenLayer));
        }
        //Add output layer
        layers.add(new NeuronLayer(numOutputs, numNeuronsPerHiddenLayer));
    }

    public List<Double> processNet(List<Double> i) {
        List<Double> input = i;
        List<Double> output = null;
        for(NeuronLayer l : layers) {
            output = l.processLayer(input);
            input = output;
        }
        return output;
    }

    /**
     * Returns 1-class deep copy of NeuralNet
     */
    public NeuralNet getCopy() {
        return new NeuralNet(numInputs, numOutputs, numHiddenLayers, numNeuronsPerHiddenLayer);
    }

    /**
     * Returns NeuralNet's layers
     */
    public List<NeuronLayer> getLayers() {
        return layers;
    }
}
