package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

/**
 * JPanel represents a Element (by Color)
 * @author Yves
 *
 */
public class Symbol extends JPanel implements DragGestureListener {	
	/** The ConnectionLine from Symbol to TimeLine */
	private ConnectionLine line = null;
	/** is Line selected? */
	private boolean markLine = false;
	private int id;	
	private DatePoint datePoint = null;
	private JTextField textfield; 
	
	/**
	 * constructor for a Symbol
	 * @param maus Point for Location
	 * @param color BackGroundColor
	 */
	public Symbol(Point maus, Color color, int id){	
		this.setLayout(null);
		this.setBackground(color);
		this.setBounds(maus.x, maus.y, 20,20);	
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.id = id;		
		// Drag'n Drop
		DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this,
            DnDConstants.ACTION_COPY, this);    
	}	
	
	public void setDatePoint(DatePoint dp, MyKeyListener keyLis){
		this.datePoint = dp;
		if (dp.isDateVisible()){
			textfield = new JTextField();
			textfield.setBounds(2,2,67,22);	
			textfield.addKeyListener(keyLis);
			this.add(textfield);			
			this.setSize(new Dimension(71,26));
		}
	}
	
	public void setText(String text){
		if (textfield != null)
			textfield.setText(text);
	}
	
	public String getText(){
		if (textfield == null)
			return null;
		return textfield.getText();
	}
	
	public DatePoint getDatePoint(){
		return datePoint;
	}
	
	public int getId(){
		return id;
	}
	
	/**
	 * set the ConnectionLine
	 * @param line ConnectionLine
	 */
	public void setConnectionLine(ConnectionLine line){
		markLine = false;
		this.line = line;
	}
		
	public boolean isMarkLine(){
		return markLine;
	}
	
	public void setMarkLine(boolean b){
		markLine = b;
	}
	
	public ConnectionLine getConnectionLine(){
		return line;
	}
	
	/**
	 * draws the ConnectionLine
	 * @param g Graphics to draw on it
	 */
	public void drawConnectionLine(Graphics g){
		if (markLine)
			g.setColor(Color.YELLOW);
		if (line != null){
			line.drawConnectionLine(g);
		}
		g.setColor(Color.BLACK);
	}
	
	/**
	 * mark Symbol
	 * @param mark mark Symbol?
	 */
	public void markSymbol(boolean mark){
		Border border = this.getBorder();
		Border margin;
		
		if (mark){
			margin = new LineBorder(Color.YELLOW,2);
			this.setBorder(new CompoundBorder(border, margin));	
		}
		else{
			margin = new LineBorder(Color.BLACK,1);
			this.setBorder(margin);
		}
			
	}

	/**
	 * For Drag'n Drop
	 */
	public void dragGestureRecognized(DragGestureEvent event) {		
        Cursor cursor = null;
        Symbol symbol = this;  
        BufferedImage img = new BufferedImage( 8, 8, BufferedImage.TYPE_4BYTE_ABGR );
        Graphics g = img.getGraphics();
      //  g.setColor( Color.LIGHT_GRAY );
        //g.fillRect( 0, 0, 8, 8 );
        g.setColor( symbol.getBackground() );
        g.fillRect( 3, 3, 5, 5 );
        // Element soll nur kopiert werden
        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            //cursor = DragSource.DefaultCopyDrop;
        	cursor = getToolkit().createCustomCursor(
        			  img,//new ImageIcon(img).getImage(),
        			  new Point(8,8), "Cursor" );
        			       	
        }
        // Drag starten, Cursor uebergeben sowie Element, das gedropt werden soll
        event.startDrag(cursor, new TransferableElement(symbol));	
	}			
}
