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
		setRolloverEnabled(false);
		setBorder(null);
	}
	
	public AlyButton(){
		this("");
	}

}
