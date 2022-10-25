public class Layer {
    final protected ActivationType activation;
    private double[][] weights;
    private double[] biases;
    final private int n;

    public Layer(ActivationType activation, int n) {
        this.activation = activation;
        this.n = n;
    }

    public double[] evaluate(double[] past) {
        double[] finalVals = new double[n];
        for (int c = 0; c < n; c++) {
            // for each node in layer
            double res = 0;
            for (int p = 0; p < past.length; p++) {
                res += past[p] * weights[c][p];// c for current, p for past
            }
            res += biases[c];
            // res = activation.activate(res);
            finalVals[c] = res;
        }

        finalVals = activation.activate(finalVals);

        return finalVals;
    }

    public void setRandomWeights(int past) {
        weights = new double[n][past];
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = genValue(-1, 1);
            }
        }
    }

    public void setRandomBiases() {
        biases = new double[n];
        for (int i = 0; i < n; i++) {
            biases[i] = genValue(-1, 1);
        }
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }

    public void setBiases(double[] biases) {
        this.biases = biases;
    }

    public static double genValue(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public int getN() {
        return n;
    }

    public double[][] getWeights() {
        return weights;
    }

    public double[] getBiases() {
        return biases;
    }

    public ActivationType getActivation() {
        return activation;
    }

    public String toEncoded() {
        ActivationType active = getActivation();
        String activationName = "";
        if (active == null)
            activationName = "null";
        else
            activationName = active.getEncodeName();
        return String.format("%s %s", activationName, n);
    }
}
