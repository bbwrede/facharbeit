
class ClientConnection extends Thread
{
    private Socket socket;

    private InputStream ins;
    private OutputStream outs;

    private Scanner scanner;

    private AbstractQueue outputQueue;
    private 

    public ClientConnection (Socket pSocket)
    {
        socket = pSocket;

        ins = socket.getInputStream ();
        outs = socket.getOutputStream ();

        scanner = new Scanner (ins);
        scanner.useDelimiter ("%");
    }

    public void run ()
    {
        while (true)
        {
            if (scanner.hasNextLine ())
            {
                String line = scanner.nextLine ();
                line = line.trim ();
                Command cmd = new Command (line);
                Server.getServer ().getProcessor ().enqueue (cmd);
            }

            if (!outputQueue.isEmpty ())
            {
                
            }
        }
    }
}
