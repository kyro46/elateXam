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

public class EssayToTextConverter {

	public static TextSubTaskDef processing(Question question)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		TextSubTaskDef subTask = new TextSubTaskDef();

		subTask.setProblem(Base64Relocator.relocateBase64(question
				.getQuestiontext().getText(), question.getQuestiontext()
				.getFile()));
		subTask.setHint(Base64Relocator.relocateBase64(question
				.getGeneralfeedback().getText(), question.getQuestiontext()
				.getFile()));

		try {
			subTask.setCorrectionHint(Base64Relocator.relocateBase64(question
					.getGraderinfo().getText(), question.getGraderinfo()
					.getFile()));
		} catch (NullPointerException e) {
			subTask.setCorrectionHint("Kein Muster hinterlegt.");

		}

		try {

			subTask.setTextFieldHeight(Integer.parseInt(question
					.getResponsefieldlines()));
		} catch (Exception e) {
			subTask.setTextFieldHeight(15);
		}

		try {

			subTask.setTextFieldWidth(Integer.parseInt(question
					.getResponsefieldwidth()));
		} catch (Exception e) {
			subTask.setTextFieldWidth(60);
		}

		
		try {
			subTask.setInitialTextFieldValue(question.getInitialtextfieldvalue());
		} catch (NullPointerException e) {
			//Do nothing
		}	
		
		
		
		subTask.setTrash(false);
		subTask.setInteractiveFeedback(false);
		subTask.setId(question.getName().getText().toString() + "_"
				+ rand.getRandomID());

		return subTask;
	}

}
