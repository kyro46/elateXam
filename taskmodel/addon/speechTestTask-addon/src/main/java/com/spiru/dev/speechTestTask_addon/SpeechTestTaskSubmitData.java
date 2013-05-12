package com.spiru.dev.speechTestTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;

public class SpeechTestTaskSubmitData implements SubmitData {

	private String solution;

	public SpeechTestTaskSubmitData(String solution) {
		this.solution=solution;
	}

	public String getAnswer() {
		return this.solution;
	}
}
