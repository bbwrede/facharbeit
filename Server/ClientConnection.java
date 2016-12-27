import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Scanner;
import java.lang.InterruptedException;

class ClientConnection extends Thread
{
    private Socket socket;

    private InputStream ins;
    private Scanner dataIn;

    private OutputStream outs;
    private AbstractQueue<Command> outputQueue;
    private DataOutputStream dataOut; 

    private String nickname;

    public ClientConnection (Socket pSocket)
    {
        socket = pSocket;

        try 
        {
            ins = socket.getInputStream ();
            outs = socket.getOutputStream ();
        }

        catch (IOException e)
        {
            e.printStackTrace ();
        }

        dataIn = new Scanner (ins);
        dataIn.useDelimiter ("%");

        outputQueue = new ConcurrentLinkedQueue<Command> ();
        dataOut = new DataOutputStream (outs);

        nickname = null;
    }

    public void run ()
    {
        while (true)
        {
            if (dataIn.hasNext ())
            {
                String line = dataIn.next ();
                line = line.trim ();
                Command cmd;
                try
                {
                    cmd = new ValidCommand (line);
                }
                catch (Command.ParsingException e)
                {
                    cmd = new InvalidCommand (line);
                }
                Server.getServer ().getProcessor ().enqueue (cmd, this);
            }

            if (!outputQueue.isEmpty ())
            {
                String cmd = outputQueue.remove ().toString ();
                try
                {
                    dataOut.writeUTF(cmd);
                }
                catch (IOException e)
                {
                    e.printStackTrace ();
                }
            }

            try
            {
                Thread.sleep (50);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace ();
            }
        }
    }

    public void setNickname (String pNick)
    {
        nickname = pNick;
    }

    public String getNickname ()
    {
        return nickname;
    }
}
