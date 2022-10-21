import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
            writer.write("abc");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occured saving the file");
            e.printStackTrace();
        
        }
    }
}