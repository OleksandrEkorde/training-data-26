import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;

/**
 * Клас BasicDataOperationsUsingQueue реалізує роботу з чергою ArrayDeque для Integer.
 *
 * <p>Основні функції класу:</p>
 * <ul>
 *   <li>{@link #runDataProcessing()} - Запускає комплекс операцій з даними.</li>
 *   <li>{@link #findInQueue()} - Пошук значення в черзі Integer.</li>
 *   <li>{@link #locateMinMaxInQueue()} - Знаходить граничні значення в черзі.</li>
 *   <li>{@link #performQueueOperations()} - Виконує операції peek і poll з чергою.</li>
 * </ul>
 */
public class BasicDataOperationsUsingQueue {
    private Integer valueToSearch;
    private Queue<Integer> valueQueue;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     *
     * @param valueToSearch Значення для пошуку
     * @param valueArray Масив Integer
     */
    BasicDataOperationsUsingQueue(Integer valueToSearch, Integer[] valueArray) {
        this.valueToSearch = valueToSearch;
        this.valueQueue = new ArrayDeque<>(Arrays.asList(valueArray));
    }

    /**
     * Запускає комплексну обробку даних з використанням черги.
     *
     * Метод виконує пошук і визначення граничних значень у черзі Integer,
     * а потім операції peek і poll.
     */
    public void runDataProcessing() {
        findInQueue();
        locateMinMaxInQueue();

        // зберігаємо до файлу відсортовані значення з ArrayDeque (з повторами);
        // ArrayDeque зберігає порядок надходження, тому сортуємо копію її вмісту
        Integer[] sortedQueueValues = valueQueue.toArray(new Integer[0]);
        Arrays.sort(sortedQueueValues);
        DataFileHandler.writeArrayToFile(sortedQueueValues, BasicDataOperations.SORTED_FILE_PREFIX + ".queue.data");

        // Вилучення виконуємо після пошуку і запису файлу, щоб вони працювали з повним складом черги.
        performQueueOperations();
    }

    /**
     * Здійснює пошук конкретного значення в черзі.
     */
    private void findInQueue() {
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "пошук елемента '" + valueToSearch + "' в ArrayDeque<Integer>";
        long timeStart = System.nanoTime();

        // ArrayDeque зберігає елементи в циклічному масиві в порядку надходження.
        // Впорядкування немає, тому contains() перебирає елементи від голови й порівнює
        // їх через equals(), доки не знайде збіг. Це лінійний пошук, O(n).
        boolean elementExists = this.valueQueue.contains(valueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        if (elementExists) {
            System.out.println("Елемент '" + valueToSearch + "' знайдено в Queue");
        } else {
            System.out.println("Елемент '" + valueToSearch + "' відсутній в Queue.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в черзі Integer.
     */
    private void locateMinMaxInQueue() {
        if (valueQueue == null || valueQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();

        // Collections.min() і Collections.max() обходять усі елементи черги ітератором
        // і порівнюють їх через compareTo(), тому кожен виклик O(n).
        Integer minValue = Collections.min(valueQueue);
        Integer maxValue = Collections.max(valueQueue);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в ArrayDeque<Integer>");

        System.out.println("Найменше значення в Queue: " + minValue);
        System.out.println("Найбільше значення в Queue: " + maxValue);
    }

    /**
     * Виконує операції peek і poll з чергою Integer.
     */
    private void performQueueOperations() {
        if (valueQueue == null || valueQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        // peek() повертає голову черги, тобто перший доданий елемент, не видаляючи його; O(1).
        Integer headElement = valueQueue.peek();
        System.out.println("Головний елемент черги (peek): " + headElement);

        // poll() видаляє голову черги; O(1). Новою головою стає наступний за порядком надходження елемент.
        headElement = valueQueue.poll();
        System.out.println("Видалений елемент черги (poll): " + headElement);

        headElement = valueQueue.peek();
        System.out.println("Новий головний елемент черги: " + headElement);
    }
}
