import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ExecutionCounter {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = null;
        Integer counter = 0;
        try {
            File dataDir = new File("./data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            File rafFile = new File(dataDir, "raf.out");
            if (!rafFile.exists()) {
                rafFile.createNewFile();
            }
            raf = new RandomAccessFile(rafFile, "rw");
            if (raf.length() == 0) {
                counter = 1;
            } else {
                counter = raf.readInt();
                counter++;
            }
            System.out.println(counter);
            raf.seek(0);
            raf.writeInt(counter);
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            counter = 1;
            System.out.println(counter);
            raf.writeInt(counter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
