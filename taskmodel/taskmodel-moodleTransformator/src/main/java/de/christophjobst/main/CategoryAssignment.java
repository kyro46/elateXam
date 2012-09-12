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

public class CategoryAssignment {

	// Alle Category-Blöcke auf einer einzigen Ebene
	public static ComplexTaskDef assignFlatCategories(
			ComplexTaskDef complexTaskDef,
			List<CategoryManager> categoryManagerList) {
		for (CategoryManager categoryManager : categoryManagerList) {
			complexTaskDef.getCategory()
					.add(categoryManager.generateCategory());
		}
		return complexTaskDef;
	}
}
