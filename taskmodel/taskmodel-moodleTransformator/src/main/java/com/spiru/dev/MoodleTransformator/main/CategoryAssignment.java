/**
 * Programm zur Konvertierung von aus Moodle exportierten �bungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package com.spiru.dev.MoodleTransformator.main;

import java.util.List;

import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef;

public class CategoryAssignment {

	// Alle Category-Bl�cke auf einer einzigen Ebene
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
