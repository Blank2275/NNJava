import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Saver {
    private Network network;

    public Saver(Network network) {
        this.network = network;
    }

    public void save(String filepath) {
        try {
            File file = new File(filepath);
            FileWriter writer = new FileWriter(filepath);
            file.createNewFile();
            writer.write(encodeModel());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occured saving the file");
            e.printStackTrace();

        }
    }

    public static Network load(String filepath) {
        Network n = new Network(new Layer[]{});
        try {
            File file = new File(filepath);
            Scanner reader = new Scanner(file);

            String data = "";
            while (reader.hasNextLine()) {
                data += reader.nextLine();
            }

            n = decodeModel(data);
        } catch (IOException e) {
            System.out.println("An error occured loading the file");
            e.printStackTrace();
        }
        return n;
    }

    private static Network decodeModel(String data){
        //read header
        String[] dataArr = data.split("\n");
        String currentLine = dataArr[0];

        int numLayers = Integer.parseInt(dataArr[0]);

        Layer[] layers = new Layer[numLayers];
        int i = 1;
        while(currentLine != "==="){
            String[] parts = currentLine.split(" ");

            ActivationType activation = null;

            if(parts[0].equals("relu")){
                activation = new ActivationRelu();
            }

            int n = Integer.parseInt(parts[1]);
            layers[i - 1] = new Layer(activation, n);
            currentLine = dataArr[i];
            i++;
        }

        int layer = 0;
        double[] biases = null;
        double[][] weights = null;
        int currentNode = 0;
        for(int j = 0; j < dataArr.length; j++){
            if(biases != null && !dataArr[j].equals("===")){
                //if not on first layer until next ===
                weights[currentNode] = strArrToDbArr(dataArr[j].split(" "));
                currentNode++;
                
            }
            if(dataArr[j].equals("===")){
                layer++;
                j++;
                int n = layers[layer].getN();
                int pastN = layers[layer - 1].getN();

                //actually set the layer's weights and biases
                
                
                biases = strArrToDbArr(dataArr[j].split(" "));
                j++;
                weights = new double[n][pastN];
                currentNode = 0;

                
            }
        }
        
        Network n = new Network(layers);
        return n;
    }

    private String encodeModel() {
        String res = "";

        // add layer information
        res += network.getLayers().length + "\n";
        for (Layer layer : network.getLayers()) {
            res += layer.toEncoded() + "\n";
        }

        res += "===\n";// divider

        for (Layer layer : network.getLayers()) {// add in each layer's weights and biases
            double[][] weights = layer.getWeights();
            double[] biases = layer.getBiases();

            if (biases == null)
                continue; // if first layer, not needed

            // first line will be biases b1 b2 b3 b4 b5
            // the rest of the lines will be weights
            // w01 w02 w03 w04 w05
            // w11 w12 w13 w14 w15

            res += String.join(" ", dbArrToStrArr(biases)) + "\n";

            for (double[] node : weights) {
                res += String.join(" ", dbArrToStrArr(node)) + "\n";
            }

            res += "===\n";
        }

        return res;
    }

    private String[] dbArrToStrArr(double[] input) {
        String[] res = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            res[i] = String.valueOf(input[i]);
        }
        return res;
    }

    private static double[] strArrToDbArr(String[] input){
        double[] res = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            res[i] = Double.parseDouble(input[i]);
        }
        return res;
    }
}