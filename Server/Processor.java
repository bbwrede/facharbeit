

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.InterruptedException;

public class Processor extends Thread
{
    private BlockingQueue<Command> processingQueue;

    public Processor ()
    {
        processingQueue = new LinkedBlockingQueue<Command> ();
    }

    public void run ()
    {
        Command cmd = new ValidCommand ();

        while (true)
        {
            try
            {
                cmd = processingQueue.take ();   
            }
            catch (InterruptedException e)
            {
                e.printStackTrace ();
            }

            System.out.println (cmd.toString ());
        }
    }

    public void enqueue (Command cmd)
    {
        processingQueue.add (cmd);
    }
}
