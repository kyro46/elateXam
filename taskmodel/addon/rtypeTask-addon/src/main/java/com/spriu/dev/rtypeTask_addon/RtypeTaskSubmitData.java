package com.spriu.dev.rtypeTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;

public class RtypeTaskSubmitData implements SubmitData {

	private String solution;

	public RtypeTaskSubmitData(String solution) {
		this.solution=solution;
	}

	public String getAnswer() {
		return this.solution;
	}
}
