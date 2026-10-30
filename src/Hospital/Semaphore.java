package Hospital;

public class Semaphore {
    private int value;
    public Semaphore(int value) {
        this.value = value;
    }
    public synchronized void p() {
        value--;
        if (value < 0) {
            try{
                wait();
            }catch(InterruptedException e) {}
        }
    }
    public synchronized void v() {
        value++;
        if (value <=0) {
            notify();
        }
    }

    public synchronized boolean tryAcquire(int time) {
        long deadline = System.currentTimeMillis();
        long remaining = time;
        value--;
        while (value<0){
            if (remaining <= 0) {
                value ++;
                notify();
                return false;
            }
            try {
                wait(remaining);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            remaining = deadline - System.currentTimeMillis();
        }
        return true;
    }
}
