import java.util.concurrent.BlockingQueue;

public class UserGenerator implements Runnable {
    private final BlockingQueue<User> queue;

    private final int USER_NUMBER;
    private final long REQUEST_TIME;


    public UserGenerator(BlockingQueue<User> queue, int USER_NUMBER, long REQUEST_TIME) {
        this.queue = queue;
        this.USER_NUMBER = USER_NUMBER;
        this.REQUEST_TIME = REQUEST_TIME;
    }

    @Override
    public void run() {
        for (int id = 1; id <= USER_NUMBER; id++) {
            User user = new User(id);
            System.out.println(user + " waits on floor " + user.getStart());
            try {
                queue.put(user);
                Thread.sleep(REQUEST_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
