public class ActivationRelu extends  ActivationType{
    @Override
    public double activate(double in) {
        return Math.max(super.activate(in), 0);
    }
}
