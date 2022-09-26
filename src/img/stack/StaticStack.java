package img.stack;

public class StaticStack<T> {
    protected int top;
    protected T[] stack;
    public StaticStack(int capacity){
        if (capacity < 1){
            throw new IllegalArgumentException("Capacity must be higher than 0!");
        }
        stack = (T[]) new Object[capacity];
        top = -1;
    }

    public void add(T element){
        if (top + 1 >= stack.length){
            throw new IllegalStateException("Full stack (Overflow)");
        }
        top++;
        stack[top] = element;
    }

    public T remove(){
        if (top >= 0){
            top--;

            T value = stack[top+1];
            stack[top+1] = null;

            return value;
        }

        throw new IllegalStateException("Empty stack (Underflow)");
    }

    public void show(){
        if (top >= 0)
            System.out.println(stack[top]);
        else
            System.out.println("No element in stack");
    }

    public void clear(){
        while(!isEmpty()){
            remove();
        }
    }

    public int getSize(){
        return (top + 1);
    }

    public boolean isEmpty(){
        return (top < 0);
    }

    public int getCapacity(){
        return stack.length;
    }
}
