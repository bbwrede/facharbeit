import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.sun.corba.se.spi.orbutil.fsm.Input;

public class Client extends Thread
{
	private Socket socket = null;
	private int port;
	private InetAddress ip;
	private String username;
	private UUID uuid;
	private PrintWriter output;
	private Scanner reader;

	public Client(String pUsername, String pIp) throws UnknownHostException, IOException
	{
		uuid = UUID.randomUUID();
		port = 5335;
		ip = ip.getByName(pIp);
		username = pUsername;
		connect();
	}
	
	public void connect() throws UnknownHostException, IOException
	{
		socket = new Socket(ip, port);
		output = new PrintWriter(socket.getOutputStream());
		reader = new Scanner(socket.getInputStream());
		
		this.start();
		
	}
	
	public void heartbeat()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask() 
		{ 
			public void run() 
			{ 
				output.write("%heartbeat$"+uuid+"%");
				output.flush();
				System.out.println("30");
			}
		},  0 ,30000);
	}
	
	public void run()
	{
		heartbeat();
		while (true)
		{	
			
			if (reader.hasNextLine())
			{
				String cmd = reader.nextLine();
				System.out.println(cmd);
			}
			
			System.out.println("efsefsef");
			
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
}


