package com.spiru.dev.compareTextTask_addon;

import de.thorstenberger.taskmodel.complex.addon.AddOnSubTaskletFactory;
import de.thorstenberger.taskmodel.complex.complextaskdef.Block;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot;
import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;
import de.thorstenberger.taskmodel.complex.jaxb.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskHandling.Try.Page.AddonSubTask;

public class CompareTextTaskAddOnSubTaskletFactoryImpl implements AddOnSubTaskletFactory {

	@Override
	public AddOnSubTasklet createAddOnSubTasklet(ComplexTaskDefRoot root,Block block, Object subTaskDef, Object subTask) {
		return new SubTasklet_CompareTextTaskImpl(root,block,(AddonSubTaskDef)subTaskDef,(AddonSubTask)subTask);
	}

	@Override
	public String getAddonTaskType() {
		return "comparetexttask";
	}

}
