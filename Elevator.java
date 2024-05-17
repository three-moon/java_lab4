import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Elevator implements Runnable {
    public static void setFloorTime(long floorTime) {
        FLOOR_TIME = floorTime;
    }

    public static void setStopTime(long stopTime) {
        STOP_TIME = stopTime;
    }

    private static long FLOOR_TIME = 1000;
    private static long STOP_TIME = 1000;

    private final Object lock = new Object();
    private final String name;

    private boolean inProcess = false;
    private int currentFloor = 1;

    private int nextFloor;
    private User user;


    public Elevator(String name) {
        this.name = name;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDelta(int a, int b) {
        if (a < b) {
            return 1;
        } else {
            return -1;
        }
    }

    public boolean isWaiting(){
        return user == null;
    }

    public void setUser(User user) {
        synchronized (lock) {
            System.out.println(name + " go to " + user + " on floor " + user.getStart());
            this.user = user;
            lock.notify();
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
                if (user == null) {
                    synchronized (lock) {
                        lock.wait();
                        nextFloor = user.getStart();
                    }
                }
                while (currentFloor != nextFloor) {
                    currentFloor += getDelta(currentFloor, nextFloor);
                    Thread.sleep(FLOOR_TIME);
                    if (inProcess){
                        System.out.println(name + " with " + user + " is on floor " + currentFloor);
                    } else {
                        System.out.println(name + " is on floor " + currentFloor);
                    }
                }
                System.out.println(name + " open doors on floor " + currentFloor);
                Thread.sleep(STOP_TIME);
                if (!inProcess){
                    System.out.println(user + " get in the " + name);
                    nextFloor = user.getEnd();
                    System.out.println(user + " wants to floor " + nextFloor);
                    inProcess = true;
                } else {
                    System.out.println(user + " get out the " + name);
                    user = null;
                    inProcess = false;
                }
                System.out.println(name + " close doors on floor " + currentFloor);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}