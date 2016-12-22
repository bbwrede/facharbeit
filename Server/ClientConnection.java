
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
