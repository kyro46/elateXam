package com.spiru.dev.groupingTask_addon.Utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
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
	private List<Element> elemente;
	/** Liste mit allen zur Auswahl stehenden Elementen */
	private final List<Element> auswahlElemente;
	/** MouseListener zur Auswahl von Elementen */
	private MyMouseListener listener;
	/** Liste mit allen Verbindungen zwischen zwei Elementen */
	private List<Verbindung> verbindungen = null;
	
	/**
	 * Konstruktor fuer PanelSpielplatz
	 * @param auswahl zur Auswahl stehender Elemente
	 * @param listener MouseListener fuer Mausaktionen
	 */
	public PanelSpielplatz(List<Element> auswahl, MyMouseListener listener){
		elemente = new ArrayList<Element>();
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
	private void zeichneVerbindungen(Graphics g){		
		for(Verbindung n:verbindungen){			
			n.draw(g);
		}
		
	}
	
	@Override
	public void paint( Graphics g ){		
		super.paint( g );			
		zeichneVerbindungen(g);
		//g.drawLine(0,0,100,200);
		this.setImage();
	  }
	
	/**
	 * Fuegt ein neues Element zur liste hinzu,
	 * falls es nicht schon vorhanden ist
	 * @param e Element welches hinzugefuegt werden soll
	 * @param pos Point-Objekt mit Koordinaten fuer Position des Elementes
	 */
	public void addElement(Element e, Point pos){
		Element neuesElement = null;
		// element schon auf PanelSpielplatz?
		for (Element n: elemente){
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
	    	for(Element n:auswahlElemente){
	    		if (n.getCaption().equals(e.getCaption())){
	    			// wenn Anzahl = 0, dann abbrechen, da Aktion nicht erlaubt ist
	    			if (n.getAnz()==0) return;
	    			n.decAnz();	    			
	    			break;
	    		}
	    	}
	    	// neues Element erzeugen
	    	neuesElement = new Element(e.getCaption(),null, listener);		
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
	public void removeElement(Element e){			
		for (Element n:auswahlElemente){
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
	private void deleteVerbindung(Element e){
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
		for(Element n:auswahlElemente){
			n.reset();
		}
		elemente.clear();
		verbindungen.clear();
		this.repaint();
	}
	
	// getter and setter
	
	public List<Element> getElemente() {
		return elemente;
	}

	public void setElemente(List<Element> elemente) {
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

	public List<Element> getAuswahlElemente() {
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
	
	public void setImage(){		
		Image img = new ImageIcon(Base64.base64ToByteArray(string)).getImage();
		this.getGraphics().drawImage(img, 0, 0, null);
	}
	
	private String string = "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lg8DAEBgAQAILAAAgQUAgMACABBYAAACCwAAgQUAILAAAAQWAAACCwBAYAEACCwAAIEFAIDAAgAQWAAAAgsAAIEFACCwAAAEFgAAAgsAQGABAAgsAAAEFgCAwAIAEFgAAAILAACBBQAgsAAABBYAAAILAEBgAQAILAAABBYAgMACABBYAAACS2ABAAgsAACBBQAgsAAAEFgAAAILAEBgAQAgsAAABBYAgMACAEBgAQAILAAAgQUAILB8GAAAAgsAQGABAAgsAAAEFgCAwAIAEFgAAAgsAACBBQAgsAAAEFgAAAILAEBgAQAILAAABBYAgMACABBYAAAILAAAgQUAILAAABBYAAACCwBAYAEAILAAAAQWAIDAAgAQWAAACCwAAIEFACCwAAAQWAAAAgsAQGABACCwAAAEFgCAwAIAEFgCCwBAYAEACCwAAIEFAIDAAgAQWAAAAgsAAIEFACCwAAAEFgAAAgsAQGABAAgsAACB5cMAABBYAAACCwBAYAEAILAAAAQWAIDAAgBAYAEACCwAAIEFAIDAAgAQWAAAAgsAQGABACCwAAAEFgCAwAIAQGABAAgsAACBBQCAwAIAEFgAAAILAACBBQAgsAAABBYAgMACAEBgAQAILAAAgQUAgMACABBYAAACCwAAgQUAILAAAAQWAIDAElgAAAILAEBgAQAILAAABBYAgMACABBYAAAILAAAgQUAILAAABBYAAACCwBAYAEACCyBBQAgsAAABBYAgMACAEBgAQAILAAAgQUAgMACABBYAAACCwAAgQUAILAAAAQWAIDAAgBAYAEACCwAAIEFAIDAAgAQWAAAAgsAAIEFACCwAAAEFgAAAgsAQGABAAgsAACBBQCAwAIAEFgAAAILAACBBQAgsAAABBYAAAILAEBgAQAILAAAgSWwAAAEFgCAwAIAEFgAAAgsAACBBQAgsAAAEFgAAAILAEBgAQAgsAAABBYAgMACABBYAgsAQGABAAgsAACBBQCAwAIAEFgAAAILAACBBQAgsAAABBYAAAILAEBgAQAILAAAgeXDAAAQWAAAAgsAQGABACCwAAAEFgCAwAIAQGABAAgsAACBBQCAwAIAEFgAAAILAEBgAQAgsAAABBYAgMACAEBgAQAILAAAgQUAgMACABBYAAACCwAAgQUAILAAAAQWAIDAAgBAYAEACCwAAIEFAIDAAgAQWAAAAgsAAIEFACCwAAAEFgCAwBJYAAACCwBAYAEACCwAAAQWAIDAAgAQWAAACCwAAIEFACCwAAAQWAAAAgsAQGABAAgsHwYAgMACABBYAAACCwAAgQUAILAAAAQWAAACCwBAYAEACCwAAAQWAIDAAgAQWAAAAgsAAIEFACCwAAAEFgAAAgsAQGABAAgsAAAEFgCAwAIAEFgAAAgsAACBBQAgsAAABBYAAAILAEBgAQAILAAABBYAgMACABBYAAAILAAAgQUAILAAAASWwAIAEFgAAAILAEBgAQAgsAAABBYAgMACAEBgAQAILAAAgQUAgMACABBYAAACCwBAYPkwAAAEFgCAwAIAEFgAAAgsAACBBQAgsAAAEFgAAAILAEBgAQAgsAAABBYAgMACABBYAAAILAAAgQUAILAAABBYAAACCwBAYAEAILAAAAQWAIDAAgBAYAEACCwAgLoJLAAA8vkPN0zcikTEh1sAAAAASUVORK5CYII=";
}
