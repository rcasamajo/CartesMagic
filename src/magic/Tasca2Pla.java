package magic;

import javafx.concurrent.Task;

public class Tasca2Pla extends Task<String>
{
    @Override
    protected String call() throws Exception {
        //Block the thread for a short time, but be sure
        //to check the InterruptedException for cancellation
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interrupted) {
            if (isCancelled()) {
                updateMessage("Cancelled");
            }
        }
        return "Done";
    }
}
