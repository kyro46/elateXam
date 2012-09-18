package com.spriu.dev.rtypeTask_addon;

import java.util.List;

import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;

public interface SubTasklet_RtypeTask extends AddOnSubTasklet {
	

	public String getAnswer();
	//public String getLastCorrectedAnswer();

	//public String getGroupingGradeDoc();
	//public double getGroupingScore();

	public int getTextFieldWidth();
	public int getTextFieldHeight();
	
	
	public boolean loadFromHandling();
	public String getImage();
	
}


