package tmp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RunningMedian {

    /*
     * Complete the runningMedian function below.
     */
    static double[] runningMedian(int[] a) {
        List<Double> medians = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();

        for (int data : a) {
            addSorted(intList, data);
            medians.add(findMedian(intList));
        }
        return medians.stream().mapToDouble(d -> d).toArray();
    }

    private static Double findMedian(List<Integer> intList) {
        double median;
        if (intList.size() % 2 == 1) {
            median = intList.get(intList.size() / 2);
        } else {
            int median1 = intList.get(intList.size() / 2 - 1);
            int median2 = intList.get(intList.size() / 2);
            median = (median1 + median2) / 2.0;
        }
        return Math.round(median * 10.0) / 10.0;
    }

    static void addSorted(List<Integer> ints, Integer newInt) {
        int index = findPositionFor(ints, newInt);
        if (index == -1)
            ints.add(newInt);
        else
            ints.add(index, newInt);
    }

    private static int findPositionFor(List<Integer> ints, Integer newInt) {

        int lower = 0;
        int upper = ints.size() - 1;

        if (ints.isEmpty() || ints.get(upper).compareTo(newInt) < 0)
            return -1;
        if (ints.get(lower).compareTo(newInt) > 0)
            return lower;

        while (lower <= upper) {
            int mid = lower + ((upper - lower) / 2);
            Integer before = ints.get(mid);
            Integer after = (mid + 1 != ints.size()) ? ints.get(mid + 1) : null;
            if (before.compareTo(newInt) < 0
                    &&
                    (after != null && after.compareTo(newInt) >= 0)) {
                return mid + 1;
            } else if (before.compareTo(newInt) == 0)
                return mid;
            if (newInt.compareTo(before) > 0) {
                lower = mid + 1;
            } else {
                upper = mid - 1;
            }
        }

        return -1;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int aCount = Integer.parseInt(scanner.nextLine().trim());

        int[] a = new int[aCount];

        for (int aItr = 0; aItr < aCount; aItr++) {
            int aItem = Integer.parseInt(scanner.nextLine().trim());
            a[aItr] = aItem;
        }

        double[] result = runningMedian(a);

        for (double v : result) {
            System.out.println(v);
        }

    }
}
