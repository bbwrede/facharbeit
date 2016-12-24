import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class GUI extends JFrame
{

	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblChatroom;
	private JPanel panel_1;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JTextField tfHostname;
	private JButton btnConnect;
	private JSeparator separator;
	private JLabel lblUsername;
	private JButton btnExit;


	public GUI()
	{
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 634, 421);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblChatroom = new JLabel("Chatroom");
		lblChatroom.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblChatroom.setHorizontalAlignment(SwingConstants.CENTER);
		lblChatroom.setBounds(257, 11, 120, 56);
		panel.add(lblChatroom);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(196, 83, 241, 255);
		panel.add(panel_1);
		panel_1.setLayout(null);
		tfUsername = new JTextField();
		tfUsername.setBounds(39, 44, 162, 28);
		panel_1.add(tfUsername);
		tfUsername.setColumns(10);
		
		tfHostname = new JTextField();
		tfHostname.setBounds(39, 119, 162, 28);
		panel_1.add(tfHostname);
		tfHostname.setColumns(10);
		
		separator = new JSeparator();
		separator.setBounds(39, 84, 162, 2);
		panel_1.add(separator);
		
		lblUsername = new JLabel("Nutzername");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(54, 16, 122, 16);
		panel_1.add(lblUsername);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(39, 159, 162, 28);
		panel_1.add(pfPassword);
		
		btnConnect = new JButton("Verbinden");
		btnConnect.setBounds(196, 350, 137, 28);
		panel.add(btnConnect);
		
		btnExit = new JButton("Beenden");
		btnExit.setBounds(345, 350, 92, 28);
		panel.add(btnExit);
		
		setVisible(true);
	}
	
	public void setActionListener(ActionListener l)
	{
		btnConnect.addActionListener(l);
		btnConnect.setActionCommand("Connect");
		btnExit.addActionListener(l);
		btnExit.setActionCommand("Exit");
	}
	
	public String getHostname()
	{
		return tfHostname.getText();
	}
	
	public char[] getPassword()
	{
		return pfPassword.getPassword();
	}
}
