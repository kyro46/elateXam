/**
 * Programm zur Konvertierung von aus Moodle exportierten �bungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package de.christophjobst.converter;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import generated.Quiz.Question;
import de.christophjobst.main.Base64Relocator;
import de.christophjobst.main.RandomIdentifierGenerator;
import de.thorstenberger.taskmodel.complex.complextaskdef.McSubTaskDef;

public class MultichoiceToMcConverter {

	public static McSubTaskDef processing(Question question)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		McSubTaskDef subTask = new McSubTaskDef();

		McSubTaskDef.Correct correct = new McSubTaskDef.Correct();
		McSubTaskDef.Incorrect incorrect = new McSubTaskDef.Incorrect();

		// Allgemeine Angaben pro Frage
		subTask.setTrash(false);
		subTask.setInteractiveFeedback(false);
		subTask.setPreserveOrderOfAnswers(!question.isShuffleanswers());

		subTask.setCategory(question.getSingle().equals("true") ? "singleSelect"
				: "multipleSelect");

		if (question.getSingle().equals("false")) {

			try {

				if (question.getNumRightMin() < question.getNumRightMax()) {
					subTask.setMinCorrectAnswers(question.getNumRightMin());
					subTask.setMaxCorrectAnswers(question.getNumRightMax());
				}

				if (question.getNumShown() == 0) {
					subTask.setDisplayedAnswers(question.getNumShown());
				} else {
					subTask.setDisplayedAnswers(question.getAnswer().toArray().length);
				}
			} catch (Exception e) {
				subTask.setDisplayedAnswers(question.getAnswer().toArray().length);
			}
		} else {
			try {
				if (question.getNumShown() == 0) {
					subTask.setDisplayedAnswers(question.getNumShown());
				} else {
					subTask.setDisplayedAnswers(question.getAnswer().toArray().length);
				}
			} catch (Exception e) {
				subTask.setDisplayedAnswers(question.getAnswer().toArray().length);
			}
		}

		// Spezielle Angaben pro Frage
		subTask.setProblem(Base64Relocator.relocateBase64(question
				.getQuestiontext().getText(), question.getQuestiontext()
				.getFile()));
		subTask.setHint(Base64Relocator.relocateBase64(question
				.getGeneralfeedback().getText(), question.getQuestiontext()
				.getFile()));
		subTask.setId(question.getName().getText().toString() + "_"
				+ rand.getRandomID());
		int correctAnswerCount = 0;
		for (int j = 0; j < question.getAnswer().toArray().length; j++) {

			if (!question.getAnswer().get(j).getFraction().equals("0")) {
				correct.setValue(Base64Relocator.relocateBase64(question
						.getAnswer().get(j).getText(), question.getAnswer()
						.get(j).getFile()));
				correct.setId(rand.getRandomID());
				correctAnswerCount++;
				subTask.getCorrectOrIncorrect().add(correct);
				correct = new McSubTaskDef.Correct();

			} else {
				incorrect.setValue(Base64Relocator.relocateBase64(question
						.getAnswer().get(j).getText(), question.getAnswer()
						.get(j).getFile()));
				incorrect.setId(rand.getRandomID());
				subTask.getCorrectOrIncorrect().add(incorrect);
				incorrect = new McSubTaskDef.Incorrect();
			}

		}

		/*
		 * Sonderfall: Singlechoice-Aufgabe mit mehreren angezeigten korrekten
		 * Lösungen Kann so nicht abgebildet werden - daher Umwandlung zur
		 * multichoice-Aufgabe
		 */
		if (correctAnswerCount > 1) {
			subTask.setCategory("multipleSelect");
		}

		return subTask;
	}
}
