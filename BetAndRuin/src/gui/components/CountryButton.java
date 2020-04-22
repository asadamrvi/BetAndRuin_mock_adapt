package gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Buttons of the left panel on the browse tab designating the country
 *
 */
@SuppressWarnings("serial")
public class CountryButton extends JButton{

	public CountryButton(String country, JPanel panel) {
		setText(country);
		setBackground(new Color(255,255,255)); 
		setIcon(new ImageIcon("images/country/" + country +  ".png"));
		setHorizontalAlignment(SwingConstants.LEFT);
		setAlignmentY(Component.TOP_ALIGNMENT);
		setFocusPainted(false);
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(220,220,220)));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(!panel.isVisible());
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			setBackground(new Color(190, 190, 190));
		} else if (getModel().isRollover()) {
			setBackground(new Color(235,235,235));
		} else if (isSelected()) {
			setBackground(new Color(200, 200, 200));
		}   
		else {
			setBackground(new Color(255,255,255));
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