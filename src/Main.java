import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FileCache fc = new FileCache();
        fc.loadAllFiles();
        fc.fileHandler.printFileContents("assets/source1.txt");
    }
}