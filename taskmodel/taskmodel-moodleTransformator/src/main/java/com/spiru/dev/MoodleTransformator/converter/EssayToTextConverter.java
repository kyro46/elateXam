/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package com.spiru.dev.MoodleTransformator.converter;

import com.spiru.dev.MoodleTransformator.main.RandomIdentifierGenerator;

import de.thorstenberger.taskmodel.complex.jaxb.TextSubTaskDef;
import generated.Quiz.Question;

public class EssayToTextConverter {

	public static TextSubTaskDef processing(Question question) {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		TextSubTaskDef subTask = new TextSubTaskDef();

			subTask.setProblem(question.getQuestiontext().getText().toString());
			subTask.setHint(question.getName().getText().toString());

			subTask.setTrash(false);
			subTask.setInteractiveFeedback(false);
			subTask.setId(question.getName().getText().toString() + "_"
					+ rand.getRandomID());

		return subTask;
	}

}
