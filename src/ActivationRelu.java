public class ActivationRelu extends  ActivationType{
    public ActivationRelu(){
        encodeName = "relu";
    }
    @Override
    public double activate(double in) {
        return Math.max(super.activate(in), 0);
    }
}
