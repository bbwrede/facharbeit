
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.AbstractQueue;
import java.util.Scanner;

class ClientConnection extends Thread
{
    private Socket socket;

    private InputStream ins;
    private Scanner scanner;

    private OutputStream outs;
    private AbstractQueue<Command> outputQueue;
    private BufferedWriter outputWriter; 

    public ClientConnection (Socket pSocket)
    {
        socket = pSocket;

        ins = socket.getInputStream ();
        outs = socket.getOutputStream ();

        scanner = new Scanner (ins);
        scanner.useDelimiter ("%");

        outputQueue = new AbstractQueue<Command> ();
        outputWriter = new BufferedWriter (outs);
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
                String cmd = outputQueue.remove ().toString ();
                outputWriter.write (cmd, 0, cmd.length ());
            }

            Thread.sleep (50);
        }
    }
}
