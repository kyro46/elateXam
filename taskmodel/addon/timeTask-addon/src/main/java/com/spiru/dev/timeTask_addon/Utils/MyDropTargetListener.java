package com.spiru.dev.timeTask_addon.Utils;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

public class MyDropTargetListener extends DropTargetAdapter {

	/** A DropTarget is an object responsible for 
	 * accepting drops in an drag and drop operation. 
	 */
	private DropTarget dropTarget;
	/** Objekt dessen Mathoden aufgerufen werden sollen */
    private JPanelPlayGround panel;
    
    /**
     * Konstruktor fuer einen Drag'n Drop Listener
     * @param panel Panel, dessen Methoden auferufen werden sollen
     */
    public MyDropTargetListener(JPanelPlayGround panel) {
        this.panel = panel;
        /* create a drop target object
        c - The Component with which this DropTarget is associated
        ops - The default acceptable actions for this DropTarget
        dtl - The DropTargetListener for this DropTarget
        act - Is the DropTarget accepting drops.
        fm - The FlavorMap to use, or null for the default FlavorMap
        */ 
        dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, 
            this, true, null);
    }
    
	//@Override
	public void drop(DropTargetDropEvent event) {		
		// wenn Maus losgelassen wurde und ein Objekt gedropt werden soll
		panel.setModified();
        try {              
            Transferable tr = event.getTransferable();
            // erzeuge Symbol
            Symbol symbol = (Symbol) tr.getTransferData(TransferableElement.elementFlavor);            
              if (event.isDataFlavorSupported(TransferableElement.elementFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                // Symbol hinzufuegen
                panel.moveSymbol(symbol, event.getLocation());
                // Drop beenden
                event.dropComplete(true);                               
              } else
            	  event.rejectDrop();
          } catch (Exception e) {
            e.printStackTrace();
            event.rejectDrop();
          }              
	}

}
