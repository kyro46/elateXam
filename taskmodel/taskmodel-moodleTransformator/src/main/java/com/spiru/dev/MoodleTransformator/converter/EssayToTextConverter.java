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

public class EssayToTextConverter {

	public static TextSubTaskDef processing(Question question) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		TextSubTaskDef subTask = new TextSubTaskDef();

			subTask.setProblem(Base64Relocator.relocateBase64(question.getQuestiontext()));
			subTask.setHint(question.getName().getText().toString());

			subTask.setTrash(false);
			subTask.setInteractiveFeedback(false);
			subTask.setId(question.getName().getText().toString() + "_"
					+ rand.getRandomID());

		return subTask;
	}

}
