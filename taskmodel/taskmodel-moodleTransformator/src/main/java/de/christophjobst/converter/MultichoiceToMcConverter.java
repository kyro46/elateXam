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

	public static McSubTaskDef processing(Question question) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		McSubTaskDef subTask = new McSubTaskDef();

		McSubTaskDef.Correct correct = new McSubTaskDef.Correct();
		McSubTaskDef.Incorrect incorrect = new McSubTaskDef.Incorrect();

		// Allgemeine Angaben pro Frage
		subTask.setTrash(false);
		subTask.setInteractiveFeedback(false);
		subTask.setPreserveOrderOfAnswers(false);
		subTask.setDisplayedAnswers(question.getAnswer().toArray().length);

		subTask.setCategory(question.getSingle().equals("true") ? "singleSelect"
				: "multipleSelect");

		// Spezielle Angaben pro Frage
		subTask.setProblem(Base64Relocator.relocateBase64(question.getQuestiontext()));
		subTask.setHint(question.getName().getText().toString());
		subTask.setId(question.getName().getText().toString() + "_"
				+ rand.getRandomID());
		int correctAnswerCount = 0;
		for (int j = 0; j < question.getAnswer().toArray().length; j++) {

			if (!question.getAnswer().get(j).getFraction().equals("0")) {
				correct.setValue(question.getAnswer().get(j).getText());
				correct.setId(rand.getRandomID());
				correctAnswerCount++;
				subTask.getCorrectOrIncorrect().add(correct);
				correct = new McSubTaskDef.Correct();

			} else {
				incorrect.setValue(question.getAnswer().get(j).getText());
				incorrect.setId(rand.getRandomID());
				subTask.getCorrectOrIncorrect().add(incorrect);
				incorrect = new McSubTaskDef.Incorrect();
			}

		}
		
		/* Sonderfall: Singlechoice-Aufgabe mit mehreren angezeigten korrekten L�sungen
		 * Kann so nicht abgebildet werden - daher Umwandlung zur multichoice-Aufgabe
		 */
		if (correctAnswerCount > 1) {
			subTask.setCategory("multipleSelect");
		}

		return subTask;
	}
}
