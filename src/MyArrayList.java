public class MyArrayList<Type extends Comparable<Type>> {
    private int capacity = 16;
    private Type[] list = (Type[]) new Comparable[capacity];
    private int size = 0;
    private long comparisons = 0;

    public void setList(Type[] list) {
        this.list = list;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void insert(Type item, int index) {
        if (index <= size && index >= 0) {
            setSize(size = size + 1);
            if (size == capacity) {
                resize();
            }
            for (int i = list.length - 1; i > index; i--) {
                list[i] = list[i - 1];
            }
            list[index] = item;
        }
    }

    private void resize() {
        setCapacity(capacity = capacity * 2);
        Type[] newList = (Type[]) new Comparable[getCapacity()];
        for (int i = 0, k = 0; i < list.length; i++) {
            newList[k++] = list[i];
        }
        setList(newList);
    }

    public void remove(int index) {
        if (index < capacity && index > -1 * capacity) {
            setSize(size = size - 1);
            Type[] tempArray = (Type[]) new Comparable[getCapacity()];
            for (int i = 0, k = 0; i < list.length; i++) {
                if (i == index) {
                    continue;
                }
                tempArray[k++] = list[i];
            }
            setList(tempArray);
        }
    }

    public boolean contains(Type item) {
        comparisons++;
        for (int i = 0; i <= list.length; i++) {
            if (list[i] == null) {
                comparisons++;
                if (list[i].compareTo(item) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public int indexOf(Type item) {
        for (int i = 0; i <= size; i++) {
            if (list[i] == null) {
                if (list[i].compareTo(item) == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public long getComparisons() {
        return comparisons;
    }

    public Type get(int index) {
        if (index < capacity && index >= 0) {
            final Type t = list[index];
            return t;
        }
        return null;
    }

    public void set(int index, Type item) {
        if (index < capacity && index >= 0) {
            for (int i = 0; i <= capacity; i++) {
                if (index == i) {
                    list[index] = item;
                }
            }
        }
    }

    public Boolean isEmpty() {
        return size == 0;
    }

    public void sort() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (list[i].compareTo( list[j]) < 0) {
                    Type t1 =  list[i];
                    list[i] = list[j];
                    list[j] = t1;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder array = new StringBuilder();
        array.append("[");
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                if(i == 0){
                    array.append(list[0]);
                } else {
                    array.append(", " + list[i]);
                }
            }
        }
        array.append("]");
        return array.toString();
    }
}