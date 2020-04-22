package gui.components;

import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class passVisibleLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	
	private boolean visible;
	private HintPassField pwfield;
	
	public passVisibleLabel(HintPassField pwf) {
		this.pwfield = pwf;
		setIcon(new ImageIcon("images/passvisible.png"));
		this.visible = false;
		
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!visible) {
					pwfield.setEchoChar((char)0);
					setIcon(new ImageIcon("images/passinvisible.png"));
					visible = true;
				}
				else {
					pwfield.setEchoChar('•');
					setIcon(new ImageIcon("images/passvisible.png"));
					visible = false;
				}
			}
		});
	}
	
	
		
	

}
