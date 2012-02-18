package gui;

import javax.swing.JButton;
import java.awt.*;

/**
 * @author Aly
 *
 */
public class AlyButton extends JButton{
	
	
	public AlyButton(String text){
		super(text);
		setBackground(ColorScheme.DEFAULT_MAIN);
		setForeground(ColorScheme.DEFAULT_SECONDARY);
		setFocusable(false);
		setBorder(null);
	}
	
	public AlyButton(){
		this("");
	}
	
	public void flipColors(){
		Color c = getBackground();
		setBackground(getForeground());
		setForeground(c);
	}

}
