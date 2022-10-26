package NN.LossFunctions;

public class CategoricalCrossentropy extends LossFunction{
    @Override
    public double evaluateLoss(double[][] expected, double[][] actual){
        double avg = 0;
        for(int i = 0; i < expected.length; i++){
            for(int j = 0; j < expected[0].length; j++) {
                avg -= expected[i][j] * Math.log10(actual[i][j]);
            }
        }
        avg /= expected.length;
        return avg;
    }
}
