package gui.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * Extended Jtextfield with the possibility of displaying hint text
 *
 */
public class HintTextField extends JTextField implements FocusListener {
	private static final long serialVersionUID = 1L;

	private final String hint;
	private boolean showingHint;
	private Color textcolor;
	private Color hintcolor;

	public HintTextField(final String hint) { 	
		super(hint);
		this.hint = hint;
		
		this.textcolor = new Color(255,255, 255);
		this.hintcolor = new Color(169, 169, 169);
		this.setCaretColor(new Color(255,255,255));
		
		this.setOpaque(false);
		this.showingHint = true;
		super.addFocusListener(this);
		this.setForeground(hintcolor);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
	}

	public HintTextField(final String hint, Color backcolor, Color bordercolor, Color textcolor, Color hintcolor, Color caretcolor) { 
		super(hint);
		this.hint = hint;
		
		this.textcolor = textcolor;
		this.hintcolor = hintcolor;
		this.setCaretColor(caretcolor);
		
		this.setBackground(backcolor);
		this.showingHint = true;
		super.addFocusListener(this);
		this.setForeground(new Color(169, 169, 169));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, bordercolor));
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText("");
			showingHint = false;
			this.setForeground(textcolor);		
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText(hint);
			showingHint = true;
			this.setForeground(hintcolor);
		}
	}

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
	
	public void setColor(Color backcolor, Color bordercolor, Color textcolor) {
		this.setBackground(backcolor);
		this.setForeground(textcolor);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, bordercolor));
		
	}
}