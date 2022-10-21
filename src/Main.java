public class Main {
    public static void main(String[] args) throws CloneNotSupportedException{
        Layer[] layers = new Layer[3];
        layers[0] = new Layer(null, 2);
        layers[1] = new Layer(new ActivationRelu(), 1);
        layers[2] = new Layer(new ActivationRelu(), 1);

        Network network = new Network(layers);

        int num_examples = 1000;
        double[][] inputData = new double[num_examples][2];
        double[][] outputData = new double[num_examples][1];
        for(int i = 0; i < num_examples; i++){
            double n1 = Math.random() * .5;
            double n2 = Math.random() * .5;
            inputData[i] = new double[]{n1, n2};
            outputData[i] = new double[]{n1 + n2};
        }

        DataSet data = new DataSet(inputData, outputData);

        Trainer trainer = new Trainer(100, network);

        Network bestNetwork = trainer.train(500, data);

        //for testing, tested with known parameters to make sure it evaluated correctly
//        layers[0].setBiases(new double[]{1});
//        layers[0].setWeights(new double[][]{{1, 1}});
//        layers[1].setWeights(new double[][]{{1, 1}});
//        layers[1].setBiases(new double[]{0});
//        layers[2].setWeights(new double[][]{{1}});
//        layers[2].setBiases(new double[]{0});

        System.out.println(bestNetwork.evaluateNetwork(new double[]{0.5, 0.2})[0]);
    }
}
