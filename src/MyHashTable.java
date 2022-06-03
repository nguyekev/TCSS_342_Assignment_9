public class MyHashTable<Key extends Comparable<Key>, Value> {
    private Integer capacity;
    private Key[] keysBuckets;
    private Value[] valuesBuckets;
    private Integer size;
    public MyArrayList<Key> keys = new MyArrayList<>();
    public Integer comparisons = 0;
    public Integer maxProbe = 0;

    public MyHashTable(Integer capacity) {
        this.capacity = capacity;
        keysBuckets = (Key[]) new Comparable[capacity];
        valuesBuckets = (Value[]) new Object[capacity];
        size = 0;
    }
    private Integer hash(Key key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    public Value get(Key key) {
        int index = hash(key);
        while (keysBuckets[index] != null) {
            if (keysBuckets[index].compareTo(key)==0) {
                return valuesBuckets[index];
            }
            index = (index + 1) % capacity;
        }
        return null;
    }

    public void put(Key key, Value value) {
        int probes = 1;
        int index = hash(key);
        boolean flag = false;
        while (keysBuckets[index] != null) {
            if (keysBuckets[index].compareTo(key) == 0) {
                flag = true;
                break;
            }
            comparisons++;
            index = (index + 1) % capacity;
            probes++;
        }
        if (!flag) {
            comparisons++;
            keysBuckets[index] = key;
            keys.insert(key, keys.size());
            size++;
        }
        maxProbe = Math.max(maxProbe, probes);
        valuesBuckets[index] = value;
    }

    public Integer size() {
        return size;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            if (keysBuckets[i] != null) {
                sb.append(keysBuckets[i]).append(": ").append(valuesBuckets[i]).append("\n");
            }
        }
        return sb.toString();
    }
}
