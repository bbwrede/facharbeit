import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Client extends Thread
{
	private Socket socket = new Socket();
	private int port;
	private InetAddress ip;
	private String username;
	private UUID uuid;
	private GUI gui;
	private PrintWriter output;
	private Scanner reader;

	public Client()
	{
		uuid = UUID.randomUUID();
		gui = new GUI();
	}
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public static void main(String[] args)
	{
		new Client();
	}

}


