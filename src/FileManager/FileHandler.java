package FileManager;

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


    public void writeToFile(List<Integer> list, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Integer integer : list) {
            writer.write(integer.toString());
            writer.newLine();
        }
        writer.close();
    }
}
