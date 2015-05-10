import java.util.List;

/**
 * Miro Furtado
 * 5/9/2015
 */
public class NeuronNet {
    //Basic parameter variables for a NeuronNet
    int numInputs;
    int numOutputs;
    int numHiddenLayers;
    int numNeuronsPerHiddenLayer;
    List<NeuronLayer> layers;

    /**
     * Constructor for NeuronNet using above parameters
     */
    public NeuronNet(int numInputs, int numOutputs, int numHiddenLayers, int numNeuronsPerHiddenLayer) {
        //First add the first hidden layer
        layers.add(new NeuronLayer(numNeuronsPerHiddenLayer, numInputs));
        //Add the rest of the hidden layers
        for(int i = 1; i < numHiddenLayers; i++) {
            layers.add(new NeuronLayer(numNeuronsPerHiddenLayer, numNeuronsPerHiddenLayer));
        }
        //Add output layer
        layers.add(new NeuronLayer(numOutputs, numNeuronsPerHiddenLayer));
    }
}
