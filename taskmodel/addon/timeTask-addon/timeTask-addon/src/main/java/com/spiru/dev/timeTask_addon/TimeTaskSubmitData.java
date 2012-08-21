package com.spiru.dev.timeTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;

public class TimeTaskSubmitData implements SubmitData {

	private String solution;

	public TimeTaskSubmitData(String solution) {
		this.solution=solution;
	}

	public String getAnswer() {
		return this.solution;
	}
}
