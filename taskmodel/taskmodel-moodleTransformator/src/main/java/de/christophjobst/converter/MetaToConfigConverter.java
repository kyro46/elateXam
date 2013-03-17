/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package de.christophjobst.converter;

import generated.Quiz.Question;
import de.christophjobst.main.Base64Relocator;
import de.christophjobst.main.RandomIdentifierGenerator;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Config.CorrectionMode;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Config.CorrectionMode.MultipleCorrectors;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Config.CorrectionMode.Regular;

public class MetaToConfigConverter {

	static RandomIdentifierGenerator rand = new RandomIdentifierGenerator();
	static ComplexTaskDef complexTaskDef = new ComplexTaskDef();
	static ComplexTaskDef.Config config = new ComplexTaskDef.Config();
	static CorrectionMode correctionMode = new CorrectionMode();
	static Regular regular = new Regular();
	static MultipleCorrectors multipleCorrectors = new MultipleCorrectors();
	
	public static ComplexTaskDef processing(Question question) {
		
		complexTaskDef.setTitle(question.getName().getText());
		complexTaskDef.setShowHandlingHintsBeforeStart(question.getShowhandlinghintsbeforestart().equals("1") ? true : false);
		complexTaskDef.setStartText(Base64Relocator.relocateBase64(question.getGeneralfeedback().getText(),question.getGeneralfeedback().getFile()));;
		complexTaskDef.setDescription(Base64Relocator.relocateBase64(question.getQuestiontext().getText(),question.getQuestiontext().getFile()));
			
		config.setTime(Integer.parseInt(question.getTime()));
		config.setKindnessExtensionTime(Integer.parseInt(question.getKindnessextensiontime()));
		config.setTasksPerPage(Integer.parseInt(question.getTasksperpage()));
		config.setTries(Integer.parseInt(question.getTries()));


		int numberOfCorrectors = Integer.parseInt(question.getNumberofcorrectors());

		if (numberOfCorrectors == 1) {
			correctionMode.setRegular(regular);
		} else {
			multipleCorrectors.setNumberOfCorrectors(numberOfCorrectors);
			correctionMode.setMultipleCorrectors(multipleCorrectors);
		}
		config.setCorrectionMode(correctionMode);

		complexTaskDef.setConfig(config);
		complexTaskDef.setID(rand.getRandomID());
		//Wird z.Z. nicht angeboten
		//correctOnlyProcessedTasks.setNumberOfTasks(10);
		//correctionMode.setCorrectOnlyProcessedTasks(correctOnlyProcessedTasks);
		//config.setCorrectionMode(correctionMode);
		
		
		
		
		return complexTaskDef;
	}
	
	
	public static ComplexTaskDef setDefault() {

		complexTaskDef.setTitle("Testklausur ohne Klausurkonfiguration");
		complexTaskDef.setShowHandlingHintsBeforeStart(true);
		complexTaskDef.setStartText("Starttext - Keine Klausurkonfiguration angegeben");
		complexTaskDef.setDescription("Beschreibung - Keine Klausurkonfiguration angegeben");

		config.setTime(5);
		config.setKindnessExtensionTime(2);
		config.setTasksPerPage(3);
		config.setTries(5);
		complexTaskDef.setConfig(config);
		complexTaskDef.setID(rand.getRandomID());

		correctionMode.setRegular(regular);
		config.setCorrectionMode(correctionMode);

		return complexTaskDef;
	}
	
}
