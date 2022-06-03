public class MyPriorityQueue <Type extends Comparable<Type>> {
    private MyArrayList<Type> heap;
    public MyPriorityQueue() {
        heap = new MyArrayList<Type>();
    }
    public void insert(Type item) {
        int index = heap.size();
        heap.insert(item, index);
        bubbleUp();
    }
    public void bubbleUp(){
        int index = heap.size()-1;
        while(index>0 && heap.get(parent(index)).compareTo(heap.get(index))>0){
            swap(parent(index),index);
            index = parent(index);
        }
    }
    private void swap(int i, int j){
        Type temp = heap.get(i);
        heap.set(i,heap.get(j));
        heap.set(j,temp);
    }

    public Type removeMin() {
        if (heap.size() != 0) {
            Type min = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            sinkDown();
            return min;
        }
        return null;
    }
    public void sinkDown(){
        int index = 0;
        while(left(index)<heap.size()){
            int smallerChild = left(index);
            if(right(index)<heap.size() && heap.get(right(index)).compareTo(heap.get(left(index)))<0){
                smallerChild = right(index);
            }
            if(heap.get(index).compareTo(heap.get(smallerChild))>0){
                swap(index,smallerChild);
                index = smallerChild;
            }
            else{
                break;
            }
        }
    }

    public boolean isEmpty() {
        return heap.isEmpty();
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
        StringBuilder array = new StringBuilder();
        array.append("[");
        for (int i = 0; i < heap.size(); i++) {
            array.append(heap.get(i));
            if (i != heap.size() - 1) {
                array.append(", ");
            }
        }
        array.append("]");
        return array.toString();
    }
}