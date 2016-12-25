import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Controller implements ActionListener
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
	
	public static void main(String[] args)
	{
		new Controller();
	}
}
