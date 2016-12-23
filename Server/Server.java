
import java.net.ServerSocket;
import java.io.IOException;
import java.lang.Exception.*;

class Server
{
    private ServerSocket ss;
    private ConnectionManager cm;

    
    public Server ()
    {
        cm = new ConnectionManager ();

        try {
            ss = new ServerSocket (53335);
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

    public static void main (String[] args)
    {
        Server srv = new Server ();
        srv.run ();
    }
}
