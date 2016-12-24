
import java.net.ServerSocket;
import java.lang.Exception.*;

class Server
{
    private static Server srv;
    
    private ServerSocket srvs;
    private ConnectionManager cm;
    private Processor proc;
    
    public Server ()
    {
        proc = new Processor ();
        proc.start ();
        cm = new ConnectionManager ();

        try {
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
        // TODO: max. Verbindungen
        while (true)
        {
            
        }
    }

    public static Server getServer ()
    {
        return srv;
    }

    public static void setServer (Server pSrv)
    {
        srv = pSrv;
    }

    public static void main (String[] args)
    {
        Server server = new Server ();
        setServer (server);
        server.run ();
    }
}
