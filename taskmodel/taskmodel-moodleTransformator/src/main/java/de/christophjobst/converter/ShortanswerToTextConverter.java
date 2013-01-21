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

import de.christophjobst.main.Base64Relocator;

import de.christophjobst.main.RandomIdentifierGenerator;
import de.thorstenberger.taskmodel.complex.complextaskdef.TextSubTaskDef;
import generated.Quiz.Question;

public class ShortanswerToTextConverter {

	public static TextSubTaskDef processing(Question question)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		TextSubTaskDef subTask = new TextSubTaskDef();

		subTask = new TextSubTaskDef();

		subTask.setHint(Base64Relocator.relocateBase64(question
				.getGeneralfeedback().getText(), question.getQuestiontext()
				.getFile()));

		String answer = "";

		for(int i=0; i<question.getAnswer().size(); i++) {
		 answer = answer + question.getAnswer().get(i).getText() + "; ";
		}
		
		subTask.setCorrectionHint(answer);

		// Allgemeine Angaben pro Frage
		subTask.setTrash(false);
		subTask.setInteractiveFeedback(false);

		// Spezielle Angaben pro Frage
		subTask.setProblem(Base64Relocator.relocateBase64(question
				.getQuestiontext().getText(), question.getQuestiontext()
				.getFile()));
		subTask.setId(question.getName().getText().toString() + "_"
				+ rand.getRandomID());

		return subTask;
	}

}
