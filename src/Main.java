public class Main {
    public static void main(String[] args){
        Layer[] layers = new Layer[3];
        layers[0] = new Layer(null, 2);
        layers[1] = new Layer(new ActivationRelu(), 1);
        layers[2] = new Layer(new ActivationRelu(), 1);

        Network network = new Network(layers);

        //for testing, tested with known parameters to make sure it evaluated correctly
//        layers[0].setBiases(new double[]{1});
//        layers[0].setWeights(new double[][]{{1, 1}});
//        layers[1].setWeights(new double[][]{{1, 1}});
//        layers[1].setBiases(new double[]{0});
//        layers[2].setWeights(new double[][]{{1}});
//        layers[2].setBiases(new double[]{0});

        System.out.println(network.evaluateNetwork(new double[]{1, 2})[0]);
    }
}
