package com.spiru.dev.compareTextTask_addon;


import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;

public interface SubTasklet_CompareTextTask extends AddOnSubTasklet {
	public String getTagsString();
	public String getInitialText();
	public String getResult();
}


