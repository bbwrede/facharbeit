
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.lang.Exception.*;
import java.time.LocalTime;

class Server
{
    private static Server srv;
    private static boolean debug;
    
    private ServerSocket srvs;
    private ConnectionManager cm;
    private Processor proc;
    
    public Server ()
    {
        debug = false;
        
        proc = new Processor ();
        proc.start ();
        cm = new ConnectionManager ();

        try
        {
            srvs = new ServerSocket (5335);
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    public ConnectionManager getConnectionManager ()
    {
        return cm;
    }

    
    /**
     * Starte die Ausführung des Servers
     */
    public void run ()
    {
        Server.consoleMsg ("Server gestartet. Moin.");
        Server.debugMsg ("Debug-Modus aktiviert.");

        // TODO: max. Verbindungen
        while (true)
        {
            try {
                Socket s = srvs.accept ();
                cm.add (new ClientConnection (s));
                Server.debugMsg (String.format ("Neue Verbindung aufgebaut:  %s",
                                                s.getRemoteSocketAddress ().toString ()));
            }
            catch (Exception e)
            {
                e.printStackTrace ();
            }
        }
    }

    public Processor getProcessor ()
    {
        return proc;
    }

    public static Server getServer ()
    {
        return srv;
    }

    public static void setServer (Server pSrv)
    {
        srv = pSrv;
    }

    public static void setDebug (boolean pDebug)
    {
        debug = pDebug;
    }

    public static boolean getDebug ()
    {
        return debug;
    }

    public static synchronized void debugMsg (String msg)
    {
        if (debug)
        {
            consoleMsg (msg); 
        }
    }
        
    public static synchronized void consoleMsg (String msg)
    {
        System.out.println (String.format ("[  %s  ]   %s",
                            LocalTime.now ().toString (),
                            msg));
    }

    public static void main (String[] args)
    {
        Server server = new Server ();
        
        for (String arg : args)
        {
            if (arg.equals ("debug"))
            {
                Server.setDebug (true);
            }
        }

        setServer (server);
        server.run ();
    }
}
