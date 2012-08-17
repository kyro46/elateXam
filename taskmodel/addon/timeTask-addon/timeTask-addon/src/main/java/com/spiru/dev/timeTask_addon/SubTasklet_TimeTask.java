package com.spiru.dev.timeTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;

public interface SubTasklet_TimeTask extends AddOnSubTasklet {
	

	public String getAnswer();
	public String getLastCorrectedAnswer();

	public String getAnordnungGradeDoc();
	public double getAnordnungScore();

	public int getTextFieldWidth();
	public int getTextFieldHeight();



}


