package com.spiru.dev.groupingTask_addon;


import de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet;
import de.thorstenberger.taskmodel.view.AddonSubTaskViewFactory;
import de.thorstenberger.taskmodel.view.SubTaskView;

public class GroupingTaskSubTaskViewFactory implements AddonSubTaskViewFactory {

    public String getAddonTaskType() {
    	
    	System.out.println("ViewSubtasklet groupingTask successfully loaded");
    	return "groupingTask";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.thorstenberger.taskmodel.view.SubTaskViewFactory#getSubTaskView(de
     * .thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet)
     */
    public SubTaskView getSubTaskView(final SubTasklet subTasklet) {
        return new SubTaskView_GroupingTask((SubTasklet_GroupingTask) subTasklet);
    }
}
