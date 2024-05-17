import java.util.List;
import java.util.concurrent.BlockingQueue;


public class ElevatorManager implements Runnable {
    private final List<Elevator> elevators;
    private final BlockingQueue<User> queue;

    public ElevatorManager(List<Elevator> elevators, BlockingQueue<User> queue) {
        this.elevators = elevators;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            User user;
            try {
                user = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Elevator bestElevator = getBestElevator(user.getStart());
            bestElevator.setUser(user);
        }
    }

    private Elevator getBestElevator(long floor) {
        long bestDistance = Integer.MAX_VALUE;
        Elevator bestElevator = null;
        while (bestElevator == null) {
            for (Elevator elevator : elevators) {
                if (elevator.isWaiting()) {
                    if (Math.abs(floor - elevator.getCurrentFloor()) < bestDistance) {
                        bestDistance = Math.abs(floor - elevator.getCurrentFloor());
                        bestElevator = elevator;
                    }
                }
            }
        }
        return bestElevator;
    }
}


