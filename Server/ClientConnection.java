import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ClientConnection extends Thread
{
    private Socket socket;
    private InputStream ins;
    private OutputStream outs;

    public ClientConnection (Socket pSocket)
    {
        socket = pSocket;
    }

    public void run ()
    {
    
    }
}
