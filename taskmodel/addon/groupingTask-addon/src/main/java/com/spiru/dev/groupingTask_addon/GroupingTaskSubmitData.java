package com.spiru.dev.groupingTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;

public class GroupingTaskSubmitData implements SubmitData {

	private String solution;

	public GroupingTaskSubmitData(String solution) {
		this.solution=solution;
	}

	public String getAnswer() {
		return this.solution;
	}
}
