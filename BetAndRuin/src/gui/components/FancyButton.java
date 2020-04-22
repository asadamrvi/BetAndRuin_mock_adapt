package gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

/**
 * Buttons that change color dinamically when user presses, hovers or selects them.
 */
@SuppressWarnings("serial")
public class FancyButton extends JButton{
	
	private Color background ;
	private Color hover;
	private Color selected;
	
	public FancyButton(String text, Color background, Color hover, Color selected){
		this.setText(text);
		
		this.background = background;
		this.hover = hover;
		this.selected = selected;
		
		setFocusPainted(false);
		setBorderPainted(false);
		//setIcon(new ImageIcon("images/competition/" + competition.toString() +  ".png"));

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			setBackground(background);
		} else if (getModel().isRollover()) {
			setBackground(hover);
		} else if (isSelected()) {
			setBackground(selected);
		}   
		else {
			setBackground(background);
		}
		super.paintComponent(g);
	}
	
	@Override
	public Dimension getMaximumSize()
	{
		Dimension d = super.getMaximumSize();
		d.width = Integer.MAX_VALUE;
		return d;
	}
}
