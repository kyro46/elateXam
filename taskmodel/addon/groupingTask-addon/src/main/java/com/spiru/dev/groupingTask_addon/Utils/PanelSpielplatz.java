package com.spiru.dev.groupingTask_addon.Utils;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
	}
	
	/**
	 * zeichnet alle Verbindungen
	 */
	private void zeichneVerbindungen(){		
		for(Verbindung n:verbindungen){			
			n.paint(this.getGraphics());
		}			
	}
	
	@Override
	protected void paintComponent( Graphics g ){		
	    super.paintComponent( g );	   
	    zeichneVerbindungen();
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
	    this.paint(this.getGraphics());
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
		this.paint(this.getGraphics());
	}	
	
	/**
	 * Fuegt eine Linie zur Liste hinzu
	 * @param linie Verbindung welche hinzugefuegt werden soll
	 */
	public void addVerbindung(Verbindung linie){
		// prüfen, dass sie nicht schon vorhanden ist...***********************************************************
		for(Verbindung n:verbindungen){
			if (n.find(linie)){
				return;
			}
		}
		verbindungen.add(linie);
		this.paint(this.getGraphics());
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
		this.paint(this.getGraphics());
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
		this.paint(this.getGraphics());
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

	
	
}
