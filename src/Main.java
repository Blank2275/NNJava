import NN.*;
import NN.Activations.ActivationRelu;
import NN.Activations.ActivationSoftmax;
import NN.LossFunctions.CategoricalCrossentropy;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException{
        Layer[] layers = new Layer[4];
        layers[0] = new Layer(null, 2);
        layers[1] = new Layer(new ActivationRelu(), 5);
        layers[2] = new Layer(new ActivationRelu(), 3);
        layers[3] = new Layer(new ActivationSoftmax(), 2);

        Network network = new Network(layers);

        int num_examples = 1000;
        double[][] inputData = new double[num_examples][2];
        double[][] outputData = new double[num_examples][2];
        for(int i = 0; i < num_examples; i++){
            double n1 = Math.random() * .5;
            double n2 = Math.random() * .5;
            double r1 = 0;
            double r2 = 0;
            if(n1 + n2 < 0) r1 = 1;
            else r2 = 1;
            inputData[i] = new double[]{n1, n2};
            outputData[i] = new double[]{r1, r2};
        }

        DataSet data = new DataSet(inputData, outputData);

       Trainer trainer = new Trainer(100, network, new CategoricalCrossentropy());
//
       Network bestNetwork = trainer.train(300, data);

        //for testing, tested with known parameters to make sure it evaluated correctly
//        layers[0].setBiases(new double[]{1});
//        layers[0].setWeights(new double[][]{{1, 1}});
//        layers[1].setWeights(new double[][]{{1, 1}});
//        layers[1].setBiases(new double[]{0});
//        layers[2].setWeights(new double[][]{{1}});
//        layers[2].setBiases(new double[]{0});

        // NN.Network loadedNetwork = NN.Saver.load("./best.wb");

        double[] res = bestNetwork.evaluate(new double[]{0.5, 0.2});
        System.out.println(res[0] + " " + res[1]);

       Saver saver = new Saver(bestNetwork);
       saver.save("./best.wb");
    }
}
