package SortingHandler;

import FileManager.FileCache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SortHandler {
    private int inversions = 0;

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
                switch (algorithm) {
                    case "mergeSort":
                        mergeSort(list);
                        break;
                    // add cases for other sorting algorithms here
                    default:
                        System.out.println("Invalid sorting algorithm specified");
                        return;
                }
                if (isSorted(list)) {
                    fc.fileHandler.writeToFile(list, file.getPath() + "_sorted_algorithm");
                    System.out.println("FileManager " + file.getPath() + " sorted with " + inversions + " inversions");
                } else {
                    System.out.println("FileManager " + file.getPath() + " is not sorted");
                }
                inversions = 0; // reset inversions count
            }
        }
    }
}

