import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionalVariables {
    private final Buffer buffer = new Buffer();
    private final Lock lock = new ReentrantLock();
    private final Condition fill = lock.newCondition();
    private final Condition empty = lock.newCondition();

    public static class Buffer {
        int[] buffer = new int[100];
        int fill = 0;
        int use = 0;
        int count = 0;
        void put(int value){
            buffer[fill] = value;
            fill = (fill + 1) % 100;
            count++;
        }
        int get(){
            int temp = buffer[use];
            use = (use + 1) % 100;
            count--;
            return  temp;
        }
    }
    public void producer(int count) throws InterruptedException {
        for(int i = 0; i < count; i++){
            lock.lock();
            while (buffer.count == 100){
                empty.await();
            }
            buffer.put(i);
            System.out.println(Thread.currentThread().getName() + "put: " + i);
            fill.signal();
            lock.unlock();
        }
    }

    public void consumer(int count) throws InterruptedException {
        for(int i = 0; i < count; i++){
            lock.lock();
            while (buffer.count == 0) {
                fill.await();
            }
            System.out.println(Thread.currentThread().getName() + "get: " + buffer.get());
            empty.signal();
            lock.unlock();
        }
    }
}
