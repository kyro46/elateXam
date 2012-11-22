package com.spiru.dev.compareTextTask_addon;


import javax.management.modelmbean.XMLParseException;

import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;

public interface SubTasklet_CompareTextTask extends AddOnSubTasklet {
	public String getMementoAsBase64();
	public String getResultAsBase64() throws XMLParseException;
	public String getResult() throws XMLParseException;
}


