import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class User {
    public static void setFirstFloorChance(double firstFloorChance) {
        FIRST_FLOOR_CHANCE = firstFloorChance;
    }

    public static void setFloorNumber(int floorNumber) {
        FLOOR_NUMBER = floorNumber;
    }

    private static double FIRST_FLOOR_CHANCE = 0.5;
    private static int FLOOR_NUMBER = 10;

    private final long id;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private Integer start = null;

    private Integer end = null;

    public User(int id){
        this.id = id;
    }

    public int getStart() {
        if (start == null) {
            if (ThreadLocalRandom.current().nextDouble() < FIRST_FLOOR_CHANCE) {
                start = 1;
            } else {
                start = random.nextInt(2, FLOOR_NUMBER);
            }
        }
        return start;
    }

    public int getEnd() {
        if (end == null) {
            if (start == 1) {
                end = random.nextInt(2, FLOOR_NUMBER);
            } else {
                end = 1;
            }
        }
        return end;
    }

    @Override
    public String toString(){
        return "User_" + id;
    }
}