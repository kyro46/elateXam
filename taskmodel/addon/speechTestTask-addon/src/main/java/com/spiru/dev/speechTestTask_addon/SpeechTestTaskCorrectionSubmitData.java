package com.spiru.dev.speechTestTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.correctionsubmitdata.CorrectionSubmitDataImpl;

public class SpeechTestTaskCorrectionSubmitData extends CorrectionSubmitDataImpl {

	private float points;

	public SpeechTestTaskCorrectionSubmitData(float points) {
		this.points=points;
	}
	public float getPoints() {
		return this.points;
	}

}
