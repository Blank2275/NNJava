package NN.Activations;

public abstract class ActivationType {
    protected String encodeName = "";
    public double[] activate(double[] in){
        return in;
    }

    public String getEncodeName(){
        return encodeName;
    }
}
