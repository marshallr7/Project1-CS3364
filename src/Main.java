import SortingHandler.SortHandler;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        SortHandler sh = new SortHandler();

        sh.sortAllFiles("mergeSort");
    }
}