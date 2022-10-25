public class ActivationRelu extends  ActivationType{
    public ActivationRelu(){
        encodeName = "relu";
    }
    @Override
    public double[] activate(double[] in) {
        double[] changed = new double[in.length];
        for(int i = 0; i < in.length; i++){
            changed[i] = Math.max(in[i], 0);
        }
        return changed;
    }
}
