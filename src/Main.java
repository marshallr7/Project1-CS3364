import SortingHandler.NewSortingHandler;
import SortingHandler.SortHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final SortHandler sh = new SortHandler();
    private static final NewSortingHandler nsh = new NewSortingHandler();

    public static void main(String[] args) throws IOException {
        List<String> fileNames = Arrays.asList(
                "assets/source1.txt",
                "assets/source2.txt",
                "assets/source3.txt",
                "assets/source4.txt",
                "assets/source5.txt"
        );
        List<Integer> sums = nsh.sumValuesByIndex(fileNames);
        System.out.println(sums);
        nsh.sortAllFilesWithPositions("quick", sums);

    }
}