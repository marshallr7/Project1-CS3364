package File;

import java.io.File;
import java.io.IOException;
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
        File folder = new File("assets");
        File[] files = folder.listFiles();
        FileCache fileCache = new FileCache();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().startsWith("source") && file.getName().endsWith(".txt")) {
                    String fileName = file.getPath();
                    List<Integer> data = fileCache.loadFromFile(fileName);
                    System.out.println("Loaded " + data.size() + " integers from " + fileName);
                }
            }
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
