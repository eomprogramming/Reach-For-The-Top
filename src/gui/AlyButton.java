package gui;

import javax.swing.JButton;
import java.awt.*;

public class AlyButton extends JButton{
	
	private Color mainColor, secondColor;
	
	public AlyButton(String text){
		super(text);
		setBackground(mainColor);
	}
	
	public AlyButton(){
		this("");
	}

}
