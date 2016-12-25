import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.UUID;

import javax.swing.JOptionPane;

public class Client extends Thread implements ActionListener
{
	private Socket socket = null;
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
		port = 5335;
		gui = new GUI();
		gui.setActionListener(this);
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
	
	public void connect() throws UnknownHostException, IOException
	{
		socket = new Socket(gui.getHostname(), port);
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		
		if (cmd.equals("Connect"))
		{
			System.out.println("Connect");
			try
			{
				connect();
			} catch (IOException e1)
			{
				JOptionPane.showMessageDialog(gui.getFrame(),
					    "Es konnte keine Verbindung hergestellt werden.","Verbindung fehlgeschlagen" , JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
		
		if (cmd.equals("Exit"))
		{
			System.exit(0);
		}
		
	}

}


