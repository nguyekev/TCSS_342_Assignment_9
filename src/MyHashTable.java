public class MyHashTable<Key extends Comparable<Key>, Value> {
    private Integer capacity;
    private Key[] keysBuckets;
    private Value[] valuesBuckets;
    private Integer size = 0;
    public MyArrayList<Key> keys = new MyArrayList<>();
    public Integer comparisons = 0;
    public Integer maxProbe = 0;

    public MyHashTable() {
        this.capacity = 32768;
        keysBuckets = (Key[]) new Comparable[capacity];
        valuesBuckets = (Value[]) new Object[capacity];
    }
    private Integer hash(Key key) {
        return key.hashCode() % capacity;
    }
    public Value get(Key key) {
        int index = hash(key);
        int probe = 0;
        while (keysBuckets[index] != null) {
            comparisons++;
            if (keysBuckets[index].equals(key)) {
                return valuesBuckets[index];
            }
            index = (index + 1) % capacity;
            probe++;
        }
        return null;
    }

    public void put(Key key, Value value) {
        int index = hash(key);
        int probe = 1;
        while (keysBuckets[index] != null) {
            if (keysBuckets[index].equals(key)) {
                valuesBuckets[index] = value;
                return;
            }
            index = (index + 1) % capacity;
            probe++;
        }
        keysBuckets[index] = key;
        valuesBuckets[index] = value;
        size++;
        keys.insert(key,1);
        comparisons += probe;
        if (probe > maxProbe) {
            maxProbe = probe;
        }
    }
    public Integer size() {
        return size;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < capacity; i++) {
            if (keysBuckets[i] != null) {
                sb.append(keysBuckets[i] + ": " + valuesBuckets[i] + ", ");
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return sb.toString();
    }
}
