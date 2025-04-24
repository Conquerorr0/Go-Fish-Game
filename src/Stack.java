public class Stack {
    private int maxSize;
    private Object[] stackArray;
    private int top;
    
    // Constructor
    public Stack(int size) {
        this.maxSize = size;
        this.stackArray = new Object[maxSize];
        this.top = -1;
    }
    
    // Push an item to the stack
    public void push(Object item) {
        if (isFull()) {
            // Stack overflow
            System.out.println("Stack dolu, ekleme yapılamıyor!");
            return;
        }
        stackArray[++top] = item;
    }
    
    // Pop an item from the stack
    public Object pop() {
        if (isEmpty()) {
            // Stack underflow
            System.out.println("Stack boş, çıkarma yapılamıyor!");
            return null;
        }
        return stackArray[top--];
    }
    
    // Peek at the top item without removing it
    public Object peek() {
        if (isEmpty()) {
            // Stack is empty
            return null;
        }
        return stackArray[top];
    }
    
    // Check if stack is full
    public boolean isFull() {
        return (top == maxSize - 1);
    }
    
    // Check if stack is empty
    public boolean isEmpty() {
        return (top == -1);
    }
    
    // Return the size of the stack
    public int size() {
        return top + 1;
    }
} 