/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 *
 */

package de.christophjobst.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.christophjobst.addonQuestionGetter.CompareTextTask;
import de.christophjobst.addonQuestionGetter.GroupingTask;
import de.christophjobst.addonQuestionGetter.TimeTask;
import de.christophjobst.converter.CategoryToCategoryConverter;
import de.christophjobst.converter.ClozeToClozeConverter;
import de.christophjobst.converter.EssayToTextConverter;
import de.christophjobst.converter.MatchingToMappingConverter;
import de.christophjobst.converter.MultichoiceToMcConverter;
import de.christophjobst.converter.ShortanswerToTextConverter;
import de.christophjobst.converter.TruefalseToMcConverter;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Config.CorrectionMode.Regular;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Config.CorrectionMode;
import generated.*;

public class Inputaufteiler {

	/*
	 * Quiz-Input nach Knoten der Aufgabentypen durchlaufen und den
	 * entsprechenden Konvertern zuordnen Mögliche Typen: - Essay - Cloze -
	 * Multichoice - Shortanswer - Mapping - Truefalse
	 */
	public static ComplexTaskDef inputAufteilen(Quiz quizsammlung) {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();
		Date date = new Date();

		// Klausurdatei aufsetzen - Allgemeine Angaben
		ComplexTaskDef complexTaskDef = new ComplexTaskDef();
		CorrectionMode correctionMode = new CorrectionMode();
		Regular regular = new Regular();
		ComplexTaskDef.Config config = new ComplexTaskDef.Config();
		ComplexTaskDef.Revisions.Revision revision = new ComplexTaskDef.Revisions.Revision();
		ComplexTaskDef.Revisions revisions = new ComplexTaskDef.Revisions();

		correctionMode.setRegular(regular);
		config.setKindnessExtensionTime(2);
		config.setCorrectionMode(correctionMode);
		config.setTries(5);
		config.setTasksPerPage(30);
		complexTaskDef.setConfig(config);
		complexTaskDef.setDescription("Eine Testklausur");
		complexTaskDef.setID(rand.getRandomID());
		complexTaskDef.setShowHandlingHintsBeforeStart(false);

		revisions.getRevision().add(revision);
		revision.setAuthor("Christoph Jobst");
		revision.setDate(date.getTime());
		revision.setSerialNumber(1);
		complexTaskDef.setRevisions(revisions);
		complexTaskDef.setTitle("Testklausur");

		/*
		 * Zuweisungs und Konvertierungsschleife für Category-Blöcke
		 * Funktionsweise: 1. Alle Titel einlesen und in categoryNameList
		 * speichern Dabei Redundanz entfernen 2. Sofern erstmaliges Auftrten
		 * einer neuen Category hinzufügen eines neuen Objektex im
		 * categoryManager
		 */
		List<String> categoryNameList = new ArrayList<String>();
		List<CategoryManager> categoryManagerList = new ArrayList<CategoryManager>();
		for (int i = 0; i < quizsammlung.getQuestion().toArray().length; i++) {
			try {
				if (quizsammlung.getQuestion().get(i).getType().toString()
						.equals("category")
						&& (!categoryNameList.contains(quizsammlung
								.getQuestion().get(i).getCategory().getText()
								.toString().substring(9)))) {
					categoryNameList.add(quizsammlung.getQuestion().get(i)
							.getCategory().getText().toString().substring(9));
					categoryManagerList.add(new CategoryManager(
							CategoryToCategoryConverter.processing(quizsammlung
									.getQuestion().get(i))));
				}

			} catch (Exception e) {
				System.out.println("Problem beim Category-Parser");
			}

		}

		/*
		 * Zuweisungs- und Konvertierungsschleife für Fragen Funktionsweise: 1.
		 * Gehe alle Fragen im Moodle-Quiz durch. 2. Ist ein Category dabei? ->
		 * Gehe die Fragen ab da durch und füge sie dem entsprechenden Element
		 * (categoryManager) hinzu, bis eine neue Category auftaucht.
		 */
		int belongingCategoryIndex = 0;
		for (int j = 0; j < quizsammlung.getQuestion().toArray().length; j++) {
			try {

				if (quizsammlung.getQuestion().get(j).getType().toString()
						.equals("category")) {

					/*
					 * Abgleich: Ist zu welchem Objekt im categoryManager gehört
					 * die aktuell gefundene Category?
					 */
					for (int k = 0; k < categoryManagerList.toArray().length; k++) {
						if (categoryManagerList
								.get(k)
								.getTitle()
								.equals(quizsammlung.getQuestion().get(j)
										.getCategory().getText().toString()
										.substring(9))) {
							belongingCategoryIndex = k;
						}
					}

					/*
					 * TaskBlock-Erstellungsschleife Die nachsten Elemente bis
					 * zur nachten Category konvertieren und hinzufügen.
					 */
					for (int i = j + 1; i < quizsammlung.getQuestion()
							.toArray().length; i++) {

						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("essay")) {

							categoryManagerList
									.get(belongingCategoryIndex)
									.getTextTaskBlock()
									.getTextSubTaskDefOrChoice()
									.add(EssayToTextConverter
											.processing(quizsammlung
													.getQuestion().get(i)));
							categoryManagerList.get(belongingCategoryIndex)
									.setHasTextTaskBlock(true);
							// Debug:
							// System.out.println("Categoryblock nummer "
							// + belongingCategoryIndex
							// + " hat nun "
							// + categoryManager
							// .get(belongingCategoryIndex)
							// .getTextTaskBlock()
							// .getTextSubTaskDefOrChoice()
							// .toArray().length + " textfragen");
						}

						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("cloze")) {
							categoryManagerList
									.get(belongingCategoryIndex)
									.getClozeTaskBlock()
									.getClozeSubTaskDefOrChoice()
									.add(ClozeToClozeConverter
											.processing(quizsammlung
													.getQuestion().get(i)));
							categoryManagerList.get(belongingCategoryIndex)
									.setHasClozeTaskBlock(true);
						}

						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("truefalse")) {
							categoryManagerList
									.get(belongingCategoryIndex)
									.getMcTaskBlock()
									.getMcSubTaskDefOrChoice()
									.add(TruefalseToMcConverter
											.processing(quizsammlung
													.getQuestion().get(i)));
							categoryManagerList.get(belongingCategoryIndex)
									.setHasMcTaskBlock(true);
						}

						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("multichoice")) {
							categoryManagerList
									.get(belongingCategoryIndex)
									.getMcTaskBlock()
									.getMcSubTaskDefOrChoice()
									.add(MultichoiceToMcConverter
											.processing(quizsammlung
													.getQuestion().get(i)));
							categoryManagerList.get(belongingCategoryIndex)
									.setHasMcTaskBlock(true);

						}

						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("shortanswer")) {

							categoryManagerList
									.get(belongingCategoryIndex)
									.getTextTaskBlock()
									.getTextSubTaskDefOrChoice()
									.add(ShortanswerToTextConverter
											.processing(quizsammlung
													.getQuestion().get(i)));
							categoryManagerList.get(belongingCategoryIndex)
									.setHasTextTaskBlock(true);

						}

						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("matching")) {

							categoryManagerList
									.get(belongingCategoryIndex)
									.getMappingTaskBlock()
									.getMappingSubTaskDefOrChoice()
									.add(MatchingToMappingConverter
											.processing(quizsammlung
													.getQuestion().get(i)));
							categoryManagerList.get(belongingCategoryIndex)
									.setHasMappingTaskBlock(true);
						}
						
						
						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("groupingtask")) {

							categoryManagerList
									.get(belongingCategoryIndex)
									.getAddonTaskBlock()
									.getAddonSubTaskDefOrChoice()
									.add(GroupingTask
											.processing(quizsammlung
													.getQuestion().get(i)));
							categoryManagerList.get(belongingCategoryIndex)
									.setHasAddonTaskBlock(true);
						}					
						
						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("comparetexttask")) {

							categoryManagerList
									.get(belongingCategoryIndex)
									.getAddonTaskBlock()
									.getAddonSubTaskDefOrChoice()
									.add(CompareTextTask
											.processing(quizsammlung
													.getQuestion().get(i)));
							categoryManagerList.get(belongingCategoryIndex)
									.setHasAddonTaskBlock(true);
						}			
						
						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("timetask")) {

							categoryManagerList
									.get(belongingCategoryIndex)
									.getAddonTaskBlock()
									.getAddonSubTaskDefOrChoice()
									.add(TimeTask
											.processing(quizsammlung
													.getQuestion().get(i)));
							categoryManagerList.get(belongingCategoryIndex)
									.setHasAddonTaskBlock(true);
						}			
						
						if (quizsammlung.getQuestion().get(i).getType()
								.toString().equals("category")) {
							/*
							 * Wird ein Category gefunden, dann verlasse die
							 * TaskBlock-Erstellungsschleife und suche dir die
							 * neue zu füllende Category aus dem CategoryManager
							 * 
							 * TODO Das ruft nach Rekursion:
							 * 
							 * Wenn es eine Category ist und zu einer
							 * Unterkategorie gehört verfahre wie bisher mit den
							 * TaskBlöcken -> füge der aktuellen Category eine
							 * Unterkategorie hinzu.
							 * 
							 * Alternative: Category-Blöcke einzeln erstellen
							 * (wie bisher) und iterativ gesondert einander
							 * zuweisen
							 * 
							 * In beiden Fällen: Stringparser für "/" in
							 * Category.Title nötig, um Unterordner zu
							 * definieren.
							 */
							break;
						}
					}
				}
			} catch (Exception e) {
				System.out.println("PROBLEM BEIM INPUTAUFTEILER");
				e.printStackTrace();
			}
		}

		// Alle Category in der Liste hinzufügen
		// Hier Auswahl, ob flach oder geschachtelt
		complexTaskDef = CategoryAssignment.assignFlatCategories(
				complexTaskDef, categoryManagerList);

		return complexTaskDef;
	}

}