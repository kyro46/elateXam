package com.spiru.dev.groupingTask_addon;


import de.thorstenberger.taskmodel.complex.addon.AddOnSubTaskletFactory;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot.CorrectionModeType;
import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;
import de.thorstenberger.taskmodel.complex.jaxb.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskHandling.Try.Page.AddonSubTask;

public class GroupingTaskAddOnSubTaskletFactoryImpl  implements
AddOnSubTaskletFactory {

public AddOnSubTasklet createAddOnSubTasklet( Object subTaskDef, Object subTask, CorrectionModeType correctionMode, float reachablePoints) {
return new SubTasklet_GroupingTaskImpl((AddonSubTaskDef)subTaskDef,(AddonSubTask)subTask, correctionMode, reachablePoints);
}

public String getAddonTaskType() {
	System.out.println("Subtasklet groupingTask successfully loaded");
return "groupingTask";
}

}
