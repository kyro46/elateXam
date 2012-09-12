package com.spiru.dev.compareTextTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.correctionsubmitdata.CorrectionSubmitDataImpl;

public class CompareTextTaskCorrectionSubmitData extends CorrectionSubmitDataImpl {

	private float points;

	public CompareTextTaskCorrectionSubmitData(float points) {
		this.points=points;
	}
	public float getPoints() {
		return this.points;
	}

}
