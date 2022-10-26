package NN.Activations;

public class ActivationSigmoid extends ActivationType {
    public ActivationSigmoid(){
        encodeName = "relu";
    }
    @Override
    public double[] activate(double[] in) {
        double[] changed = new double[in.length];
        for(int i = 0; i < in.length; i++){
            changed[i] = 1 / 1 + Math.exp(-in[i]);
        }
        return changed;
    }
}
