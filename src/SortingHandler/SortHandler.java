package SortingHandler;

import FileManager.FileCache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SortHandler {
    private final FileCache fc;
    private int inversions;

    public SortHandler() {
        this.fc = new FileCache();
        this.inversions = 0;
    }

    public void mergeSort(List<Integer> list) {
        if (list.size() < 2) {
            return;
        }
        int mid = list.size() / 2;
        List<Integer> left = new ArrayList<>(list.subList(0, mid));
        List<Integer> right = new ArrayList<>(list.subList(mid, list.size()));
        mergeSort(left);
        mergeSort(right);
        merge(left, right, list);
    }

    private void merge(List<Integer> left, List<Integer> right, List<Integer> list) {
        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                list.set(i + j, left.get(i));
                i++;
            } else {
                list.set(i + j, right.get(j));
                j++;
                inversions += left.size() - i;
            }
        }
        while (i < left.size()) {
            list.set(i + j, left.get(i));
            i++;
        }
        while (j < right.size()) {
            list.set(i + j, right.get(j));
            j++;
        }
    }

    public void quickSort(List<Integer> list,int low,int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high); // start by calling the partition function
            quickSort(list, low, pivotIndex - 1); // quickSort numbers lower than pivot
            quickSort(list, pivotIndex + 1, high); // quickSort numbers higher than pivot
        }
    }

    private int partition(List<Integer> list, int low, int high) {
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

    private void swap(List<Integer> list, int i, int j) {
        Integer temp = list.get(i);
        list.set(i,list.get(j));
        list.set(j,temp);
    }

    public int getInversions() {
        return inversions;
    }

    public boolean isSorted(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public void sortAllFiles(String algorithm) throws IOException {
        FileCache fc = new FileCache();
        fc.loadAllFiles();
        File folder = new File("assets");
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                List<Integer> list = fc.cache.get(file.getPath());
                if (list != null) {
                    switch (algorithm) {
                        case "mergeSort":
                            mergeSort(list);
                            break;
                        // add cases for other sorting algorithms here
                        case "quickSort":
                            quickSort(list, 0, list.size() - 1);
                            break;
                        default:
                            System.out.println("Invalid sorting algorithm specified");
                            return;
                    }
                    if (isSorted(list)) {
                        fc.fileHandler.writeToFile(list, file.getPath() + "_sorted_" + algorithm);
                        System.out.println("FileManager " + file.getPath() + " sorted with " + inversions + " inversions");
                    } else {
                        System.out.println("FileManager " + file.getPath() + " is not sorted");
                    }
                }
                inversions = 0; // reset inversions count
            }
        }
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


}

