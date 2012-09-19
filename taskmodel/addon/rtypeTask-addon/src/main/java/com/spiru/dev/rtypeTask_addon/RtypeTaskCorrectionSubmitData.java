package com.spiru.dev.rtypeTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.correctionsubmitdata.CorrectionSubmitDataImpl;

public class RtypeTaskCorrectionSubmitData extends CorrectionSubmitDataImpl {

	private float points;

	public RtypeTaskCorrectionSubmitData(float points) {
		this.points=points;
	}
	public float getPoints() {
		return this.points;
	}

}
