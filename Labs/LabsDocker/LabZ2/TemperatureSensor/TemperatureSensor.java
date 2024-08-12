import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TemperatureSensor {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        while (true) {
            int temperature = random.nextInt(46) + 5;
            try {
                FileWriter writer = new FileWriter("temperature.txt", true);
                writer.write(Integer.toString(temperature) + " ");
                writer.close();
                Thread.sleep(30000); // Wait for 30 seconds
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
