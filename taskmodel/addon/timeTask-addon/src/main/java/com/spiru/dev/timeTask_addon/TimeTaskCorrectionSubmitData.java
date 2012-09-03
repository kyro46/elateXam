package com.spiru.dev.timeTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.correctionsubmitdata.CorrectionSubmitDataImpl;

public class TimeTaskCorrectionSubmitData extends CorrectionSubmitDataImpl {

	private float points;

	public TimeTaskCorrectionSubmitData(float points) {
		this.points=points;
	}
	public float getPoints() {
		return this.points;
	}

}
