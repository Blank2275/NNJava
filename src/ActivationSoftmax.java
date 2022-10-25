public class ActivationSoftmax extends ActivationType {
    protected String encodeName = "softmax";

    @Override
    public double[] activate(double[] in){
        double[] res = new  double[in.length];

        double sum = 0;
        for(double d: in)
            sum += Math.exp(d);

        for(int i = 0; i < res.length; i++)
            res[i] = Math.exp(in[i]) / sum;

        return res;
    }
    public String

    getEncodeName(){
        return encodeName;
    }
}