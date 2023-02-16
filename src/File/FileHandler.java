package File;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public List<Integer> loadFromFile(String fileName) throws IOException {
        List<Integer> allIntegers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            int value = Integer.parseInt(line);
            allIntegers.add(value);
        }
        reader.close();

        return allIntegers;
    }

    public void printFileContents(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    public boolean isFileSorted(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int previousValue = Integer.MIN_VALUE; // set initial value to the smallest integer
        String line;
        while ((line = reader.readLine()) != null) {
            int value = Integer.parseInt(line);
            if (value < previousValue) {
                reader.close();
                return false;
            }
            previousValue = value;
        }
        reader.close();
        return true;
    }
}
