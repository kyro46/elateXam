/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
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

public class TruefalseToMcConverter {

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
		subTask.setPreserveOrderOfAnswers(false);
		subTask.setDisplayedAnswers(2);

		subTask.setCategory("singleSelect");

		// Spezielle Angaben pro Frage
		subTask.setProblem(Base64Relocator.relocateBase64(question
				.getQuestiontext().getText(), question.getQuestiontext()
				.getFile()));
		subTask.setHint(Base64Relocator.relocateBase64(question
				.getGeneralfeedback().getText(), question.getQuestiontext()
				.getFile()));
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

		return subTask;
	}

}
