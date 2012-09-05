/**
 * Programm zur Konvertierung von aus Moodle exportierten �bungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package com.spiru.dev.MoodleTransformator.converter;

import java.util.*;

import com.spiru.dev.MoodleTransformator.main.RandomIdentifierGenerator;

import generated.Quiz.Question;

import de.thorstenberger.taskmodel.complex.jaxb.MappingSubTaskDef.Concept;
import de.thorstenberger.taskmodel.complex.jaxb.MappingSubTaskDef.Assignment;
import de.thorstenberger.taskmodel.complex.jaxb.MappingSubTaskDef;

public class MatchingToMappingConverter {

	public static MappingSubTaskDef processing(Question question) {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		MappingSubTaskDef subTask = new MappingSubTaskDef();

//		if (question.getType().toString().equals("matching")) {
//			System.out.println("Es ist ein matching.");

			// Allgemeine Angaben pro Frage
			subTask.setTrash(false);
			subTask.setInteractiveFeedback(false);
			subTask.setCorrectionHint(" ");
			subTask.setHint(question.getName().getText().toString());

			// Spezielle Angaben pro Frage
			subTask.setId(question.getName().getText().toString() + "_"
					+ rand.getRandomID());

			subTask.setProblem(question.getQuestiontext().getText());

			Concept concept = new Concept();
			Assignment assignment = new Assignment();

			/*
			 * 1. lies alle assignments ein und gib ihnen eine id 2. wenn ein
			 * string schon vorhanden, tue nichts 3. lies die concepts ein und
			 * suche bei den assignments nach der L�sung 4. weise die L�sungsID
			 * dem concept zu
			 */
			List<String> assignmentList = new ArrayList<String>();
			List<String> conceptList = new ArrayList<String>();
			List<String> conceptAnswerListRaw = new ArrayList<String>();
			for (int j = 0; j < question.getSubquestion().toArray().length; j++) {

				if (!assignmentList.contains(question.getSubquestion().get(j)
						.getAnswer().getText())) {
					assignmentList.add(question.getSubquestion().get(j)
							.getAnswer().getText());
				}
				conceptList.add(question.getSubquestion().get(j).getText());
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
				concept.setName(conceptList.get(j));
				concept.getCorrectAssignmentID().add(conceptIDList.get(j));
				subTask.getConcept().add(concept);
				concept = new Concept();
			}

			// Assignments zuf�llig anordnen
			Collections.shuffle(assignmentObjectList);

			// Assignemntobjekte der SubTask zuordnen
			for (int j = 0; j < assignmentObjectList.toArray().length; j++) {
				subTask.getAssignment().add(assignmentObjectList.get(j));
			}

//		}

		return subTask;
	}

}
