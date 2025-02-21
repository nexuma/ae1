package com.ae1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataUtils {

    public static int[] copyArray(ArrayList<Integer> data) {
        int n = data.size();
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = data.get(i);
        }
        return res;
    }

    public static int[] readArray(String path) throws FileNotFoundException {
        ArrayList<Integer> data = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextInt()) {
                data.add(sc.nextInt());
            }
        }
        return copyArray(data);
    }
}