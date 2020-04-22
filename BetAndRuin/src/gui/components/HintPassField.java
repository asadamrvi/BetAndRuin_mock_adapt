package gui.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

/**
 * Extended JPasswordField with the possibility of displaying hint text
 */
public class HintPassField extends JPasswordField implements FocusListener{

private static final long serialVersionUID = 1L;
	
	 private final String hint;
	 private boolean showingHint;
	 
	 
	  public HintPassField(final String hint) {
	    super(hint);
	    this.hint = hint;
	    this.setOpaque(false);
	    this.showingHint = true;
	    this.setEchoChar((char)0);
	    super.addFocusListener(this);
	    this.setForeground(new Color(169, 169, 169));
	    this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
	  }

	  @Override
	  public void focusGained(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      this.setEchoChar('•');
	      super.setText("");
	      showingHint = false;
	      this.setForeground(new Color(255,255, 255));
	      this.setCaretColor(new Color(255,255,255));
	    }
	  }
	  @Override
	  public void focusLost(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	        this.setEchoChar((char)0);
	      super.setText(hint);
	      showingHint = true;
	      this.setForeground(new Color(169, 169, 169));
	    }
	  }

	@SuppressWarnings("deprecation")
	@Override
	  public String getText() {
	    return showingHint ? "" : super.getText();
	  }

}
