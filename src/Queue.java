public class Queue {
    private int maxSize;
    private Object[] queueArray;
    private int front;
    private int rear;
    private int nItems;
    
    // Constructor
    public Queue(int size) {
        this.maxSize = size;
        this.queueArray = new Object[maxSize];
        this.front = 0;
        this.rear = -1;
        this.nItems = 0;
    }
    
    // Add an item to the queue
    public void enqueue(Object item) {
        if (isFull()) {
            // Queue is full
            System.out.println("Kuyruk dolu, ekleme yapılamıyor!");
            return;
        }
        // Circular queue implementation
        if (rear == maxSize - 1) {
            rear = -1;
        }
        queueArray[++rear] = item;
        nItems++;
    }
    
    // Remove an item from the queue
    public Object dequeue() {
        if (isEmpty()) {
            // Queue is empty
            System.out.println("Kuyruk boş, çıkarma yapılamıyor!");
            return null;
        }
        Object temp = queueArray[front];
        queueArray[front] = null; // Help garbage collection
        front++;
        if (front == maxSize) {
            front = 0; // Wrap around
        }
        nItems--;
        return temp;
    }
    
    // Peek at the first item without removing it
    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        return queueArray[front];
    }
    
    // Check if queue is full
    public boolean isFull() {
        return (nItems == maxSize);
    }
    
    // Check if queue is empty
    public boolean isEmpty() {
        return (nItems == 0);
    }
    
    // Return the size of the queue
    public int size() {
        return nItems;
    }
} 