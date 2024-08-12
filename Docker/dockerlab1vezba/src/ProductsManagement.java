import java.io.*;

public class ProductsManagement {

    public static void main(String[] args) {
        String path = System.getenv("PRODUCT_FILE_PATH");
        BufferedReader reader = null;
        String line = null;
        try {
            if (path == null) {
                throw new RuntimeException("PRODUCT_FILE_PATH environment variable is not set.");
            }
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            while ((line = reader.readLine()) != null) {
                String[] cells = line.split(";");
                if (cells.length != 3) {
                    throw new RuntimeException("Invalid row!");
                }
                System.out.printf("Product Name: %s\n", cells[0]);
                System.out.printf("Product Price: %s\n", cells[1]);
                System.out.printf("Product Quantity: %s\n", cells[2]);
                System.out.println("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
