package gui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;


@SuppressWarnings("serial")
public class Switch extends JComponent implements MouseListener{

    /**
     * Captures the state of the switch
     */
    private boolean OnOff = true;
    
    /**
     * Margin between the border of the component and the switch
     */
    private final int MARGIN = 5;
    
    /**
     * Margin between the circular button and the switch
     */
    private final int BORDER = 4;
    
    /**
     * Background color of the switch (not the component) 
     */
    private Color backgroundColor;
    
    /**
     * Color of the circular button of the switch
     */ 
    private Color buttonColor;
    
    /**Color del interrupor cuando esta desabilitado
     */
    private final Color DISABLED_COMPONENT_COLOR = new Color(131,131,131);
    

    public Switch(){
        super();
        Switch.this.setSize(new Dimension(60, 40));
        Switch.this.setPreferredSize(new Dimension(60, 40));
        Switch.this.setMinimumSize(new Dimension(60, 40));
        Switch.this.setVisible(true);
        Switch.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Switch.this.addMouseListener(Switch.this);  
        //colores iniciales
        Switch.this.setBackgroundColor(new Color(75,216,101));
        Switch.this.setButtonColor(new Color(255,255,255));
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isOpaque()) {//Colors the background of the component 
            g2.setColor(getBackground());
            g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        }

        if(isEnabled()){//component enabled
            g2.setColor(( (OnOff) ? getBackgroundColor():new Color(216,217,219) ) );
            g2.fill(new RoundRectangle2D.Double((float) MARGIN, (float) MARGIN, 
                (float) getWidth() - MARGIN * 2, (float) getHeight() - MARGIN * 2,
                getHeight() - MARGIN * 2, getHeight() - MARGIN * 2));
        }else{//component disabled
            g2.setColor(DISABLED_COMPONENT_COLOR );    
            g2.draw(new RoundRectangle2D.Double((float) MARGIN, (float) MARGIN, 
                (float) getWidth() - MARGIN * 2, (float) getHeight() - MARGIN * 2,
                getHeight() - MARGIN * 2, getHeight() - MARGIN * 2));
        }
        
        g2.setColor((isEnabled()) ? getButtonColor() : DISABLED_COMPONENT_COLOR);
        //circular button    
        if (OnOff) {//ON to the left          
            g2.fillOval(MARGIN + BORDER / 2, MARGIN + BORDER / 2, 
                    getHeight() - MARGIN * 2 - BORDER, getHeight() - MARGIN * 2 - BORDER);
        } else {//OFF to the right
            g2.fillOval(getWidth() - getHeight() + MARGIN + BORDER / 2, MARGIN + BORDER / 2,
                    getHeight() - MARGIN * 2 - BORDER, getHeight() - MARGIN * 2 - BORDER);
        }
    }

    /**
     * returns the state of the switch
     * 
     * @return boolean True: ON False: OFF
     */
    public boolean isOnOff() {
        return OnOff;
    }

    public void setOnOff(boolean OnOff) {
        this.OnOff = OnOff;        
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(Color buttonColor) {
        this.buttonColor = buttonColor;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (isEnabled()) {
            OnOff = !OnOff;
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { /*...*/ }

    @Override
    public void mouseReleased(MouseEvent e) { /*...*/ }

    @Override
    public void mouseEntered(MouseEvent e) { /*...*/ }

    @Override
    public void mouseExited(MouseEvent e) { /*...*/ }
    
}