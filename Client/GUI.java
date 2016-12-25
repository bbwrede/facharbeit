import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextPane;

public class GUI extends JFrame
{

	private JPanel contentPane;
	private JPanel paneConnect;
	private JLabel lblChatroom;
	private JPanel panel_1;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JTextField tfHostname;
	private JButton btnConnect;
	private JSeparator separator;
	private JLabel lblUsername;
	private JButton btnExit;
	private JPanel paneChat;
	private JScrollPane spChat;
	private JList listChat;
	private JScrollPane spText;
	private JTextPane tpText;
	private JButton btnSend;
	private JButton btnDelete;


	public GUI()
	{
		try 
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) 
		{
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		paneChat = new JPanel();
		paneChat.setBounds(0, 0, 634, 421);
		contentPane.add(paneChat);
		paneChat.setLayout(null);
		
		spChat = new JScrollPane();
		spChat.setBounds(6, 6, 622, 283);
		paneChat.add(spChat);
		
		listChat = new JList();
		spChat.setViewportView(listChat);
		
		spText = new JScrollPane();
		spText.setBounds(6, 301, 622, 84);
		paneChat.add(spText);
		
		tpText = new JTextPane();
		tpText.setText("Hier die Nachricht eingeben");
		spText.setViewportView(tpText);
		
		btnSend = new JButton("Absenden");
		btnSend.setBounds(538, 387, 90, 28);
		paneChat.add(btnSend);
		
		btnDelete = new JButton("Löschen");
		btnDelete.setBounds(436, 387, 90, 28);
		paneChat.add(btnDelete);
		
		paneConnect = new JPanel();
		paneConnect.setBounds(0, 0, 634, 421);
		contentPane.add(paneConnect);
		paneConnect.setLayout(null);
		
		lblChatroom = new JLabel("Chatroom");
		lblChatroom.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblChatroom.setHorizontalAlignment(SwingConstants.CENTER);
		lblChatroom.setBounds(257, 11, 120, 56);
		paneConnect.add(lblChatroom);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(196, 83, 241, 255);
		paneConnect.add(panel_1);
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
		paneConnect.add(btnConnect);
		
		btnExit = new JButton("Beenden");
		btnExit.setBounds(345, 350, 92, 28);
		paneConnect.add(btnExit);
		
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
	
	public String getUsername()
	{
		return tfUsername.getText();
	}
	
	public char[] getPassword()
	{
		return pfPassword.getPassword();
	}
	
	public JFrame getFrame()
	{
		return this;
	}
}
