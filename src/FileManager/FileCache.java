package FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCache {
    public final Map<String, List<Integer>> cache;
    public FileHandler fileHandler;

    public FileCache() {
        this.cache = new HashMap<>();
        this.fileHandler = new FileHandler();
    }

    public void loadAllFiles() throws IOException {
        for (int i = 1; i <= 5; i++) {
            String fileName = "assets/source" + i + ".txt";
            List<Integer> integers = fileHandler.loadFromFile(fileName);
            cache.put(fileName, new ArrayList<>(integers));
        }
    }


    public List<Integer> loadFromFile(String fileName) throws IOException {
        if (cache.containsKey(fileName)) {
            System.out.println("Using cached data for " + fileName);
            return cache.get(fileName);
        } else {
            System.out.println("Loading data from " + fileName);
            FileHandler fileHandler = new FileHandler();
            List<Integer> data = fileHandler.loadFromFile(fileName);
            cache.put(fileName, data);
            return data;
        }
    }
}
