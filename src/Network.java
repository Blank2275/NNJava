public class Network {
    private Layer[] layers;
    public Network(Layer[] layers){
        this.layers = layers;
        this.genNetwork();
    }

    private void genNetwork(){
        for(int i = 1; i < layers.length; i++){
            layers[i].setRandomWeights(layers[i - 1].getN());
            layers[i].setRandomBiases();
        }
    }

    public double[] evaluateNetwork(double[] input){
        for(int i = 1; i < layers.length; i++){
            input = layers[i].evaluate(input);
        }
        return input;
    }

    public Layer[] getLayers(){
        return layers;
    }
}
