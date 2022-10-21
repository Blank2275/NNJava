public class Trainer {
    private int batchSize;
    private int topTenSize;
    private Network base;
    public Trainer(int batchSize, Network base){
        this.batchSize = batchSize;
        this.topTenSize = batchSize / 10;
        this.base = base;
    }

    private Network spawnChild(Network p1, Network p2){
        Network child = new Network(base.getLayers());

        for(int l = 0; l < child.getLayers().length; l++){

        }

        return child;
    }
}
