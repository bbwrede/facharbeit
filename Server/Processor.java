
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
                    // Wenn nicht registriert und empfangenes Kommando nicht %REG$ ... %:
                    else
                    {
                        ValidCommand rsp = new ValidCommand ();
                        rsp.setType (Command.Type.RSP);
                        rsp.RSP_setCode (Command.RspCode.NOTREGISTERED);
                        sender.sendCmd (rsp);
                    }
                }
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
                ValidCommand rsp = new ValidCommand ();
                rsp.setType (Command.Type.RSP);
                rsp.setUUID (cmd.getUUID ());
                String nick = cmd.REG_getNick();

                // Wenn der Absender sich bereits registriert hat:
                if (sender.getNickname () != null)
                {
                    rsp.setParams (new String[] {Command.RspCode.DENIED.toString()});
                    Server.debugMsg (String.format (
                                "Client versucht sich mit Nickname zu registrieren:    %s",
                                nick
                        ));
                    Server.debugMsg ("Zurückgewiesen. Grund: Client hat bereits einen Nicknamen.");
                }
                // Wenn es diesen Nicknamen schon gibt:
                else if (Server.getServer().getConnectionManager().isNicknameTaken(nick))
                {
                    rsp.RSP_setCode(Command.RspCode.NICKALREADYTAKEN);
                    Server.debugMsg (String.format (
                                "Client versucht sich mit Nickname zu registrieren:    %s",
                                nick
                        ));
                    Server.debugMsg ("Zurückgewiesen. Grund: Name bereits vergeben.");
                }
                // Wenn alles ok ist:
                else
                {
                    rsp.RSP_setCode(Command.RspCode.OK);
                    sender.setNickname (nick);
                    Server.consoleMsg (String.format (
                                "Client hat sich registriert:   %s",
                                nick
                        ));
                }
                sender.sendCmd (rsp);
                break;

            case MSG:
                // params[0] ist Adressat, params[1] ist die Nachricht (s. Protokoll)
                // wenn params[0] == "*": nachricht an alle außer absender weiterleiten
                // ansonsten: Nachricht an Connection mit nickname == params[0] weiterleiten
                // Falls nicht vorhanden: Error zurückgeben (TODO)
                
                String adressat = cmd.getParams()[0];
                String nachricht = cmd.getParams()[1];

                Server.debugMsg (String.format ("Client hat Nachricht gesendet:"));
                Server.debugMsg (String.format (
                            "    %s - %s",
                            adressat,
                            nachricht
                    ));

                boolean nicksExist = true;
                
                if (adressat.equals ("*"))
                {
                    // An alle Clients außer den Absender verschicken
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
                        nicksExist = false;
                    }
                }

                // Response erstellen
                ValidCommand rsp = new ValidCommand();
                rsp.setUUID (cmd.getUUID ());
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
