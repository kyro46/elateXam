package com.spiru.dev.groupingTaskProfessor_addon.Utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Panel, auf das Elemente gezogen werden koennen 
 * und zwischen diesen dann Verbindungen gezogen werden koennen.
 * @author Yves
 *
 */
public class PanelSpielplatz extends JPanel {
	
	/** Liste mit allen auf das Panel gezogenen Elementen */
	private List<DragElement> elemente;
	/** Liste mit allen zur Auswahl stehenden Elementen */
	private final List<DragElement> auswahlElemente;
	/** MouseListener zur Auswahl von Elementen */
	private MyMouseListener listener;
	/** Liste mit allen Verbindungen zwischen zwei Elementen */
	private List<Verbindung> verbindungen = null;
	
	private String imageAsString;
	
	/**
	 * Konstruktor fuer PanelSpielplatz
	 * @param auswahl zur Auswahl stehender Elemente
	 * @param listener MouseListener fuer Mausaktionen
	 */
	public PanelSpielplatz(List<DragElement> auswahl, MyMouseListener listener){
		elemente = new ArrayList<DragElement>();
		verbindungen = new ArrayList<Verbindung>();
		auswahlElemente = auswahl;
		this.listener = listener;
		this.setLayout(null);
		this.addMouseListener(listener);
				
		this.setDoubleBuffered(false);
	}
	
	/**
	 * zeichnet alle Verbindungen
	 */
	private void zeichneVerbindungen(){		
		for(Verbindung n:verbindungen){			
			n.draw(this.getGraphics());
		}			
	}
	
	@Override
	public void paint( Graphics g ){		
		super.paint( g );	   
		zeichneVerbindungen();
		if (this.imageAsString != null){
			this.setImage();
			System.out.println("paint");
		}
	  }
	
	private void setImage(){		
		Image img = new ImageIcon(Base64.base64ToByteArray(this.imageAsString)).getImage();
		this.getGraphics().drawImage(img, 0, 0, null);
	}
	
	/**
	 * Fuegt ein neues Element zur liste hinzu,
	 * falls es nicht schon vorhanden ist
	 * @param e Element welches hinzugefuegt werden soll
	 * @param pos Point-Objekt mit Koordinaten fuer Position des Elementes
	 */
	public void addElement(DragElement e, Point pos){
		this.setBase64String(null);
		DragElement neuesElement = null;
		// element schon auf PanelSpielplatz?
		for (DragElement n: elemente){
			if (n.getId() == e.getId()){			
				neuesElement = n;
				break;
			}
		}// wenn ja, dann nur Position ändern
	    if (neuesElement != null){
	    	neuesElement.setLocation(pos);	    	
	    } // sonst ein neues Element anzeigen
	    else{
	    	// vom AuswahlElement Anzahl veringern
	    	for(DragElement n:auswahlElemente){
	    		if (n.getCaption().equals(e.getCaption())){
	    			// wenn Anzahl = 0, dann abbrechen, da Aktion nicht erlaubt ist
	    			if (n.getAnz()==0) return;
	    			n.decAnz();	    			
	    			break;
	    		}
	    	}
	    	// neues Element erzeugen
	    	neuesElement = new DragElement(e.getCaption(),null, listener);		
			//neuesElement.setBounds(0,0, auswahlElemente.get(0).getWidth()+70, auswahlElemente.get(0).getHeight());
			// passt so besser zur Mausposition ;)
			pos.y -= 5; 
			neuesElement.setLocation(pos);
			// zur Liste hinzufuegen und an PanelSpielplatz uebergeben
			elemente.add(neuesElement);				
			this.add(neuesElement);
			// MouseListener hinzufuegen
		    neuesElement.addMouseListener(listener);			
	    }
	    // Panel neu zeichnen
	    this.repaint();
	}
	
	/**
	 * Loescht ein Element und setzt dessen Anzahl zurueck
	 * @param e Element welches gelöscht werden soll
	 */
	public void removeElement(DragElement e){			
		for (DragElement n:auswahlElemente){
			if (n.getCaption().equals(e.getCaption()))
				n.incAnz();
		}
		elemente.remove(e);
		// alle Verbindungen mit diesem Element loeschen
		deleteVerbindung(e);
		this.remove(e);
		// Panel neu zeichnen
		this.repaint();
	}	
	
	/**
	 * Fuegt eine Linie zur Liste hinzu
	 * @param linie Verbindung welche hinzugefuegt werden soll
	 */
	public void addVerbindung(Verbindung linie){
		// prüfen, dass sie nicht schon vorhanden ist...***********************************************************
		for(Verbindung n:verbindungen){
			if (n.find(linie)){
				this.repaint();
				return;
			}
		}
		verbindungen.add(linie);
		this.repaint();
	}
	
	/**
	 * Loescht alle Verbindungen, die zum Element gehoeren
	 * @param e Element dessen Verbindungen geloescht werden sollen
	 */
	private void deleteVerbindung(DragElement e){
		List<Verbindung> clear = new ArrayList<Verbindung>();
		// finde alle Verbindungen zum Element e
		for(Verbindung n: verbindungen){
			if (n.find(e)){
				clear.add(n);
			}
		}
		verbindungen.removeAll(clear);
		this.repaint();
	}
	
	/**
	 * Loescht alle Elemente, die auf Panel dargestellt werden 
	 * und setzt Anzahl der AuswahlElemente zurueck
	 */
	public void clear(){
		this.removeAll();
		// Anzahl aller AuswahlElemente zuruecksetzen
		for(DragElement n:auswahlElemente){
			n.reset();
		}
		elemente.clear();
		verbindungen.clear();
		this.repaint();
	}
	
	// getter and setter
	
	public List<DragElement> getElemente() {
		return elemente;
	}

	public void setElemente(List<DragElement> elemente) {
		this.elemente = elemente;
	}

	public MyMouseListener getListener() {
		return listener;
	}

	public void setListener(MyMouseListener listener) {
		this.listener = listener;
	}

	public List<Verbindung> getVerbindungen() {
		return verbindungen;
	}

	public void setVerbindungen(List<Verbindung> verbindungen) {
		this.verbindungen = verbindungen;
	}

	public List<DragElement> getAuswahlElemente() {
		return auswahlElemente;
	}

	public String getBase64StringFromImage(){
		BufferedImage img=new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
		this.paint(img.getGraphics());//dirty hack :)
		  ByteArrayOutputStream bos=new ByteArrayOutputStream();
		  try {
			ImageIO.write(img,"png",bos);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		  return Base64.byteArrayToBase64(bos.toByteArray());	
	}
	
	public void setBase64String(String base64){
		this.imageAsString = base64;
	}
	
	
}
