package com.spiru.dev.groupingTask_addon.Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * Klasse fuer eine Verbindung zwischen zwei Elementen
 * @author Yves
 *
 */
public class Verbindung {
	/** Element1 */
	private DragElement e1;
	/** Element2 */
	private DragElement e2;
	/** Verbindung zwischen Element1 und Element2 */
	private Line2D linie;
	/** Wenn markiert, dann in anderer Farbe dargestellt */
	private boolean markiert;
	
	/**
	 * Konstruktor zur Erzeugung einer Verbindung zwischen zwei Elementen
	 * @param e1 Element1
	 * @param e2 Element2
	 */
	public Verbindung(DragElement e1, DragElement e2){
		this.e1 = e1;
		this.e2 = e2;
		this.markiert = false;		
		linie = new Line2D.Double(e1.getX()+e1.getWidth()/2, e1.getY()+e1.getHeight()/2, e2.getX()+e2.getWidth()/2, e2.getY()+e2.getHeight()/2);		
	}
	
	private void adjustLine(){
		// liegt e1 weiter rechts als e2, dann tausche
		if (e1.getX()>e2.getX()){
			DragElement dummy = e2;
			this.e2 = e1;
			this.e1 = dummy;
		}
		int x1, y1, x2, y2;
		if(e1.getY()<e2.getY()){
			// from  midpoint (x) 
			if(e1.getX()+e1.getWidth() > e2.getX()){
				x1 = e1.getX()+e1.getWidth()/2;
				x2 = e2.getX()+e2.getWidth()/2;
			}
			else{				
				x1 = e1.getX()+e1.getWidth();
				x2 = e2.getX();
				}
			//  from midpoint (y) 
			if(e1.getY()+e1.getHeight()>e2.getY()){
				y1 = e1.getY()+e1.getHeight()/2;
				y2 = e2.getY()+e2.getHeight()/2;				
			}
			else{
				y1 = e1.getY()+e1.getHeight();			
				y2 = e2.getY();				
			}
			linie = new Line2D.Double(x1,y1,x2,y2);
		}
		else{
			// from  midpoint (x)
			if(e1.getX()+e1.getWidth() > e2.getX()){
				x1 = e1.getX()+ (e1.getWidth()/2);
				x2 = e2.getX()+ (e2.getWidth()/2);					
			}else{
				x1 = e1.getX()+e1.getWidth();
				x2 = e2.getX();
			}
			// from  midpoint (y)
			if (e1.getY()+e1.getHeight()-20>e2.getY() && e1.getX()+e1.getWidth()<e2.getX()){
				y1 = e1.getY()+e1.getHeight()/2;
				y2 = e2.getY()+e2.getHeight()/2;								
			}else{
				y1 = e1.getY();					
				y2 = e2.getY()+e2.getHeight();
			}
			linie = new Line2D.Double(x1,y1,x2,y2);
		}
		
			
	}
	
	/**
	 * Wurde Verbindung selektiert?
	 * @return true, wenn markiert
	 */
	public boolean isMarkiert() {
		return markiert;
	}

	/**
	 * Setzt Markierung auf true oder false
	 * @param markiert true-> Objekt wird markiert
	 */
	public void setMarkiert(boolean markiert) {
		this.markiert = markiert;
	}

	/**
	 * Zeichnet die Verbindung
	 * @param g Zeichenbereich auf dem gezeichnet werden soll
	 */
	public void draw(Graphics g){
		adjustLine();
		g.setColor(Color.BLACK);	
		if (markiert)
			g.setColor(Color.YELLOW);			
		Graphics2D g2D = (Graphics2D) g;
		if (markiert)
			g2D.setColor(Color.YELLOW);
		g2D.draw(linie);			
	}
	
	/**
	 * Liefert Element 1 zurueck
	 * @return e1
	 */
	public DragElement getElement1(){
		return e1;
	}
	
	/**
	 * Liefert Element 2 zurueck
	 * @return e2
	 */
	public DragElement getElement2(){
		return e2;
	}
	
	/**
	 * Prueft, ob Verbindung die selbe ist wie die uebergebene Verbindung
	 * @param bindung Verbindung, die verglichen werden soll
	 * @return true, wenn gleich; false, wenn zwei verschiedene 
	 */
	public boolean find(Verbindung bindung){
		if ((e1.equals(bindung.getElement1()) && e2.equals(bindung.getElement2()) )
				|| (e2.equals(bindung.getElement1()) && e1.equals(bindung.getElement2()))
			)
			return true;
		return false;
	}
	
	/**
	 * Prueft, ob Verbindung zum gegebenem Element gehoert
	 * @param e Element
	 * @return true, wenn Element zur Verbindung gehoert; false, wenn nicht 
	 */
	public boolean find(DragElement e){
		if (e1 == e || e2 == e)
			return true;
		return false;
	}
	
	/**
	 * Test, ob auf Verbindung geklickt wurde.
	 * @param maus Pointer mit zu pruefenden Koordinaten
	 * @return true, wenn Verbindung angeklickt wurde
	 */
	public boolean isClickLine(Point maus){	
		if (linie.intersects(maus.getX()-5, maus.getY()-5, 10, 10))
			return true;			
		return false;
	}

}
