import java.util.HashSet;
import java.lang.Iterable;
import java.util.Iterator;

/**
 * Verwaltet alle {@link ClientConnection}s
 */
class ConnectionManager implements Iterable<ClientConnection>
{
    private HashSet<ClientConnection> connections;

    public ConnectionManager ()
    {
        connections = new HashSet<ClientConnection> ();
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

    /**
     * @return Alle momentan registrierten Nicknames.
     */
    public HashSet<String> getAllNicknames ()
    {
        HashSet<String> result = new HashSet<String> ();
        for (ClientConnection conn : connections)
        {
            if (conn.getNickname() != null)
            {
                result.add (conn.getNickname ());
            }  
        }
        return result;
    }

    /**
     * @return Ob ein Nickname bereits vergeben ist.
     *
     * @param nick Der zu überprüfende Nickname
     */
    public boolean isNicknameTaken (String nick)
    {
        for (ClientConnection conn : connections)
        {
            if (conn.getNickname ().equals (nick))
            {
                return true;
            }
        }
        return false;
    }

    public Iterator<ClientConnection> iterator ()
    {
        return connections.iterator ();
    }


}
