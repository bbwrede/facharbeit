import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

class HintTextField extends JTextField implements FocusListener 
{

	private final String hint;
	private boolean showingHint;

	public HintTextField(final String pHint) 
	{
		super(pHint);
		hint = pHint;
		showingHint = true;
		setForeground(Color.GRAY);
		super.addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
		if(getText().isEmpty()) 
		{
			setForeground(Color.BLACK);
			super.setText("");
			showingHint = false;
		}
	}
	@Override
  	public void focusLost(FocusEvent e) 
	{
		if(getText().isEmpty()) 
		{
			super.setText(hint);
			setForeground(Color.GRAY);
			showingHint = true;
		}
	}

	@Override
	public String getText() 
	{
		return showingHint ? "" : super.getText();
	}
	
	@Override
	public void setText(String pText) 
	{
		showingHint = false;
		setForeground(Color.BLACK);
		super.setText(pText);
	}
}