package com.spiru.dev.timeTaskProfessor_addon.Utils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TransferableElement implements Transferable {

	protected static DataFlavor elementFlavor =
	        new DataFlavor(Symbol.class, "A Symbol Object");
	
    protected static DataFlavor[] supportedFlavors = {
        elementFlavor
    };
	
    /** Element, das uebertragen werden soll */
    private Symbol symbol;
    
    /**
     * Konstruktor eines zu uebertragendes Objektes
     * @param element Elemnt mit Drag'n Drop
     */
    public TransferableElement(Symbol symbol) { 
    	this.symbol = symbol;     	
    }
    
	public Symbol getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (flavor.equals(elementFlavor))
	         return symbol;
	     else 
	         throw new UnsupportedFlavorException(flavor);
	}

	public DataFlavor[] getTransferDataFlavors() {		 		
		return supportedFlavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.equals(elementFlavor)) 
			return true;
		return false;
	}

}
