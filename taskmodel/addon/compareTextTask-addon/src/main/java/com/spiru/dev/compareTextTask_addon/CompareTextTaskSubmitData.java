package com.spiru.dev.compareTextTask_addon;

import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;

public class CompareTextTaskSubmitData implements SubmitData {
	private String resultString;

	public CompareTextTaskSubmitData(String result) {
		resultString = result;
	}

	public String getResultString() {
		return resultString;
	}
}
