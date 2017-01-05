
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

    /**
     * @return Den ConnectionManager des Servers
     */
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

    /**
     * @return Den Processor des Servers.
     */
    public Processor getProcessor ()
    {
        return proc;
    }

    /**
     * @return Die Serverinstanz
     */
    public static Server getServer ()
    {
        return srv;
    }

    /**
     * Setze die laufende Serverinstanz, sodass Objekte, die Server nicht kennen,
     * dennoch per {@link Server.getServer} auf sie zugreifen können.
     */
    public static void setServer (Server pSrv)
    {
        srv = pSrv;
    }

    /**
     * Stelle den Debugmodus an oder aus.
     */
    public static void setDebug (boolean pDebug)
    {
        debug = pDebug;
    }

    /**
     * @return Ob der Debugmodus aktiviert bzw deaktiviert ist.
     */
    public static boolean getDebug ()
    {
        return debug;
    }

    /**
     * Sende eine Debugging-Nachricht an die Serverkonsole.
     *
     * <p>Die Nachricht wird mit einem Zeitstempel versehen.
     *
     * <p>Nachricht wird nur angezeigt, wenn der Debugmodus aktiviert ist. (Im Regelbetrieb
     * ist sie also nicht zu sehen)
     *
     * @param msg Die anzuzeigende Nachricht.
     */
    public static synchronized void debugMsg (String msg)
    {
        if (debug)
        {
            consoleMsg (msg); 
        }
    }
        
    /**
     * Sende eine Nachricht an die Serverkonsole.
     *
     * <p>Die Nachricht wird mit einem Zeitstempel versehen.
     *
     * @param msg Die anzuzeigende Nachricht.
     */
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
