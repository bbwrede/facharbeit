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
		
		ValidCommand cmd = new ValidCommand();
		cmd.setType(Command.Type.REG);
		String[] param = {username};
		cmd.setParams(param);
		
		output.println(cmd.toString());
		output.flush();
		
		this.start();
		
	}
	
	public void heartbeat()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask() 
		{ 
			public void run() 
			{ 
				ValidCommand cmd = new ValidCommand();
				cmd.setType(Command.Type.HEARTBEAT);

				output.println(cmd.toString());
				output.flush();
				System.out.println("30");
			}
		},  0 ,30000);
	}
	
	public String getMessage()
	{
		while (true)
		{	
			if (reader.hasNextLine())
			{
				String cmd = reader.nextLine();
				System.out.println(cmd);
				return cmd;
			}
		}
	}
	
	public void sendMessage(String pMessage)
	{
		ValidCommand cmd = new ValidCommand();
		cmd.setType(Command.Type.MSG);
		String[] param = {"*",pMessage};
		cmd.setParams(param);
		
		output.println(cmd.toString());
		output.flush();
	}
}


