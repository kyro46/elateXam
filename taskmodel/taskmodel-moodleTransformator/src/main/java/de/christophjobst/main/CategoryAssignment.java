/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package de.christophjobst.main;

import java.util.List;

import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category;

public class CategoryAssignment {

	// Alle Category-Blöcke auf einer einzigen Ebene
	public static ComplexTaskDef assignFlatCategories(
			ComplexTaskDef complex,
			List<CategoryManager> categoryManagerList) {
		
		
		ComplexTaskDef complexTaskDef = new ComplexTaskDef();
		
		try {
			complexTaskDef.setRevisions(complex.getRevisions());
			complexTaskDef.setConfig(complex.getConfig());
			complexTaskDef.setDescription(complex.getDescription());
			complexTaskDef.setID(complex.getID());
			complexTaskDef.setShowHandlingHintsBeforeStart(complex.isShowHandlingHintsBeforeStart());
			complexTaskDef.setStartText(complex.getStartText());
			complexTaskDef.setTitle(complex.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (CategoryManager categoryManager : categoryManagerList) {
			// Nur Categorien übernehmen, die mindestens 1 Taskblock haben
			
			Category category = categoryManager.getCategory();
			if (category
					.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().toArray().length != 0) {
				complexTaskDef.getCategory().add(category);
			}
		}
		return complexTaskDef;
	}
}
