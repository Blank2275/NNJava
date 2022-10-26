package NN;

public class Network implements Cloneable {
    private Layer[] layers;

    public Network(Layer[] layers) {
        this.layers = layers;
        this.genNetwork();
    }

    public void genNetwork() {
        for (int i = 1; i < layers.length; i++) {
            layers[i].setRandomWeights(layers[i - 1].getN());
            layers[i].setRandomBiases();
        }
    }

    public double[] evaluate(double[] input) {
        for (int i = 1; i < layers.length; i++) {
            input = layers[i].evaluate(input);
        }
        return input;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public void setLayer(int index, Layer layer) {
        layers[index] = layer;
    }

    public Network duplicate(){
        Layer[] l = new Layer[layers.length];
        for(int i = 0; i < layers.length; i++){
            l[i] = new Layer(layers[i].getActivation(), layers[i].getN());
        }

        for (int i = 1; i < layers.length; i++) {
            l[i].setWeights(layers[i].getWeights());
            l[i].setBiases(layers[i].getBiases());
        }
        
        Network n = new Network(l);

        return n;
        
    }
}
