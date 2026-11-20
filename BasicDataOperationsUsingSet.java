import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Клас BasicDataOperationsUsingSet реалізує операції з множиною TreeSet для Integer.
 *
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині Integer.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeDuplicates()} - Порівнює кількість вхідних значень і елементів множини.</li>
 * </ul>
 */
public class BasicDataOperationsUsingSet {
    Integer valueToSearch;
    int sourceValueCount;
    Set<Integer> valueSet;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     *
     * @param valueToSearch Значення для пошуку
     * @param valueArray Масив Integer
     */
    BasicDataOperationsUsingSet(Integer valueToSearch, Integer[] valueArray) {
        this.valueToSearch = valueToSearch;
        this.sourceValueCount = valueArray.length;
        this.valueSet = new TreeSet<>(Arrays.asList(valueArray));
    }

    /**
     * Запускає комплексний аналіз даних з використанням множини TreeSet.
     *
     * Метод виконує пошук, визначення граничних значень і аналіз повторів у множині Integer.
     */
    public void executeDataAnalysis() {
        findInSet();
        locateMinMaxInSet();
        analyzeDuplicates();

        // зберігаємо до файлу унікальні значення з TreeSet; вони вже впорядковані за зростанням
        Integer[] sortedSetValues = valueSet.toArray(new Integer[0]);
        DataFileHandler.writeArrayToFile(sortedSetValues, BasicDataOperations.SORTED_FILE_PREFIX + ".set.data");
    }

    /**
     * Здійснює пошук конкретного значення в множині.
     */
    private void findInSet() {
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "пошук елемента '" + valueToSearch + "' в TreeSet<Integer>";
        long timeStart = System.nanoTime();

        // TreeSet зберігає елементи у збалансованому дереві. contains() порівнює шукане значення
        // з вузлами через compareTo() і щоразу йде в ліве або праве піддерево; це O(log n).
        boolean elementExists = this.valueSet.contains(valueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        if (elementExists) {
            System.out.println("Елемент '" + valueToSearch + "' знайдено в TreeSet");
        } else {
            System.out.println("Елемент '" + valueToSearch + "' відсутній в TreeSet.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в множині Integer.
     */
    private void locateMinMaxInSet() {
        if (valueSet == null || valueSet.isEmpty()) {
            System.out.println("TreeSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        // Collections.min() і Collections.max() обходять усю множину ітератором і порівнюють
        // елементи через compareTo(); кожен виклик O(n), хоча TreeSet вже впорядкований.
        Integer minValue = Collections.min(valueSet);
        Integer maxValue = Collections.max(valueSet);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в TreeSet<Integer>");

        System.out.println("Найменше значення в TreeSet: " + minValue);
        System.out.println("Найбільше значення в TreeSet: " + maxValue);
    }

    /**
     * Порівнює кількість вхідних значень і елементів множини: різниця це відкинуті повтори.
     */
    private void analyzeDuplicates() {
        System.out.println("Кiлькiсть вхiдних значень: " + sourceValueCount);
        System.out.println("Кiлькiсть елементiв в TreeSet: " + valueSet.size());
        System.out.println("Повторних входжень, вiдкинутих TreeSet: " + (sourceValueCount - valueSet.size()));
    }
}
