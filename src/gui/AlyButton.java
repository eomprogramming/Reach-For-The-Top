package gui;

import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.*;

/**
 * @author Aly
 *
 */
public class AlyButton extends JButton{
	
	private boolean flipped = false;
	
	public AlyButton(String text, boolean rollover){
		super(text);
		setBackground(ColorScheme.DEFAULT_MAIN);
		setForeground(ColorScheme.DEFAULT_SECONDARY);
		setFocusable(false);
		setFont(new Font("Mangal",Font.PLAIN,20));
		setBorder(null);
		if(rollover){
			addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			        setBackground(ColorScheme.DEFAULT_ROLLOVER);
			        setForeground(ColorScheme.DEFAULT_ROLLOVER_TEXT);
			    }
			    public void mouseExited(java.awt.event.MouseEvent evt) {
			    	setBackground(ColorScheme.DEFAULT_MAIN);
			    	setForeground(ColorScheme.DEFAULT_SECONDARY);
			    	if(flipped)
			    		flipColors();
			    }
			    public void mousePressed(java.awt.event.MouseEvent evt) {
					UIManager.put("Button.select", ColorScheme.DEFAULT_ROLLOVER);
			    }
			    public void mouseReleased(java.awt.event.MouseEvent evt) {
					UIManager.put("Button.select", ColorScheme.DEFAULT_SECONDARY);
			    }
			});
		}else{
			addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			    	int size = getFont().getSize();
			    	int tenp = (int) (size*0.1);
			        setFont(new Font("Mangal",Font.BOLD,size+tenp));
			    }
			    public void mouseExited(java.awt.event.MouseEvent evt) {
			    	int size = getFont().getSize();
			    	int tenp = (int)(size*0.1);
			    	setFont(new Font("Mangal",Font.PLAIN,size-tenp));
			    }		   
			});
		}
	}
	
	public AlyButton(){
		this("");
	}
	
	public AlyButton(String text){
		this(text, true);
	}
	
	public void flipColors(){
		flipped=true;
		Color c = getBackground();
		setBackground(getForeground());
		setForeground(c);
	}

}
