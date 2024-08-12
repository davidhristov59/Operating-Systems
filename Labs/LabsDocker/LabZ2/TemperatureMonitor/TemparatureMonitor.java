import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemperatureMonitor {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("temperature.txt"));
                String line = reader.readLine();
                String[] temperatures = line.split(" ");
                int sum = 0;
                for (String temp : temperatures) {
                    sum += Integer.parseInt(temp);
                }
                int average = sum / temperatures.length;
                String level;
                if (average >= 5 && average <= 19) {
                    level = "Low";
                } else if (average >= 20 && average <= 35) {
                    level = "Medium";
                } else {
                    level = "High";
                }
                FileWriter writer = new FileWriter("temperaturelevel.txt");
                writer.write(level);
                writer.close();
                reader.close();
                Thread.sleep(60000); // Wait for 60 seconds
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
