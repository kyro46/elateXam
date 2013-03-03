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

	public static Category processing(Question question, int startmarke) {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		Category category = new Category();
		category.setTitle(question.getCategory().getText().substring(startmarke));
		category.setId("Kategorie_" + rand.getRandomID());

		//Aus dem Klausurplugin?
		if (startmarke == 0) {
			if (question.getCategory().getShuffle().toString().equals("0")){
				category.setMixAllSubTasks(false);
				category.setIgnoreOrderOfBlocks(false);
			}else {
				category.setMixAllSubTasks(true);
				category.setIgnoreOrderOfBlocks(true);
			}
		}else {
			//Normaler Frageexport -> beachte immer Reihenfolge in der Datei
			category.setMixAllSubTasks(false);
			category.setIgnoreOrderOfBlocks(false);
		}
		
		return category;
	}
}