package com.spriu.dev.rtypeTask_addon;


import de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet;
import de.thorstenberger.taskmodel.view.AddonSubTaskViewFactory;
import de.thorstenberger.taskmodel.view.SubTaskView;

public class RtypeTaskSubTaskViewFactory implements AddonSubTaskViewFactory {

    public String getAddonTaskType() {
    	
    	System.out.println("ViewSubtasklet rtypTask successfully loaded");
    	return "rtyptask";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.thorstenberger.taskmodel.view.SubTaskViewFactory#getSubTaskView(de
     * .thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet)
     */
    public SubTaskView getSubTaskView(final SubTasklet subTasklet) {
        return new SubTaskView_RtypeTask((SubTasklet_RtypeTask) subTasklet);
    }
}
