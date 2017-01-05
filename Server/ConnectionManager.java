import java.util.ArrayList;
import java.lang.Iterable;
import java.util.Iterator;

/**
 * Verwaltet alle {@link ClientConnection}s
 */
class ConnectionManager implements Iterable<ClientConnection>
{
    private ArrayList<ClientConnection> connections;

    public ConnectionManager ()
    {
        connections = new ArrayList<ClientConnection> ();
    }

    /**
     * Fügt eine ClientConnection hinzu und startet die Ausführung
     * ihrer Threads.
     * @param conn Die hinzuzufügende ClientConnection
     */
    public void add (ClientConnection conn)
    {
        connections.add (conn);
        conn.start ();
    }

    /**
     * Entfernt eine ClientConnection und stoppt die Ausführung ihrer
     * Threads.
     * @param conn Die zu entfernende ClientConnection
     */
    public void remove (ClientConnection conn)
    {
        connections.remove (conn);
        conn.stop ();
    }

    /**
     * @return Eine ClientConnection mit dem Nickname nickname.
     */
    public ClientConnection get (String nickname)
    {
        for (ClientConnection conn : connections)
        {
            if (conn.getNickname ().equals (nickname))
            {
                return conn;
            }
        }

        return null;
    }

    public Iterator<ClientConnection> iterator ()
    {
        return connections.iterator ();
    }
}
