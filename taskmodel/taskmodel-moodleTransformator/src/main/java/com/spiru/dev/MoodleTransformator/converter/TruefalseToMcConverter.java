/**
 * Programm zur Konvertierung von aus Moodle exportierten �bungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package com.spiru.dev.MoodleTransformator.converter;

import com.spiru.dev.MoodleTransformator.main.RandomIdentifierGenerator;

import generated.Quiz.Question;
import de.thorstenberger.taskmodel.complex.jaxb.McSubTaskDef;

public class TruefalseToMcConverter {

	public static McSubTaskDef processing(Question question) {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		McSubTaskDef subTask = new McSubTaskDef();
		McSubTaskDef.Correct correct = new McSubTaskDef.Correct();
		McSubTaskDef.Incorrect incorrect = new McSubTaskDef.Incorrect();

//		if (question.getType().toString().equals("truefalse")) {
//			System.out.println("Es ist ein truefalse.");

			// Allgemeine Angaben pro Frage
			subTask.setTrash(false);
			subTask.setInteractiveFeedback(false);
			subTask.setPreserveOrderOfAnswers(false);
			subTask.setDisplayedAnswers(2);

			subTask.setCategory("singleSelect");

			// Spezielle Angaben pro Frage
			subTask.setProblem(question.getQuestiontext().getText().toString());
			subTask.setHint(question.getName().getText().toString());
			subTask.setTrash(false);
			subTask.setInteractiveFeedback(false);
			subTask.setId(question.getName().getText().toString() + "_"
					+ rand.getRandomID());

			correct.setId(rand.getRandomID());
			incorrect.setId(rand.getRandomID());

			if (question.getAnswer().get(0).getFraction().equals("100")) {
				correct.setValue("Wahr");
				incorrect.setValue("Falsch");
			} else {
				correct.setValue("Falsch");
				incorrect.setValue("Wahr");
			}

			subTask.getCorrectOrIncorrect().add(correct);
			subTask.getCorrectOrIncorrect().add(incorrect);
			correct = new McSubTaskDef.Correct();
			incorrect = new McSubTaskDef.Incorrect();
//		}

		return subTask;
	}

}
