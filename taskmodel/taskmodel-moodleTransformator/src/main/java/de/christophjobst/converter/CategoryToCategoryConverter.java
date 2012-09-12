/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package de.christophjobst.converter;

import generated.Quiz.Question;
import de.christophjobst.main.RandomIdentifierGenerator;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category;

public class CategoryToCategoryConverter {

	public static Category processing(Question question) {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		Category category = new Category();
		
		category.setTitle(question.getCategory().getText().substring(9));
		category.setId("Kategorie_" + rand.getRandomID());
		category.setIgnoreOrderOfBlocks(false);
		category.setMixAllSubTasks(false);

		return category;
	}
}