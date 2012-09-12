/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 *
 */
package de.christophjobst.addonQuestionGetter;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import generated.Quiz.Question;
import de.christophjobst.main.Base64Relocator;
import de.christophjobst.main.RandomIdentifierGenerator;
import de.thorstenberger.taskmodel.complex.complextaskdef.AddonSubTaskDef;

public class GroupingTask {

	public static AddonSubTaskDef processing(Question question)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		AddonSubTaskDef subTask = new AddonSubTaskDef();

		// Allgemeine Angaben pro Frage
		subTask.setTrash(false);
		subTask.setInteractiveFeedback(false);
		subTask.setCorrectionHint(question.getCorrectorfeedback());
		subTask.setHint(question.getName().getText().toString());

		// Spezielle Angaben pro Frage
		subTask.setId(question.getName().getText().toString() + "_"
				+ rand.getRandomID());
		subTask.setProblem(Base64Relocator.relocateBase64(question.getQuestiontext()));

		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		String memento_string = question.getMemento();
	
		Document document  = builder.parse(new InputSource(new StringReader(memento_string)));
				
	     Element root = document.getDocumentElement();

	     
	     subTask.setTaskType("groupingTask");

	     subTask.setMemento(root);
		
		return subTask;
	}
}
