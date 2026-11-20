import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Клас BasicDataOperationsUsingList реалізує операції з масивом Integer та колекцією LinkedList.
 *
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations(int)} - Виконує комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив Integer.</li>
 *   <li>{@link #findInArray()} - Здійснює пошук елемента в масиві Integer.</li>
 *   <li>{@link #locateMinMaxInArray()} - Визначає найменше і найбільше значення в масиві.</li>
 *   <li>{@link #sortList()} - Сортує колекцію LinkedList з Integer.</li>
 *   <li>{@link #findInList()} - Пошук конкретного значення в списку.</li>
 *   <li>{@link #locateMinMaxInList()} - Пошук мінімального і максимального значення в списку.</li>
 *   <li>analyzeCondition - вибірка значень, більших за поріг, у масиві та в списку.</li>
 * </ul>
 */
public class BasicDataOperationsUsingList {
    private boolean listSorted = false;
    private boolean arraySorted = false;
    private Integer valueToSearch;
    private Integer[] valueArray;
    private List<Integer> valueList;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     *
     * @param valueToSearch Значення для пошуку
     * @param valueArray Масив Integer
     */
    BasicDataOperationsUsingList(Integer valueToSearch, Integer[] valueArray) {
        this.valueToSearch = valueToSearch;
        this.valueArray = valueArray.clone();
        this.valueList = new LinkedList<>(Arrays.asList(valueArray));
    }

    /**
     * Виконує комплексні операції з масивом та списком: вибірка, пошук, мінімум і максимум
     * до та після сортування, після чого записує відсортований список у файл.
     *
     * @param threshold Поріг: вибираються значення, більші за нього
     */
    public void executeDataOperations(int threshold) {
        // спочатку працюємо з колекцією List
        System.out.println("List: до сортування");
        analyzeCondition(valueList, listSorted, threshold);
        findInList();
        locateMinMaxInList();

        sortList();
        System.out.println("List: після сортування");
        analyzeCondition(valueList, listSorted, threshold);

        findInList();
        locateMinMaxInList();

        // потім обробляємо масив
        System.out.println("Масив: до сортування");
        analyzeCondition(valueArray, arraySorted, threshold);
        findInArray();
        locateMinMaxInArray();

        performArraySorting();
        System.out.println("Масив: після сортування");
        analyzeCondition(valueArray, arraySorted, threshold);

        findInArray();
        locateMinMaxInArray();

        // зберігаємо до окремого файлу відсортований вміст LinkedList
        DataFileHandler.writeArrayToFile(valueList.toArray(new Integer[0]), BasicDataOperations.SORTED_FILE_PREFIX + ".list.data");
    }

    /**
     * Упорядковує масив Integer за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(valueArray);
        arraySorted = true;

        PerformanceTracker.displayOperationTime(timeStart, "сортування масиву Integer[]");
    }

    /**
     * Здійснює пошук конкретного значення в масиві.
     */
    void findInArray() {
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "пошук елемента '" + valueToSearch + "' в масивi Integer[], " + (arraySorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        int position = -1;
        if (arraySorted) {
            position = Arrays.binarySearch(valueArray, valueToSearch);
        } else {
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i].compareTo(valueToSearch) == 0) {
                    position = i;
                    break;
                }
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        if (position >= 0) {
            System.out.println("Елемент '" + valueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + valueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві.
     */
    void locateMinMaxInArray() {
        if (valueArray == null || valueArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "визначення мiнiмального i максимального значення в масивi Integer[], " + (arraySorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        Integer minValue = valueArray[0];
        Integer maxValue = valueArray[0];

        if (arraySorted) {
            // у відсортованому масиві граничні значення лежать на його кінцях
            maxValue = valueArray[valueArray.length - 1];
        } else {
            for (Integer currentValue : valueArray) {
                if (currentValue.compareTo(minValue) < 0) {
                    minValue = currentValue;
                }
                if (currentValue.compareTo(maxValue) > 0) {
                    maxValue = currentValue;
                }
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Шукає конкретне значення в колекції LinkedList.
     */
    void findInList() {
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "пошук елемента '" + valueToSearch + "' в LinkedList<Integer>, " + (listSorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        int position = -1;
        if (listSorted) {
            position = Collections.binarySearch(valueList, valueToSearch);
        } else {
            int index = 0;
            for (Integer value : valueList) {
                if (value.compareTo(valueToSearch) == 0) {
                    position = index;
                    break;
                }
                index++;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        if (position >= 0) {
            System.out.println("Елемент '" + valueToSearch + "' знайдено в LinkedList за позицією: " + position);
        } else {
            System.out.println("Елемент '" + valueToSearch + "' відсутній в LinkedList.");
        }
    }

    /**
     * Визначає найменше і найбільше значення в колекції LinkedList.
     */
    void locateMinMaxInList() {
        if (valueList == null || valueList.isEmpty()) {
            System.out.println("Колекція LinkedList є пустою або не ініціалізованою.");
            return;
        }

        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "визначення мiнiмального i максимального значення в LinkedList<Integer>, " + (listSorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        Integer minValue;
        Integer maxValue;

        if (listSorted) {
            // у відсортованому списку граничні значення лежать на його кінцях
            minValue = valueList.get(0);
            maxValue = valueList.get(valueList.size() - 1);
        } else {
            minValue = Collections.min(valueList);
            maxValue = Collections.max(valueList);
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        System.out.println("Найменше значення в List: " + minValue);
        System.out.println("Найбільше значення в List: " + maxValue);
    }

    /**
     * Упорядковує колекцію List з Integer за зростанням.
     * Відстежує та виводить час виконання операції сортування.
     */
    void sortList() {
        long timeStart = System.nanoTime();

        Collections.sort(valueList);
        listSorted = true;

        PerformanceTracker.displayOperationTime(timeStart, "сортування LinkedList<Integer>");
    }

    // BEGIN CONDITION
    /**
     * Вибірка значень, більших за поріг, у масиві.
     * sorted=true лише для даних, упорядкованих за зростанням.
     *
     * @return кількість знайдених входжень разом із повторами
     */
    public int analyzeCondition(Integer[] values, boolean sorted, int threshold) {
        String conditionDescription = "значення більші за " + threshold;
        String phase = sorted ? "після сортування" : "до сортування";
        System.out.println("Масив: вибірка даних " + phase + ". Умова: " + conditionDescription);
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "вибірка даних: масив Integer[], " + phase + ", умова: " + conditionDescription;
        long timeStart = System.nanoTime();
        Integer[] matches = new Integer[values.length];
        int count = 0;
        int inspected = 0;
        if (!sorted) {
            // Невпорядковані дані: перевіряємо кожне входження.
            for (Integer value : values) {
                inspected++;
                if (value > threshold) matches[count++] = value;
            }
        } else {
            // Упорядковані за зростанням дані: усі значення, більші за поріг, лежать у кінці масиву.
            // Йдемо від кінця до початку й зупиняємось на першому значенні, яке не перевищує порогу.
            for (int i = values.length - 1; i >= 0; i--) {
                inspected++;
                if (values[i] <= threshold) break;
                matches[count++] = values[i];
            }
        }
        PerformanceTracker.displayOperationTime(timeStart, operationName);
        // Друк результатів не входить у виміряний час пошуку.
        System.out.println("Переглянуто елементів: " + inspected);
        printMatches(matches, count, sorted);
        System.out.println("У масиві знайдено " + count + " входжень (повтори враховано). Умова: " + conditionDescription + ".");
        return count;
    }

    /**
     * Вибірка значень, більших за поріг, у списку.
     * sorted=true лише для даних, упорядкованих за зростанням.
     *
     * @return кількість знайдених входжень разом із повторами
     */
    public int analyzeCondition(List<Integer> values, boolean sorted, int threshold) {
        String conditionDescription = "значення більші за " + threshold;
        String phase = sorted ? "після сортування" : "до сортування";
        System.out.println("List: вибірка даних " + phase + ". Умова: " + conditionDescription);
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "вибірка даних: LinkedList<Integer>, " + phase + ", умова: " + conditionDescription;
        long timeStart = System.nanoTime();
        Integer[] matches = new Integer[values.size()];
        int count = 0;
        int inspected = 0;
        if (!sorted) {
            // Невпорядковані дані: перевіряємо кожне входження.
            for (Integer value : values) {
                inspected++;
                if (value > threshold) matches[count++] = value;
            }
        } else {
            // Упорядкований за зростанням список: значення, більші за поріг, лежать у кінці.
            // ListIterator із позиції size() рухається назад і не потребує get(i), який у LinkedList повільний.
            ListIterator<Integer> cursor = values.listIterator(values.size());
            while (cursor.hasPrevious()) {
                Integer value = cursor.previous();
                inspected++;
                // далі всі значення ще менші, тож решту списку можна не переглядати
                if (value <= threshold) break;
                matches[count++] = value;
            }
        }
        PerformanceTracker.displayOperationTime(timeStart, operationName);
        // Друк результатів не входить у виміряний час пошуку.
        System.out.println("Переглянуто елементів: " + inspected);
        printMatches(matches, count, sorted);
        System.out.println("У List знайдено " + count + " входжень (повтори враховано). Умова: " + conditionDescription + ".");
        return count;
    }

    /** Друкує знайдені значення; після сортування вони збираються від кінця, тому друкуються у зворотному порядку. */
    private static void printMatches(Integer[] matches, int count, boolean collectedFromEnd) {
        if (collectedFromEnd) {
            for (int i = count - 1; i >= 0; i--) System.out.println(matches[i]);
        } else {
            for (int i = 0; i < count; i++) System.out.println(matches[i]);
        }
    }
    // END CONDITION
}
