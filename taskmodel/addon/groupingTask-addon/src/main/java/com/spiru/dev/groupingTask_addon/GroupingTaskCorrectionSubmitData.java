package com.spiru.dev.groupingTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.correctionsubmitdata.CorrectionSubmitDataImpl;

public class GroupingTaskCorrectionSubmitData extends CorrectionSubmitDataImpl {

	private float points;

	public GroupingTaskCorrectionSubmitData(float points) {
		this.points=points;
	}
	public float getPoints() {
		return this.points;
	}

}
