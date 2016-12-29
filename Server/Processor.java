
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
            System.out.println ("Hier.");

            try
            {
                cmd = processingQueue.take ();
                sender = senderQueue.take ();

                if (cmd instanceof ValidCommand)
                {
                    ValidCommand vcmd = (ValidCommand) cmd;

                    switch (vcmd.getType ())
                    {
                        case REG:
                            if (sender.getNickname () != null)
                            {
                                // TODO: rejecten
                            }
                            else
                            {
                                sender.setNickname (vcmd.getParams ()[0]);
                            }       
                            Server.debugMsg (String.format ("Client hat sich registriert:   %s",
                                                            (vcmd.getParams ()[0]));
                            break;

                        case MSG:
                            // params[0] ist addressat, params[1] ist die Nachricht
                            if 
                            
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

                cmd = new ValidCommand ();
                cmd.setType (Command.Type.GETSTAT);
                sender.sendMsg (cmd);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace ();
            }

            cmd = null;
            sender = null;
        }
    }

    public void enqueue (Command cmd, ClientConnection sender)
    {
        processingQueue.add (cmd);
        senderQueue.add (sender);
    }
}
