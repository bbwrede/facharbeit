

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Processor extends Thread
{
    private BlockingQueue<Command> processingQueue;

    public Processor ()
    {
        processingQueue = new LinkedBlockingQueue<Command> ();
    }

    public void run ()
    {
        Command cmd;

        while (true)
        {
            cmd = processingQueue.take ();   

            System.out.println (cmd.toString ());
        }
    }

    public void enqueue (Command cmd)
    {
        processingQueue.add (cmd);
    }
}
