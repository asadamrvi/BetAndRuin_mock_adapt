package gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class cancelLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;

	public cancelLabel(JDialog d) {
		setText("Cancel");
		setBackground(new Color(0, 0, 0));
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 18));
		setForeground(new Color(153, 153, 153));
		setBounds(409, 339, 136, 35);
		addMouseListener(new java.awt.event.MouseAdapter() {		
			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(new Color(153,153,153));		
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(new Color(255,255,255));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				d.dispose();
			}
		});
	}
	
}
