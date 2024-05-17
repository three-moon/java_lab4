import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private final static int ELEVATOR_NUMBER = 3;
    private final static int FLOOR_NUMBER = 15;
    private final static int USER_NUMBER = 1;
    private final static double FIRST_FLOOR_CHANCE = 0.5;
    private final static long REQUEST_TIME = 7000;
    private final static long FLOOR_TIME = 1000;
    private final static long STOP_TIME = 1000;

    public static void main(String[] args) {
        // Executor service set up
        ExecutorService executorService = Executors.newFixedThreadPool(ELEVATOR_NUMBER + 2);

        // User set up
        BlockingQueue<User> users = new LinkedBlockingQueue<>();
        User.setFirstFloorChance(FIRST_FLOOR_CHANCE);
        User.setFloorNumber(FLOOR_NUMBER);

        // Elevator set up
        List<Elevator> elevators = new ArrayList<>();
        Elevator.setFloorTime(FLOOR_TIME);
        Elevator.setStopTime(STOP_TIME);

        for (int i = 1; i <= ELEVATOR_NUMBER; i++) {
            Elevator elevator = new Elevator("Elevator_" + i);
            executorService.execute(elevator);
            elevators.add(elevator);
        }

        // User generator and elevator manager set up
        executorService.execute(new UserGenerator(users, USER_NUMBER, REQUEST_TIME));
        executorService.execute(new ElevatorManager(elevators, users));
    }
}
