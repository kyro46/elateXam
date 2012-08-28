package com.spiru.dev.compareTextTask_addon;


import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;

public interface SubTasklet_CompareTextTask extends AddOnSubTasklet {

	public String getAnswer();
	public String getLastCorrectedAnswer();
	public String getTagsString();
	public String getInitialText();
	public String getSampleSolution();
	

}


