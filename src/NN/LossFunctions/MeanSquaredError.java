package NN.LossFunctions;

public class MeanSquaredError extends LossFunction{
    @Override
    public double evaluateLoss(double[][] expected, double[][] actual){
        double avg = 0;
        for(int i = 0; i < expected.length; i++){
            for(int j = 0; j < expected[0].length; j++) {
                avg += Math.pow((actual[i][j] - actual[i][j]), 2) / expected[0].length;
            }
        }
        avg /= expected.length;
        return avg;
    }
}
