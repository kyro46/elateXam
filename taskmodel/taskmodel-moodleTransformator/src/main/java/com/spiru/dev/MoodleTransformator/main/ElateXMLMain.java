/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 * 
 * TODO Metadatablock in ComplexTaskDef einfügen
 * TODO Mehrere Schachtelungsebenen für Category übernehmen, sobald complexTaskDef.xsd final
 * TODO Weitere Fragetypen einbauen
 * TODO Punkte = Anzahl der Lücken/Matchings - inkonsistent, da in Frageinstanzen nicht einheitlich viele Lücken/Matchings
 * TODO Moodlebilder aus Version 2.3 in den HTML-Code schreiben. Siehe: http://aktuell.de.selfhtml.org/artikel/grafik/inline-images/
 */

package com.spiru.dev.MoodleTransformator.main;

//import de.christophjobst.test.*;
import com.spiru.dev.MoodleTransformator.main.Inputaufteiler;

import de.thorstenberger.taskmodel.complex.jaxb.*;


import generated.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ElateXMLMain {

	//Debug: Pfad zur Moodle-XML-Datei explizit angeben.
	//private static final String QUIZ_XML = "./roma2.xml";
	//private static final String COMPLEXTASKDEF_XML = "./complexTaskDef.xml";
	public static byte[] startTransformToElateFormat(byte[] input_xml_content) throws JAXBException, IOException {

		/*//Beispiel: >> XmlTransformatorMoodleToElate.jar input.xml output.xml
		final String QUIZ_XML = "./" + inputXMLFile;
		final String COMPLEXTASKDEF_XML = "./" + Outputname;*/

		// JAXB Context für Moodle-Quiz
		JAXBContext context_quiz = JAXBContext.newInstance(Quiz.class);
		Unmarshaller um_quiz = context_quiz.createUnmarshaller();
		Quiz quizsammlung = (Quiz) um_quiz.unmarshal(new ByteArrayInputStream(input_xml_content));

		// JAXB Context und Marshaller für ComplexTaskDef
		JAXBContext context_complexTaskDef = JAXBContext.newInstance(ComplexTaskDef.class);
		Marshaller m_complexTaskDef = context_complexTaskDef.createMarshaller();

		// System.out.println("Input from XML File: ");
		// m_quiz.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// m_quiz.marshal(quizsammlung, System.out);

		// System.out.println("Debugging starten:");
		// DebuggingAusgabe.printQuestionsTypeAndAnswer(quizsammlung);
		// DebuggingAusgabe.printExistingComplexTaskDefCategoryblocks(complexTaskDef);

		ComplexTaskDef complexTaskDef = new ComplexTaskDef();
		complexTaskDef = Inputaufteiler.inputAufteilen(quizsammlung);

		// Ausgabe des generierten XML
		// System.out.println("Output from XML File: ");
		m_complexTaskDef.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				Boolean.TRUE);
		m_complexTaskDef.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		// m_complexTaskDef.marshal(complexTaskDef, System.out);

		//Nachbearbeitung wegen der Namespaces
		//In package-info.java in taskmodel.thorstenberger.de
		//wird ein Standardnamespace angegeben. 
		//Dieser ist bei neu erstellten und eingefügten
		//Elementen nicht als zusätzliches Attribut erwünscht.
		StringWriter sw = new StringWriter();
        m_complexTaskDef.marshal(complexTaskDef, sw);
        String result = sw.toString();
        result = result.replaceAll(" xmlns=\"\" xmlns:ns2=\"http://complex.taskmodel.thorstenberger.de/complexTaskDef\"", "");
		

		result.getBytes("UTF-8");

//		ByteArrayOutputStream w = null;
//		try {
//			w = new ByteArrayOutputStream();
//			
//			m_complexTaskDef.marshal(complexTaskDef, w);
//		} finally {
//			try {
//				w.close();
//			} catch (Exception e) {
//			}
//		}
//		return w.toByteArray();
		return result.getBytes("UTF-8");
	}
	

	public static void main(String[] args) throws JAXBException, IOException {

		//Beispiel: >> XmlTransformatorMoodleToElate.jar input.xml output.xml
		final String QUIZ_XML = "./" + args[0];
		final String COMPLEXTASKDEF_XML = "./" + args[1];

		// JAXB Context f�r Moodle-Quiz
		JAXBContext context_quiz = JAXBContext.newInstance(Quiz.class);
		Unmarshaller um_quiz = context_quiz.createUnmarshaller();
		Quiz quizsammlung = (Quiz) um_quiz.unmarshal(new FileReader(QUIZ_XML));

		// JAXB Context und Marshaller für ComplexTaskDef
		JAXBContext context_complexTaskDef = JAXBContext
				.newInstance(ComplexTaskDef.class);
		Marshaller m_complexTaskDef = context_complexTaskDef.createMarshaller();

		// System.out.println("Input from XML File: ");
		// m_quiz.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// m_quiz.marshal(quizsammlung, System.out);

		// System.out.println("Debugging starten:");
		// DebuggingAusgabe.printQuestionsTypeAndAnswer(quizsammlung);
		// DebuggingAusgabe.printExistingComplexTaskDefCategoryblocks(complexTaskDef);

		ComplexTaskDef complexTaskDef = new ComplexTaskDef();
		complexTaskDef = Inputaufteiler.inputAufteilen(quizsammlung);

		// Ausgabe des generierten XML
		// System.out.println("Output from XML File: ");
		m_complexTaskDef.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				Boolean.TRUE);
		m_complexTaskDef.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		// m_complexTaskDef.marshal(complexTaskDef, System.out);

		
		
		//Nachbearbeitung wegen der Namespaces
		//In package-info.java in taskmodel.thorstenberger.de
		//wird ein Standardnamespace angegeben. 
		//Dieser ist bei neu erstellten und eingefügten
		//Elementen nicht als zusätzliches Attribut erwünscht.
		StringWriter sw = new StringWriter();
        m_complexTaskDef.marshal(complexTaskDef, sw);
        String result = sw.toString();
        //System.out.println(result);
        result = result.replaceAll(" xmlns=\"\" xmlns:ns2=\"http://complex.taskmodel.thorstenberger.de/complexTaskDef\"", "");
		
		
		Writer w = null;
		try {
			w = new FileWriter(COMPLEXTASKDEF_XML);
			w.write(result);
		} finally {
			try {
				w.close();
			} catch (Exception e) {
			}
		}
	
	System.out.println("Done");
	}	
	
}