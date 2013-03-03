/**
 * Programm zur Konvertierung von aus Moodle exportierten �bungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package de.christophjobst.converter;

import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import generated.Quiz.Question;
import de.christophjobst.main.Base64Relocator;
import de.christophjobst.main.RandomIdentifierGenerator;
import de.thorstenberger.taskmodel.complex.complextaskdef.MappingSubTaskDef.Concept;
import de.thorstenberger.taskmodel.complex.complextaskdef.MappingSubTaskDef.Assignment;
import de.thorstenberger.taskmodel.complex.complextaskdef.MappingSubTaskDef;

public class MatchingToMappingConverter {

	public static MappingSubTaskDef processing(Question question)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		MappingSubTaskDef subTask = new MappingSubTaskDef();

		// Allgemeine Angaben pro Frage
		subTask.setTrash(false);
		subTask.setInteractiveFeedback(false);
		subTask.setCorrectionHint(" ");
		subTask.setHint(Base64Relocator.relocateBase64(question
				.getGeneralfeedback().getText(), question.getQuestiontext()
				.getFile()));

		// Spezielle Angaben pro Frage
		subTask.setId(question.getName().getText().toString() + "_"
				+ rand.getRandomID());

		subTask.setProblem(Base64Relocator.relocateBase64(question
				.getQuestiontext().getText(), question.getQuestiontext()
				.getFile()));

		Concept concept = new Concept();
		Assignment assignment = new Assignment();

		/*
		 * 1. lies alle assignments ein und gib ihnen eine id 2. wenn ein string
		 * schon vorhanden, tue nichts 3. lies die concepts ein und suche bei
		 * den assignments nach der Lösung 4. weise die LösungsID dem concept zu
		 */
		List<String> assignmentList = new ArrayList<String>();
		List<String> conceptList = new ArrayList<String>();
		List<String> conceptAnswerListRaw = new ArrayList<String>();
		for (int j = 0; j < question.getSubquestion().toArray().length; j++) {

			if (!assignmentList.contains(question.getSubquestion().get(j)
					.getAnswer().getText())) {
				assignmentList.add(question.getSubquestion().get(j).getAnswer()
						.getText());
			}
			if (!question.getSubquestion().get(j).getText().equals("")) {
				conceptList.add(question.getSubquestion().get(j).getText());
			}
			conceptAnswerListRaw.add(question.getSubquestion().get(j)
					.getAnswer().getText());
		}

		List<Assignment> assignmentObjectList = new ArrayList<MappingSubTaskDef.Assignment>();

		List<String> assingmentIDList = new ArrayList<String>();
		for (int j = 0; j < assignmentList.toArray().length; j++) {
			assingmentIDList.add(rand.getRandomID());
			assignment.setId(assingmentIDList.get(j));
			assignment.setName(assignmentList.get(j));
			assignmentObjectList.add(assignment);
			assignment = new Assignment();
		}

		List<String> conceptIDList = new ArrayList<String>();
		for (int j = 0; j < conceptAnswerListRaw.toArray().length; j++) {
			// if
			// (assignmentList.contains(conceptAnswerListRaw.get(j))){
			conceptIDList.add(assingmentIDList.get(assignmentList
					.indexOf(conceptAnswerListRaw.get(j))));
			// }
		}

		for (int j = 0; j < conceptList.toArray().length; j++) {
			concept.setName(Base64Relocator.relocateBase64(conceptList.get(j),
					question.getSubquestion().get(j).getFile()));
			concept.getCorrectAssignmentID().add(conceptIDList.get(j));
			subTask.getConcept().add(concept);
			concept = new Concept();

		}

		// Assignments zufällig anordnen
		Collections.shuffle(assignmentObjectList);

		// Assignemntobjekte der SubTask zuordnen
		for (int j = 0; j < assignmentObjectList.toArray().length; j++) {
			subTask.getAssignment().add(assignmentObjectList.get(j));
		}

		return subTask;
	}

}
