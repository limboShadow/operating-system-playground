public class Semaphore {

    public static class ReadWriteLock {
        private final java.util.concurrent.Semaphore lock = new java.util.concurrent.Semaphore(1);
        private final java.util.concurrent.Semaphore writeLock = new java.util.concurrent.Semaphore(1);
        private int reader = 0;

        public void acquireReadLock() throws InterruptedException {
            lock.acquire();
            reader++;
            if (reader == 1){
                writeLock.acquire();
            }
            lock.release();
        }

        public void releaseReadLock() throws InterruptedException {
            lock.acquire();
            reader--;
            if (reader == 0){
                writeLock.release();
            }
            lock.release();
        }

        public void acquireWriteLock() throws InterruptedException {
            writeLock.acquire();
        }

        public void releaseWriteLock() {
            writeLock.release();
        }
    }
}
