import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class BasicDataOperationsUsingSet {
    Integer valueToSearch;
    int sourceValueCount;
    Set<Integer> valueSet;

    BasicDataOperationsUsingSet(Integer valueToSearch, Integer[] valueArray) {
        this.valueToSearch = valueToSearch;
        this.sourceValueCount = valueArray.length;
        this.valueSet = new TreeSet<>(Arrays.asList(valueArray));
    }

    public void executeDataAnalysis() {
        findInSet();
        locateMinMaxInSet();
        analyzeDuplicates();

        Integer[] sortedSetValues = valueSet.toArray(new Integer[0]);
        DataFileHandler.writeArrayToFile(sortedSetValues, BasicDataOperations.SORTED_FILE_PREFIX + ".set.data");
    }

    private void findInSet() {
        String operationName = "пошук елемента '" + valueToSearch + "' в TreeSet<Integer>";
        long timeStart = System.nanoTime();

        boolean elementExists = this.valueSet.contains(valueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        if (elementExists) {
            System.out.println("Елемент '" + valueToSearch + "' знайдено в TreeSet");
        } else {
            System.out.println("Елемент '" + valueToSearch + "' відсутній в TreeSet.");
        }
    }

    private void locateMinMaxInSet() {
        if (valueSet == null || valueSet.isEmpty()) {
            System.out.println("TreeSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        Integer minValue = Collections.min(valueSet);
        Integer maxValue = Collections.max(valueSet);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в TreeSet<Integer>");

        System.out.println("Найменше значення в TreeSet: " + minValue);
        System.out.println("Найбільше значення в TreeSet: " + maxValue);
    }

    private void analyzeDuplicates() {
        System.out.println("Кiлькiсть вхiдних значень: " + sourceValueCount);
        System.out.println("Кiлькiсть елементiв в TreeSet: " + valueSet.size());
        System.out.println("Повторних входжень, вiдкинутих TreeSet: " + (sourceValueCount - valueSet.size()));
    }
}
