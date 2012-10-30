package com.spiru.dev.groupingTask_addon.Utils;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;

import com.spiru.dev.groupingTask_addon.GroupingTaskAddOnJPanel;

/**
 * MouseListener ist zustaendig fuer alle Mausaktionen
 * @author Yves
 *
 */
public class MyMouseListener implements MouseListener {

	/** selektiertes Element */
	private DragElement element = null;
	/** Zweites selektiertes Element. Fuer das Erstellen einer Verbindung wichtig. */
	private DragElement element2 = null;
	/** Panel auf dem sich Aktionen auswirken sollen */
	private PanelSpielplatz spielplatz;
	
	/**
	 * setzt Panel
	 * @param spielplatz Panel auf dem Aktionen Auswirkungen haben sollen
	 */
	public void setPanel(PanelSpielplatz spielplatz){
		this.spielplatz = spielplatz;
	}
	
	/**
	 * Prueft, ob eine Verbindung angeklickt wurde
	 * @param maus Pointer mit Mauskoordinaten
	 */
	private void clickLine(Point maus){
		List<Verbindung> verbindungen = spielplatz.getVerbindungen();
		// alte Markierung aufheben
		for(Verbindung n:verbindungen){
			n.setMarkiert(false);
		}
		for(Verbindung n:verbindungen){
			if (n.isClickLine(maus)){
				n.setMarkiert(true);
				break;
			}
		}
		spielplatz.repaint();
	}
	
	public void mouseClicked(MouseEvent event) {	
		spielplatz.setModified();		
		Object objekt = event.getSource();
		// Klick auf Panel Spielplatz
		if (objekt instanceof PanelSpielplatz){			
			clickLine(event.getPoint());
			// alte Selektion aufheben
			if (element != null)
				element.markiereElement(false);
			if (element2 != null)
				element2.markiereElement(false);
			element = null;
			element2 = null;
			return;
		}
		// wenn ein Element, dann markiere dieses 
		if (objekt instanceof DragElement){
			// letzte Markierung aufheben
			if (element != null)
				element.markiereElement(false);
			// neue Markierung setzen
			element = (DragElement) objekt;
			element.markiereElement(true);	
			if (element == element2){
				element.markiereElement(false);
				element2.markiereElement(false);
				element = null;
				element2 = null;
			}
			// alte Selektierung von Verbindung aufheben
			for (Verbindung n: spielplatz.getVerbindungen()){
				n.setMarkiert(false);
			}
		} 
		// ist es ein Button?
		if (objekt instanceof JButton){
			JButton button = (JButton) objekt;
			// DeleteButton?
			if (button.getActionCommand().equals(GroupingTaskAddOnJPanel.DELETE_ACTION)){
				// wenn ein Element selektiert
				if (element != null){
					// loesche Element
					spielplatz.removeElement(element);
					element = null;
					element2 = null;
				}
				else{
					for (Verbindung n: spielplatz.getVerbindungen()){
						if (n.isMarkiert()){
							spielplatz.getVerbindungen().remove(n);
							spielplatz.repaint();
							break;
						}
					}
				}
			}
			// alles loeschen?
			if (button.getActionCommand().equals(GroupingTaskAddOnJPanel.DELETE_ALL_ACTION)){
				element2 = null;
				element = null;
				spielplatz.clear();
			}	
			if (button.getActionCommand().equals(GroupingTaskAddOnJPanel.PLUS_ACTION)){
				// siehe oben, geht aber nicht...
				element2 = ((DragElement)(button.getParent()));				
				if (element != null)
					element.markiereElement(false);
				element = null;
				element2.markiereElement(true);
			}
		}
		// wenn VerbindungsButton gedrueckt wurde und zweites Element selektiert,
		// dann erstelle neue Verbindung
		if (element2 != null && element != null && element != element2){
			Verbindung bindung = new Verbindung(element, element2);
			spielplatz.addVerbindung(bindung);			
			element.markiereElement(false);
			element2.markiereElement(false);
			element2 = null;
			element = null;
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		//
	}
}
