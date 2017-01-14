import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Scanner;
import java.lang.InterruptedException;
import java.lang.Runnable;


/**
 * Diese Klasse repräsentiert einen Client und seine Verbindung zum Server.
 * 
 * <p>Ihre Aufgabe ist es, eingehende Kommandos zur Weiterverarbeitung anzunehmen und entsprechende
 * Antworten an den Client weiterzuleiten.
 */
class ClientConnection
{
    private Socket socket;
    private InputStream ins;
    private OutputStream outs;

    private Thread threadIn;
    private Thread threadOut;

    private LinkedBlockingQueue<Command> outputQueue;

    private String nickname;

    private boolean indicateStop;

    /**
     * Zuständig für eingehende Kommandos.
     */
    class Input implements Runnable
    {
        private Scanner scanner;

        public Input ()
        {
            scanner = new Scanner (ins);
        }

        public void run ()
        {
            while (!indicateStop)
            {
                try
                {
                    if (scanner.hasNextLine ())
                    {
                        String line = scanner.nextLine ();

                        // Wenn in Zeile kein Command enthalten ist, überspringen.
                        if (!line.matches ("[^%]*%[^%]+%[^%]*"))
                        {
                            continue;
                        }

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
                        Server.getServer ().getProcessor ().enqueue (cmd, ClientConnection.this);
                    }
                }
                catch (Exception e)
                {
                    Server.getServer().getConnectionManager().remove(ClientConnection.this);
                    indicateStop = true;
                }
            }
        }
    }

    /**
     * Zuständig für zum Client ausgehende Kommandos.
     */
    class Output implements Runnable
    {
        private PrintWriter writer;

        private ClientConnection cc;

        public Output ()
        {
            writer = new PrintWriter (outs, true);
        }

        public void run ()
        {
            while (!indicateStop)
            {
                try
                {
                    Command cmd = outputQueue.take ();
                    writer.print (cmd.toString ());
                    writer.flush ();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace ();
                }
                catch (Exception e)
                {
                    Server.getServer().getConnectionManager().remove(ClientConnection.this);
                    indicateStop = true;
                }
            }
        }
    }

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

        threadIn = new Thread (new Input ());
        threadOut = new Thread (new Output ());

        outputQueue = new LinkedBlockingQueue<Command> ();

        nickname = null;
    }

    /**
     * Fange an, mit dem Client zu kommunizieren.
     */
    public void start ()
    {
        threadIn.start ();
        threadOut.start ();
    }

    /**
     * Höre auf, mit dem Client zu kommunizieren.
     */
    public void stop ()
    {
        indicateStop = true;
    }

    /**
     * Setze den Nicknamen des Clients.
     */
    public void setNickname (String pNick)
    {
        nickname = pNick;
    }

    /**
     * @return Der Nickname des Clients.
     */
    public String getNickname ()
    {
        return nickname;
    }
    
    /**
     * Übermittle dem Client ein Kommando.
     *
     * <p>Diese Methode blockiert nicht, sondern fügt das Kommando stattdessen in eine
     * Queue ein, sodass es von {@link ClientConnection.Output} verarbeitet wird.
     */
    public void sendCmd (Command cmd)
    {
        try
        {
            outputQueue.put (cmd);
        } 
        catch (InterruptedException e)
        {
            e.printStackTrace ();
        }
    }
}
