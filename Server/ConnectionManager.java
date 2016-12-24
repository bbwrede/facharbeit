import java.util.ArrayList;

import java.util.ArrayList;

class ConnectionManager
{
    private ArrayList<ClientConnection> connections;

    public ConnectionManager ()
    {
        connections = new ArrayList<ClientConnection> ();
    }

    /**
     * Fügt eine ClientConnection hinzu und startet die Ausführung
     * ihres Threads.
     * @param conn Die hinzuzufügende ClientConnection
     */
    public void add (ClientConnection conn)
    {
        connections.add (conn);
        conn.run ();
    }

    /**
     * Entfernt eine ClientConnection und stoppt die Ausführung ihres
     * Threads.
     * @param conn Die zu entfernende ClientConnection
     */
    public void remove (ClientConnection conn)
    {
        connections.remove (conn);
        conn.stop ();
    }
}
