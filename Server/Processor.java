
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.InterruptedException;

public class Processor extends Thread
{
    // Queues hängen zusammen; jedes Command korrespondiert zu einem Absender.
    private BlockingQueue<Command> processingQueue;
    private BlockingQueue<ClientConnection> senderQueue;

    public Processor ()
    {
        processingQueue = new LinkedBlockingQueue<Command> ();
        senderQueue = new LinkedBlockingQueue<ClientConnection> ();
    }

    public void run ()
    {
        Command cmd = null; 
        ClientConnection sender = null;

        while (true)
        {
            try
            {
                cmd = processingQueue.take ();
                sender = senderQueue.take ();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace ();
            }

            if (sender.getNickname () != null)
            {
                Server.debugMsg ("Kommando gesendet:");                          
                Server.debugMsg (String.format ("   %s  -  %s",
                                 sender.getNickname (),
                                 cmd.toString ()));
            }
            else
            {
                //TODO: Error an Client zurückgeben weil noch nicht registriert.
            }
        }
    }

    public void enqueue (Command cmd, ClientConnection sender)
    {
        processingQueue.add (cmd);
        senderQueue.add (sender);
    }
}
