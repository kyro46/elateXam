package com.spiru.dev.speechTestTask_addon;


import de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet;
import de.thorstenberger.taskmodel.view.AddonSubTaskViewFactory;
import de.thorstenberger.taskmodel.view.SubTaskView;

public class SpeechTestTaskSubTaskViewFactory implements AddonSubTaskViewFactory {

    public String getAddonTaskType() {
    	
    	System.out.println("ViewSubtasklet speechTestTask successfully loaded");
    	return "speechTesttask";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.thorstenberger.taskmodel.view.SubTaskViewFactory#getSubTaskView(de
     * .thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet)
     */
    public SubTaskView getSubTaskView(final SubTasklet subTasklet) {
        return new SubTaskView_SpeechTestTask((SubTasklet_SpeechTestTask) subTasklet);
    }
}
