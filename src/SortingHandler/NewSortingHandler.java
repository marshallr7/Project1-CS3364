package SortingHandler;

import FileManager.FileCache;
import FileManager.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewSortingHandler {

    private final FileCache fc;
    private final FileHandler fh;
    private int inversions;

    public NewSortingHandler() {
        this.fc = new FileCache();
        this.fh = new FileHandler();
        this.inversions = 0;
    }

    public List<Integer> sumValuesByIndex(List<String> fileNames) throws IOException {
        List<List<Integer>> lists = new ArrayList<>();
        for (String fileName : fileNames) {
            List<Integer> list = fc.loadFromFile(fileName);
            if (list != null) {
                lists.add(list);
            }
        }
        List<Integer> result = new ArrayList<>();
        int maxLength = lists.stream().mapToInt(List::size).max().orElse(0);
        for (int i = 0; i < maxLength; i++) {
            int sum = 0;
            for (List<Integer> list : lists) {
                if (i < list.size()) {
                    sum += list.get(i);
                }
            }
            result.add(sum);
        }
        return result;
    }

    public void mergeSortWithPositions(List<Integer> list, List<Integer> positions) {
        int n = list.size();
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        List<Integer> left = new ArrayList<>(list.subList(0, mid));
        List<Integer> right = new ArrayList<>(list.subList(mid, n));
        List<Integer> leftPositions = new ArrayList<>(positions.subList(0, mid));
        List<Integer> rightPositions = new ArrayList<>(positions.subList(mid, n));
        mergeSortWithPositions(left, leftPositions);
        mergeSortWithPositions(right, rightPositions);
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                list.set(k, left.get(i));
                positions.set(k, leftPositions.get(i));
                i++;
            } else {
                list.set(k, right.get(j));
                positions.set(k, rightPositions.get(j));
                j++;
            }
            k++;
        }
        while (i < left.size()) {
            list.set(k, left.get(i));
            positions.set(k, leftPositions.get(i));
            i++;
            k++;
        }
        while (j < right.size()) {
            list.set(k, right.get(j));
            positions.set(k, rightPositions.get(j));
            j++;
            k++;
        }
    }

    public void sortAllFilesWithPositions(String sortingAlgorithm, List<Integer> sumList) throws IOException {
        List<String> fileNames = Arrays.asList("assets/source1.txt", "assets/source2.txt", "assets/source3.txt", "assets/source4.txt", "assets/source5.txt");
        // Load the lists from the files
        List<List<Integer>> lists = new ArrayList<>();
        for (String fileName : fileNames) {
            List<Integer> list = this.fh.loadFromFile(fileName);
            if (list != null) {
                lists.add(list);
            }
        }
        // Sort the sum list and keep track of the positions
        List<Integer> positions = new ArrayList<>(sumList.size());
        for (int i = 0; i < sumList.size(); i++) {
            positions.add(i);
        }
        if (sortingAlgorithm.equalsIgnoreCase("merge")) {
            mergeSortWithPositions(sumList, positions);
//        } else if (sortingAlgorithm.equalsIgnoreCase("quick")) {
//            quickSortWithPositions(sumList, positions);
        } else {
            throw new IllegalArgumentException("Unsupported sorting algorithm: " + sortingAlgorithm);
        }
        // Apply the same movements to the other five lists
        for (int i = 0; i < lists.size(); i++) {
            List<Integer> list = lists.get(i);
            List<Integer> newList = new ArrayList<>(list.size());
            for (int j = 0; j < list.size(); j++) {
                int pos = positions.get(j);
                int value = list.get(pos);
                newList.add(value);
            }
            // Save the new sorted file
            String newFileName = String.format("%s_Sorted_%s", sortingAlgorithm, fileNames.get(i).substring(7));
            this.fh.writeToFile(newList, newFileName);
        }
        // Save the sorted sum list
        String sumFileName = String.format("%s_Sorted_Sum.txt", sortingAlgorithm);
        this.fh.writeToFile(sumList, sumFileName);
    }


}
