import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;


public class Controller extends Thread implements ActionListener
{
	private GUI gui;
	private Client client;
	
	public Controller()
	{
		gui = new GUI();
		gui.setActionListener(this);
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
				client = new Client(gui.getUsername(),gui.getHostname());
				gui.showChat();
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
		
		if (cmd.equals("Send"))
		{
			client.sendMessage(gui.getMessage());
		}
		
	}
	
	public void run()
	{
		client.heartbeat();
		while(true)
		{
			if (client.getMessage() != null)
			{
				try
				{
					ValidCommand cmd = new ValidCommand(client.getMessage());
					gui.showMessage(cmd.getParams()[1], cmd.getParams()[2]);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Controller();
	}
}
