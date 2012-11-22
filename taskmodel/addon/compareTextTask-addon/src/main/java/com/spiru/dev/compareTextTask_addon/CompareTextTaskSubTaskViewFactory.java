package com.spiru.dev.compareTextTask_addon;


import de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet;
import de.thorstenberger.taskmodel.view.AddonSubTaskViewFactory;
import de.thorstenberger.taskmodel.view.SubTaskView;

public class CompareTextTaskSubTaskViewFactory implements AddonSubTaskViewFactory {

	@Override
	public String getAddonTaskType() {
		return "comparetexttask";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.thorstenberger.taskmodel.view.SubTaskViewFactory#getSubTaskView(de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet)
	 */
	@Override
	public SubTaskView getSubTaskView(final SubTasklet subTasklet) {
		return new SubTaskView_CompareTextTask((SubTasklet_CompareTextTask) subTasklet);
	}
}
