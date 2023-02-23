package SortingHandler;

import FileManager.FileCache;
import FileManager.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public int mergeSortWithPositions(List<Integer> list, List<Integer> positions, List<Integer> inversions) {
        int n = list.size();
        if (n < 2) {
            return 0;
        }
        int mid = n / 2;
        List<Integer> left = new ArrayList<>(list.subList(0, mid));
        List<Integer> right = new ArrayList<>(list.subList(mid, n));
        List<Integer> leftPositions = new ArrayList<>(positions.subList(0, mid));
        List<Integer> rightPositions = new ArrayList<>(positions.subList(mid, n));
        int leftInversions = mergeSortWithPositions(left, leftPositions, inversions);
        int rightInversions = mergeSortWithPositions(right, rightPositions, inversions);
        int i = 0, j = 0, k = 0;
        int inversionsCount = leftInversions + rightInversions;
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                list.set(k, left.get(i));
                positions.set(k, leftPositions.get(i));
                i++;
            } else {
                list.set(k, right.get(j));
                positions.set(k, rightPositions.get(j));
                j++;
                int invCount = mid - i;
                inversionsCount += invCount;
                inversions.set(k, inversions.get(k) + invCount);
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
        return inversionsCount;
    }


    public void quickSortWithPositions(List<Integer> list,List<Integer> positions,int low,int high) {
        if (low < high) {
            int pivotIndex = partitionWithPositions(list, positions, low, high); // start by calling the partition function
            quickSortWithPositions(list, positions, low, pivotIndex - 1); // quickSort numbers lower than pivot
            quickSortWithPositions(list, positions, pivotIndex + 1, high); // quickSort numbers higher than pivot
        }
    }

    private int partitionWithPositions(List<Integer> list,List<Integer> positions,int low, int high) {
        Integer pivot = list.get(low); // set pivot to first value in list
        int j = high;
        for (int i = high; i > low; i--) {
            if (list.get(i) > pivot) { // if the current value is greater than the pivot value
                swap(list,i,j); // swap the values
                swap(positions,i,j--);
            }
        }
        swap(list,low,j); // swap initial pivot value into its correct position
        swap(positions,low,j);
        return j; // return location of pivot value
    }

    private void swap(List<Integer> list, int i, int j) {
        Integer temp = list.get(i);
        list.set(i,list.get(j));
        list.set(j,temp);
    }

    public void quickSortWithInversions(List<Integer> list,int low,int high) {
        if (low < high) {
            int pivotIndex = partitionWithInversions(list, low, high); // start by calling the partition function
            quickSortWithInversions(list, low, pivotIndex - 1); // quickSort numbers lower than pivot
            quickSortWithInversions(list, pivotIndex + 1, high); // quickSort numbers higher than pivot
        }
    }

    private int partitionWithInversions(List<Integer> list, int low, int high) {
        Integer pivot = list.get(low); // set pivot to first value in list
        int j = high;
        for (int i = high; i > low; i--) {
            if (list.get(i) > pivot) { // if the current value is greater than the pivot value
                swap(list,i,j--); // swap the values
                inversions++; // increment inversions
            }
        }
        swap(list,low,j); // swap initial pivot value into its correct position
        inversions++; // increment inversions
        return j; // return location of pivot value
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
            List<Integer> inversions = new ArrayList<>(Collections.nCopies(sumList.size(), 0));
            int inv = mergeSortWithPositions(sumList, positions, inversions);
            System.out.println(inv);
        } else if (sortingAlgorithm.equalsIgnoreCase("quick")) {
            quickSortWithPositions(sumList, positions, 0, sumList.size()-1);
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
        // Need: re-sort contents of each newly-sorted file and count inversions
        // Compare (in report)
    }


}
