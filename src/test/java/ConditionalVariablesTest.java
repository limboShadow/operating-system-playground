import org.junit.jupiter.api.Test;

public class ConditionalVariablesTest {

    @Test
    public void test() throws InterruptedException {
        ConditionalVariables conditionalVariables = new ConditionalVariables();
        Runnable consumer = () -> {
            try {
                conditionalVariables.consumer(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable producer = () -> {
            try {
                conditionalVariables.producer(30000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };

        //多消费者
        Thread consumer1 = new Thread(consumer);
        consumer1.start();
        Thread consumer2 = new Thread(consumer);
        consumer2.start();
        Thread consumer3 = new Thread(consumer);
        consumer3.start();
        Thread thread1 = new Thread(producer);
        thread1.start();
        consumer1.join();
        consumer2.join();
        consumer3.join();
        thread1.join();
    }
}
