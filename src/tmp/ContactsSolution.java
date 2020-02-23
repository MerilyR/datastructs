package tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.arraycopy;
import static java.lang.System.in;
import static java.lang.System.out;

public class ContactsSolution {

    static final String ADD = "add";
    static final String FIND = "find";

    static int[] contacts(String[][] queries) {

        List<Integer> found = new ArrayList<>();
        //List<String> contacts = new ArrayList<>();
        Map<Character, List<String>> contacts = new HashMap<>();

        for (String[] query : queries) {
            String op = query[0];
            String name = getName(query);
            if (op.equals(ADD)) {
                contacts.computeIfAbsent(name.charAt(0), k -> new ArrayList<>());
                addSorted(contacts.get(name.charAt(0)), name);
            } else if (op.equals(FIND)) {
                found.add(numberOfMatchingContacts(contacts.get(name.charAt(0)), name));
            }
        }
        return found.stream().mapToInt(i->i).toArray();
    }

    static void addSorted(List<String> strings, String newString){
        int index = findPositionFor(strings, newString);
        if(index == -1)
            strings.add(newString);
        else
            strings.add(index, newString);
    }

    private static int findPositionFor(List<String> strings, String newString) {

        int lower = 0;
        int upper = strings.size()-1;

        if(strings.isEmpty() || strings.get(upper).compareTo(newString) < 0)
            return -1;
        if(strings.get(lower).compareTo(newString) > 0)
            return lower;

        while(lower <= upper){
            int mid = lower + ((upper - lower) / 2);
            String before = strings.get(mid);
            String after = (mid + 1 != strings.size()) ? strings.get(mid + 1) : null;
            if(before.compareTo(newString) < 0
                    &&
                    (after != null && after.compareTo(newString) >= 0)){
                return mid + 1 ;
            }
            else if(before.compareTo(newString) == 0)
                return mid;
            if(newString.compareTo(before) > 0){
                lower = mid + 1;
            }
            else{
                upper = mid - 1;
            }
        }
        /*
        for(int i = 0; i<strings.size(); i++){
            String before = strings.get(i);
            String after = i + 1 != strings.size() ? strings.get(i + 1) : null;
            if(before.compareTo(newString) < 0
                    &&
                    (after == null || after.compareTo(newString) > 0)){
                index = i + 1;
                break;
            }
        }
        */

        return -1;
    }

    private static int numberOfMatchingContacts(List<String> contacts, String partial) {
        return binaryMatch(contacts, partial);
    }

    private static int binaryMatch(List<String> strings, String partial) {
        if (strings == null)
            return 0;

        int lower = 0;
        int upper = strings.size() - 1;
        int index = findPositionFor(strings, partial);

        int matched = 0;
        if (index != -1) {
            //count matched
            while (index < strings.size()) {
                if (strings.get(index).matches(partial + ".*")) {
                    matched++;
                    index++;
                } else
                    break;
            }
        }

        return matched;
    }

    private static String getName(String[] input) {
        String s = "";
        for(String str: java.util.Arrays.copyOfRange(input, 1, input.length)){
            s = s.concat(str);
        }
        return s;
    }

    private static final Scanner scanner = new Scanner(in);

    public static void main(String[] args) {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int queriesRows = Integer.parseInt(scanner.nextLine().trim());

        String[][] queries = new String[queriesRows][2];

        for (int queriesRowItr = 0; queriesRowItr < queriesRows; queriesRowItr++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");

            arraycopy(queriesRowItems, 0, queries[queriesRowItr], 0, 2);
        }

        int[] result = contacts(queries);

        for (int i : result) {
            out.println(i);
        }
    }
}
