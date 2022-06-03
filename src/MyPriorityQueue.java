public class MyPriorityQueue <Type extends Comparable<Type>> {
    private MyArrayList<Type> heap;
    public MyPriorityQueue() {
        heap = new MyArrayList<Type>();
    }
    public void insert(Type item) {
        heap.insert(item, heap.size());
        bubbleUp();
    }
    public void bubbleUp(){
        int index = heap.size() - 1;
        while (index > 0) {
            int parent = parent(index);
            if (heap.get(parent).compareTo(heap.get(index)) > 0) {
                Type temp = heap.get(parent);
                heap.set(parent, heap.get(index));
                heap.set(index, temp);
                index = parent;
            } else {
                break;
            }
        }
    }

    public Type removeMin() {
        Type item = heap.get(0);
        if (item == null) return null;
        heap.set(0, heap.get(size() - 1));
        heap.remove(size() - 1);
        sinkDown();
        return item;
    }
    public void sinkDown(){
        if (heap.size() == 0) {
            return;
        }
        int index = 0;
        while (index < heap.size()) {
            int left = left(index);
            int right = right(index);
            int smallest = index;
            if (left < heap.size() && heap.get(left).compareTo(heap.get(index)) < 0) {
                smallest = left;
            }
            if (right < heap.size() && heap.get(right).compareTo(heap.get(smallest)) < 0) {
                smallest = right;
            }
            if (smallest != index) {
                Type temp = heap.get(index);
                heap.set(index, heap.get(smallest));
                heap.set(smallest, temp);
                index = smallest;
            } else {
                break;
            }
        }
    }

    public boolean isEmpty() {
        if (heap.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
    public int size() {
        return heap.size();
    }
    public Type min() {
        return heap.get(0);
    }
    public int parent(int index) {
        return (index-1)/2;
    }
    public int left(int index) {
        return 2*index+1;
    }
    public int right(int index) {
        return 2*index+2;
    }
    @Override
    public String toString() {
        return heap.toString();
    }
}