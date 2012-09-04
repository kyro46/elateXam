package com.spiru.dev.groupingTask_addon;

import java.util.List;

import org.jdom.Element;

import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;

public interface SubTasklet_GroupingTask extends AddOnSubTasklet {
	

	public String getAnswer();
	public String getLastCorrectedAnswer();

	public String getAnordnungGradeDoc();
	public double getAnordnungScore();

	public int getTextFieldWidth();
	public int getTextFieldHeight();
	
	public List<Element> getChildren(Element parent, String name);



}


