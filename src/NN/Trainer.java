package NN;

import NN.DataSet;
import NN.Layer;
import NN.Network;

public class Trainer {
    private int batchSize;
    private int topTenSize;
    private Network base;

    public Trainer(int batchSize, Network base) {
        this.batchSize = batchSize;
        this.topTenSize = batchSize / 10;
        this.base = base;
    }

    public Network train(int generations, DataSet data) throws CloneNotSupportedException {
        Network[] batch = generateBatch(batchSize);

        for (int g = 0; g < generations; g++) {
            double[] scores = new double[batch.length];
            for (int n = 0; n < batch.length; n++) {
                scores[n] = evaluate(batch[n], data);
            }
            // get top 10 percent
            int[] topIndices = new int[topTenSize];
            for (int i = 0; i < topTenSize; i++) {
                int index = getLowestIndex(scores);
                scores[index] = Integer.MAX_VALUE;
                topIndices[i] = index;
            }
            for (int n = 0; n < batch.length; n++) {
                scores[n] = evaluate(batch[n], data);
            }

            // replace current batch with improved batch
            Network[] newBatch = new Network[batch.length];
            for (int i = 0; i < batch.length; i++) {
                int p1Index = (int) (Math.random() * topTenSize);
                int p2Index = (int) (Math.random() * topTenSize);
                Network p1 = batch[topIndices[p1Index]];
                Network p2 = batch[topIndices[p2Index]];
                newBatch[i] = spawnChild(p1, p2);
            }
            System.out.println(scores[topIndices[0]]);
            System.out.println(scores[6]);
            System.out.println();
            //System.out.println(String.format("generation %s, loss:%s", g + 1,scores[topIndices[0]]));
            batch = newBatch;

        }
        double[] scores = new double[batch.length];
        for (int n = 0; n < batch.length; n++) {
            scores[n] = evaluate(batch[n], data);
        }
        int topIndex = getLowestIndex(scores);

        System.out.println("========= Final Accuracy =========");
        System.out.println(scores[topIndex]);
        
        
        return batch[topIndex];
    }

    private int getLowestIndex(double[] arr) {
        int index = 0;
        double value = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Integer.MAX_VALUE)
                continue;
            if (arr[i] < value) {
                index = i;
                value = arr[i];
            }
        }
        return index;
    }

    private Network[] generateBatch(int size) throws CloneNotSupportedException{
        Network[] res = new Network[size];
        Network b = base.duplicate();
        for(int i = 0; i < size; i++){
            res[i] = new Network(b.getLayers());
            res[i].genNetwork();
        }
        return res;
    }

    public double evaluate(Network network, DataSet data) {
        double[][] expected = data.getOutput();
        double[][] input = data.getInput();

        double[][] output = new double[expected.length][expected[0].length];

        for (int i = 0; i < output.length; i++) {
            output[i] = network.evaluateNetwork(input[i]);
        }

        return calcError(expected, output);
    }

    private double calcError(double[][] expected, double[][] actual) {
        double diff = 0;
        int total = 0;
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                diff += Math.pow(expected[i][j] - actual[i][j], 2);
                total++;
            }
        }
        diff /= total;
        return diff;
    }

    private Network spawnChild(Network p1, Network p2) throws CloneNotSupportedException {
        Network b = base.duplicate();
        Network child = new Network(b.getLayers());

        for (int l = 1; l < child.getLayers().length; l++) {
            Layer p1Layer = p1.getLayers()[l];
            Layer p2Layer = p2.getLayers()[l];
            Layer newLayer = mergeLayers(p1Layer, p2Layer);
            child.setLayer(l, newLayer);
        }

        return child;
    }

    private Layer mergeLayers(Layer l1, Layer l2) {
        Layer merged = new Layer(l1.getActivation(), l1.getN());
        merged.setRandomBiases();
        merged.setRandomWeights(l1.getWeights()[0].length);
        // merge weights
        double[][] mergedWeights = merged.getWeights();
        double[] mergedBiases = merged.getBiases();
        for (int c = 0; c < l1.getWeights().length; c++) {
            for (int p = 0; p < l1.getWeights()[0].length; p++) {
                if (Math.random() > 0.5) {
                    mergedWeights[c][p] = l1.getWeights()[c][p];
                } else {
                    mergedWeights[c][p] = l2.getWeights()[c][p];
                }
                if (Math.random() > 0.99) {
                    mergedWeights[c][p] = Layer.genValue(-1, 1);
                }
            }
        }

        for (int i = 0; i < l1.getBiases().length; i++) {
            if (Math.random() > 0.5) {
                mergedBiases[i] = l1.getBiases()[i];
            } else {
                mergedBiases[i] = l2.getBiases()[i];
            }
            if (Math.random() > 0.99) {
                mergedBiases[i] = Layer.genValue(-1, 1);
            }
        }

        merged.setWeights(mergedWeights);
        merged.setBiases(mergedBiases);

        return merged;
    }
}
