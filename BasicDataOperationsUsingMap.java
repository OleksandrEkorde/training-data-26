import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class BasicDataOperationsUsingMap {
    private static final Pine KEY_TO_SEARCH_AND_DELETE = new Pine("Сосна звичайна", 31.2);
    private static final Pine KEY_TO_ADD = new Pine("Сосна алеппська", 12.1);

    private static final String VALUE_TO_SEARCH_AND_DELETE = "Черкаське";
    private static final String VALUE_TO_ADD = "Тальнівське";

    private static final Comparator<Map.Entry<Pine, String>> CUSTOM_COMPARATOR = Map.Entry.comparingByValue();

    private HashMap<Pine, String> hashMap;
    private TreeMap<Pine, String> treeMap;

    public record Pine(String species, Double height) implements Comparable<Pine> {
        public Pine {
            Objects.requireNonNull(species, "species не може бути null");
            Objects.requireNonNull(height, "height не може бути null");
        }

        @Override
        public int compareTo(Pine other) {
            int speciesComparison = this.species.compareTo(other.species);
            if (speciesComparison != 0) {
                return speciesComparison;
            }

            return other.height.compareTo(this.height);
        }

        @Override
        public String toString() {
            return "Pine{species='" + species + "', height=" + height + ", hashCode=" + hashCode() + "}";
        }
    }

    BasicDataOperationsUsingMap(HashMap<Pine, String> hashMap, TreeMap<Pine, String> treeMap) {
        this.hashMap = hashMap;
        this.treeMap = treeMap;
    }

    public void executeDataOperations() {
        System.out.println("========= Операції з HashMap =========");
        System.out.println("Початковий розмір HashMap: " + hashMap.size());

        findByKeyInHashMap();
        findByValueInHashMap();
        findMinMaxKeyInHashMap();

        printHashMap();
        sortHashMap();
        printHashMap();

        findByKeyInHashMap();
        findByValueInHashMap();
        findMinMaxKeyInHashMap();

        addEntryToHashMap();

        removeByKeyFromHashMap();
        removeByValueFromHashMap();

        System.out.println("Кінцевий розмір HashMap: " + hashMap.size());

        System.out.println("\n\n========= Операції з TreeMap =========");
        System.out.println("Початковий розмір TreeMap: " + treeMap.size());

        findByKeyInTreeMap();
        findByValueInTreeMap();
        findMinMaxKeyInTreeMap();

        printTreeMap();

        addEntryToTreeMap();

        removeByKeyFromTreeMap();
        removeByValueFromTreeMap();

        System.out.println("Кінцевий розмір TreeMap: " + treeMap.size());
    }

    private void printHashMap() {
        System.out.println("\n=== Пари ключ-значення в HashMap ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Pine, String> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в HashMap");
    }

    private void sortHashMap() {
        long timeStart = System.nanoTime();

        List<Pine> sortedKeys = new ArrayList<>(hashMap.keySet());
        Collections.sort(sortedKeys);

        HashMap<Pine, String> sortedHashMap = new HashMap<>();
        for (Pine key : sortedKeys) {
            sortedHashMap.put(key, hashMap.get(key));
        }

        hashMap = sortedHashMap;

        PerformanceTracker.displayOperationTime(timeStart, "сортування HashMap за ключами");
    }

    void findByKeyInHashMap() {
        long timeStart = System.nanoTime();

        boolean found = hashMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в HashMap");

        if (found) {
            String value = hashMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Лісництво: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    void findByValueInHashMap() {
        long timeStart = System.nanoTime();

        List<Map.Entry<Pine, String>> entries = new ArrayList<>(hashMap.entrySet());
        Collections.sort(entries, CUSTOM_COMPARATOR);

        Map.Entry<Pine, String> searchEntry = new AbstractMap.SimpleEntry<>(null, VALUE_TO_SEARCH_AND_DELETE);

        int position = Collections.binarySearch(entries, searchEntry, CUSTOM_COMPARATOR);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в HashMap");

        if (position >= 0) {
            Map.Entry<Pine, String> foundEntry = entries.get(position);
            System.out.println("Лісництво '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Сосна: " + foundEntry.getKey());
        } else {
            System.out.println("Лісництво '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутнє в HashMap.");
        }
    }

    void findMinMaxKeyInHashMap() {
        if (hashMap.isEmpty()) {
            System.out.println("HashMap порожня: найменшого й найбільшого ключа немає.");
            return;
        }

        long timeStart = System.nanoTime();

        Pine minKey = Collections.min(hashMap.keySet());
        Pine maxKey = Collections.max(hashMap.keySet());

        PerformanceTracker.displayOperationTime(timeStart, "визначення найменшого й найбільшого ключа в HashMap");

        System.out.println("Найменший ключ: " + minKey + ", найбільший ключ: " + maxKey);
    }

    void addEntryToHashMap() {
        long timeStart = System.nanoTime();

        hashMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до HashMap");

        System.out.println("Додано новий запис: сосна='" + KEY_TO_ADD + "', лісництво='" + VALUE_TO_ADD + "'");
    }

    void removeByKeyFromHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = hashMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з HashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Лісництво було: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    void removeByValueFromHashMap() {
        long timeStart = System.nanoTime();

        List<Pine> keysToRemove = new ArrayList<>();
        for (Map.Entry<Pine, String> entry : hashMap.entrySet()) {
            if (VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue())) {
                keysToRemove.add(entry.getKey());
            }
        }

        for (Pine key : keysToRemove) {
            hashMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з HashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з лісництвом '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    private void printTreeMap() {
        System.out.println("\n=== Пари ключ-значення в TreeMap ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Pine, String> entry : treeMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в TreeMap");
    }

    void findByKeyInTreeMap() {
        long timeStart = System.nanoTime();

        boolean found = treeMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в TreeMap");

        if (found) {
            String value = treeMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Лісництво: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    void findByValueInTreeMap() {
        long timeStart = System.nanoTime();

        List<Map.Entry<Pine, String>> entries = new ArrayList<>(treeMap.entrySet());
        Collections.sort(entries, CUSTOM_COMPARATOR);

        Map.Entry<Pine, String> searchEntry = new AbstractMap.SimpleEntry<>(null, VALUE_TO_SEARCH_AND_DELETE);

        int position = Collections.binarySearch(entries, searchEntry, CUSTOM_COMPARATOR);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в TreeMap");

        if (position >= 0) {
            Map.Entry<Pine, String> foundEntry = entries.get(position);
            System.out.println("Лісництво '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Сосна: " + foundEntry.getKey());
        } else {
            System.out.println("Лісництво '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутнє в TreeMap.");
        }
    }

    void findMinMaxKeyInTreeMap() {
        if (treeMap.isEmpty()) {
            System.out.println("TreeMap порожня: найменшого й найбільшого ключа немає.");
            return;
        }

        long timeStart = System.nanoTime();

        Pine minKey = treeMap.firstEntry().getKey();
        Pine maxKey = treeMap.lastEntry().getKey();

        PerformanceTracker.displayOperationTime(timeStart, "визначення найменшого й найбільшого ключа в TreeMap");

        System.out.println("Найменший ключ: " + minKey + ", найбільший ключ: " + maxKey);
    }

    void addEntryToTreeMap() {
        long timeStart = System.nanoTime();

        treeMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до TreeMap");

        System.out.println("Додано новий запис: сосна='" + KEY_TO_ADD + "', лісництво='" + VALUE_TO_ADD + "'");
    }

    void removeByKeyFromTreeMap() {
        long timeStart = System.nanoTime();

        String removedValue = treeMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з TreeMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Лісництво було: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    void removeByValueFromTreeMap() {
        long timeStart = System.nanoTime();

        List<Pine> keysToRemove = new ArrayList<>();
        for (Map.Entry<Pine, String> entry : treeMap.entrySet()) {
            if (VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue())) {
                keysToRemove.add(entry.getKey());
            }
        }

        for (Pine key : keysToRemove) {
            treeMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з TreeMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з лісництвом '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    public static void main(String[] args) {
        Map<Pine, String> pines = Map.of(
            new Pine("Сосна звичайна", 25.4), "Черкаське",
            new Pine("Сосна кримська", 18.0), "Смілянське",
            new Pine("Сосна чорна", 21.7), "Чигиринське",
            new Pine("Сосна звичайна", 31.2), "Канівське",
            new Pine("Сосна гірська", 3.5), "Черкаське",
            new Pine("Сосна кедрова", 15.8), "Золотоніське",
            new Pine("Сосна кримська", 22.3), "Уманське",
            new Pine("Сосна жорстка", 19.6), "Корсунь-Шевченківське",
            new Pine("Сосна звичайна", 28.9), "Звенигородське",
            new Pine("Сосна румелійська", 16.4), "Шполянське"
        );

        HashMap<Pine, String> hashMap = new HashMap<>(pines);
        TreeMap<Pine, String> treeMap = new TreeMap<>(pines);

        BasicDataOperationsUsingMap operations = new BasicDataOperationsUsingMap(hashMap, treeMap);
        operations.executeDataOperations();
    }
}
