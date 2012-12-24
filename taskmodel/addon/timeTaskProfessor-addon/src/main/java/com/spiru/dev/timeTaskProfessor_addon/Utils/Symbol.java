package com.spiru.dev.timeTaskProfessor_addon.Utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
	private DatePoint connectedDatePoint = null;
	private boolean visible = true;
	private JTextField textfield = null;
	
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
		markSymbol(false);
		// Drag'n Drop
		DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this,
            DnDConstants.ACTION_COPY, this);    
	}	
	
	public int getId(){
		return id;
	}
	
	public void setDatePoint(DatePoint dp, boolean visible){
		setDatePoint(dp);
		this.visible = !visible;
		if (visible){
			Border margin = new LineBorder(Color.BLUE,2);
			this.setBorder(margin);
		}
		else{
			Border margin = new LineBorder(Color.BLACK,2);
			this.setBorder(margin);
		}
	}
	
	/*
	private void createTextField(){
		textfield = new JTextField();
		this.setSize(200, 35);
		textfield.setBounds(5,5,180,25);						
		this.add(textfield);	
	}
	*/
	
	public void setDatePoint(DatePoint dp){
		if (connectedDatePoint != null)
			if (connectedDatePoint.getSymbol() != null){				
				DatePoint a = connectedDatePoint;
				connectedDatePoint = null;
				a.setSymbol(null);
				
			}
		connectedDatePoint = dp;
		if (dp == null){
			visible = true;			
			Border margin = new LineBorder(Color.BLACK,1);
			this.setBorder(margin);			
		}
		else{
			if (visible){
				Border margin = new LineBorder(Color.BLUE,2);
				this.setBorder(margin);
			}
			else{
				Border margin = new LineBorder(Color.BLACK,2);
				this.setBorder(margin);
			}
		}
	}
	
	public DatePoint getDatePoint(){
		return connectedDatePoint;
	}
	
	/**
	 * set the ConnectionLine
	 * @param line ConnectionLine
	 */
	public void setConnectionLine(ConnectionLine line){
		if (line == null)
			this.setDatePoint(null);
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
		if (!visible)
			g.setColor(Color.BLUE);
		if (markLine)
			g.setColor(Color.YELLOW);
		if (line != null){
			line.drawConnectionLine(g);
		}
		g.setColor(Color.BLACK);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(connectedDatePoint != null){			
			if (visible){
				g.drawString("!",9,13);				
			}
			else
				g.drawString("?",7,13);
		}
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
			if (connectedDatePoint == null)
				margin = new LineBorder(Color.BLACK,1);
			else if (!visible)
				margin = new LineBorder(Color.BLUE,2);
			else
				margin = new LineBorder(Color.BLACK,2);
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
            //cursor = DragSource.DefaultCopyNoDrop;
        	cursor = getToolkit().createCustomCursor(
        			  img,//new ImageIcon(img).getImage(),
        			  new Point(8,8), "Cursor" );        	
        }
        // Drag starten, Cursor uebergeben sowie Element, das gedropt werden soll
        event.startDrag(cursor, new TransferableElement(symbol));	
	}			
}
