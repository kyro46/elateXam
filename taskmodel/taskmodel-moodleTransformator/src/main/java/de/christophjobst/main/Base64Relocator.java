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
import java.util.List;

import generated.Quiz.Question.Questiontext;
import generated.Quiz.Question.Questiontext.File;

public class Base64Relocator {

	public static String relocateBase64(Questiontext questiontext) {

		String text = "";
		String problem_string = questiontext.getText();

		List<File> fileList = new ArrayList<File>();
		fileList = questiontext.getFile();
		// fileList.get(0).getValue(); //Base64-String
		// fileList.get(0).getEncoding(); //base64
		// fileList.get(0).getName(); //Dateiname, der im Text zu ersetzen ist

		if (!problem_string.equals("")) {
			try {
				for (int i = 0; i < fileList.toArray().length; i++) {
					problem_string = problem_string.replaceAll("@@PLUGINFILE@@/" + fileList.get(i).getName(), "data:image/gif;base64," + fileList.get(i).getValue());					
				}
				text = problem_string;

			} catch (Exception e) {
				e.printStackTrace();

				text = problem_string;
			}
		} else {
			// Keine Aufgabenstellung angegeben
			text = "Aufgabenstellung - Platzhalter";
		}
		// System.out.println(root.getChildNodes().item(0).getNodeName());//img
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(0).getNodeValue());//alt
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(1).getNodeValue());//height
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(2).getNodeValue());//src
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(3).getNodeValue());//width
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(2).getNodeValue().substring(15));

		// root.getChildNodes().item(0).getAttributes().item(2).setNodeValue("BASE64STRING");
		// root.getChildNodes().item(0).getAttributes().item(2).setNodeValue("data:image/gif;base64,"
		// + questiontext.getFile().get(0).getValue());

		// System.out.println(text);

		// Start: <img src="@@PLUGINFILE@@/name.jpg" alt="12345678" width="776"
		// height="436" />
		// Ziel: <img src="data:image/gif;base64,_x_x_x_x_x_" alt="12345678"
		// width="776" height="436" />

		return text;
	}
}
