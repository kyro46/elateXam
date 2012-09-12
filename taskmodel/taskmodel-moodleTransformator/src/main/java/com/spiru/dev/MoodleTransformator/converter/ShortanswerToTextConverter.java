/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package com.spiru.dev.MoodleTransformator.converter;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.spiru.dev.MoodleTransformator.main.RandomIdentifierGenerator;

import com.spiru.dev.MoodleTransformator.main.Base64Relocator;
import de.thorstenberger.taskmodel.complex.jaxb.TextSubTaskDef;
import generated.Quiz.Question;

public class ShortanswerToTextConverter {

	public static TextSubTaskDef processing(Question question) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		TextSubTaskDef subTask = new TextSubTaskDef();

				subTask = new TextSubTaskDef();

				subTask.setHint(question.getName().getText().toString());

				String answer = question.getAnswer()
						.get(0).getText();

				subTask.setCorrectionHint(answer);

				// Allgemeine Angaben pro Frage
				subTask.setTrash(false);
				subTask.setInteractiveFeedback(false);

				// Spezielle Angaben pro Frage
				subTask.setProblem(Base64Relocator.relocateBase64(question.getQuestiontext()));
				subTask.setId(question.getName()
						.getText().toString()
						+ "_" + rand.getRandomID());
		
		return subTask;
	}

}
