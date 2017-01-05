
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.InterruptedException;


/**
 * Verarbeitet eingehende {@link Command}s und generiert passende Ausgaben.
 */
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
                System.out.println (cmd.toString ());
                sender = senderQueue.take ();

                if (cmd instanceof ValidCommand)
                {
                    ValidCommand vcmd = (ValidCommand) cmd;
                    if (sender.getNickname () != null || vcmd.getType () == Command.Type.REG)
                    {
                        Server.debugMsg ("Kommando gesendet:");                          
                        Server.debugMsg (String.format ("   %s  -  %s",
                                         sender.getNickname (),
                                         cmd.toString ()));

                        processCommand (vcmd, sender);
                    }
                    else
                    {
                        //TODO: Error an Client zurückgeben weil noch nicht registriert.
                    }
                }

                sender.sendCmd (cmd);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace ();
            }

            cmd = null;
            sender = null;
        }
    }

                
    public void processCommand (ValidCommand cmd, ClientConnection sender)
    {
        switch (cmd.getType ())
        {
            case REG:
                if (sender.getNickname () != null)
                {
                    // TODO: rejecten
                }
                else
                {
                    sender.setNickname (cmd.getParams ()[0]);
                }       
                Server.debugMsg (String.format ("Client hat sich registriert:   %s",
                                                (cmd.getParams ()[0])));
                break;

            case MSG:
                // params[0] ist addressat, params[1] ist die Nachricht (s. Protokoll)
                // wenn params[0] == "*": nachricht an alle außer absender weiterleiten
                // ansonsten: Nachricht an Connection mit nickname == params[0] weiterleiten
                // Falls nicht vorhanden: Error zurückgeben (TODO)
                
                Server.debugMsg (String.format ("Client hat Nachricht gesendet:"));
                Server.debugMsg (String.format ("    %s - %s",
                                                cmd.getParams ()[0],
                                                cmd.getParams ()[1]));
                
                if (cmd.getParams()[0].equals ("*"))
                {
                    for (ClientConnection conn : Server.getServer ().getConnectionManager ())
                    {
                        if (!conn.getNickname ().equals (sender.getNickname ()))
                        {
                            conn.sendCmd (cmd);
                        }
                    }
                }
                else
                {
                    ClientConnection addr = Server.getServer ().getConnectionManager().get(cmd.getParams()[0]);
                    if (addr != null)
                    {
                        addr.sendCmd (cmd);
                    }
                    else
                    {
                        // TODO: Error zurückgeben weil Nutzer nicht vorhanden.
                    }
                }

                break; 
                        
        }
    }

    /**
     * Füge ein {@link Command} in die Verarbeitungsschlange ein.
     *
     * @param cmd Das zu verarbeitende Command
     * @param sender Der Absender des Commands
     */
    public void enqueue (Command cmd, ClientConnection sender)
    {
        processingQueue.add (cmd);
        senderQueue.add (sender);
    }
}
