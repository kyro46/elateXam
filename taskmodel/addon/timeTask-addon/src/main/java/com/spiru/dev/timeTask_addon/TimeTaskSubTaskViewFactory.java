package com.spiru.dev.timeTask_addon;


import de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet;
import de.thorstenberger.taskmodel.view.AddonSubTaskViewFactory;
import de.thorstenberger.taskmodel.view.SubTaskView;

public class TimeTaskSubTaskViewFactory implements AddonSubTaskViewFactory {

    public String getAddonTaskType() {
    	
    	System.out.println("SubtaskletViewFactory CompareTextTask successfully loaded");
    	return "timeTask";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.thorstenberger.taskmodel.view.SubTaskViewFactory#getSubTaskView(de
     * .thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet)
     */
    public SubTaskView getSubTaskView(final SubTasklet subTasklet) {
        return new SubTaskView_TimeTask((SubTasklet_TimeTask) subTasklet);
    }
}
