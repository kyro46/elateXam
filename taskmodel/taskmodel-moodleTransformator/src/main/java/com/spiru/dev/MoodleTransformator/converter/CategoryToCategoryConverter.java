/**
 * Programm zur Konvertierung von aus Moodle exportierten �bungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package com.spiru.dev.MoodleTransformator.converter;

import com.spiru.dev.MoodleTransformator.main.RandomIdentifierGenerator;

import generated.Quiz.Question;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category;

public class CategoryToCategoryConverter {

	public static Category processing(Question question) {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		Category category = new Category();
//		if (question.getType().toString().equals("category")) {
//			System.out.println("Es ist ein category.");
//		}
		
		
		category.setTitle(question.getCategory().getText().substring(9));
		category.setId("Kategorie_" + rand.getRandomID());
		category.setIgnoreOrderOfBlocks(false);
		category.setMixAllSubTasks(false);

		return category;
	}
}