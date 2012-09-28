package com.spiru.dev.rtypeTask_addon;

import java.util.List;

import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;

public interface SubTasklet_RtypeTask extends AddOnSubTasklet {
	

	public String getAnswer();
	//public String getLastCorrectedAnswer();

	//public String getGroupingGradeDoc();
	//public double getGroupingScore();

	public int getTextFieldWidth();
	public int getTextFieldHeight();
	
	public String getSelectionText();
	public String getSelectionHint();
	public List<String> getSelectionAnswers();
	public int getCountQuestions();
	public String getQuestionProblem(int number);
	public String getQuestionHint(int number);
	public List<String> getQuestionsAnswers(int question);
	public String getHandlingSolution();
	
}


