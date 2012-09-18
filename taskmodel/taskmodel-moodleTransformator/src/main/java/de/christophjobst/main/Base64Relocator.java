/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 *
 */
package de.christophjobst.main;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import generated.Quiz.Question.Questiontext;
import generated.Quiz.Question.Questiontext.File;

public class Base64Relocator {

	// TrueFalse und essay als Testklasse
	public static String relocateBase64(Questiontext questiontext)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException {

		String text = "";
		String problem_string = questiontext.getText();

		List<File> fileList = new ArrayList<File>();
		fileList = questiontext.getFile();
		// fileList.get(0).getValue(); //Base64-String
		// fileList.get(0).getEncoding(); //base64
		// fileList.get(0).getName(); //Dateiname, der im Text zu ersetzen ist

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document;
		
		if (!problem_string.equals("")) {
			try {
				document = builder.parse(new InputSource(new StringReader(
						problem_string)));
				Element root = document.getDocumentElement();
				for (int i = 0; i < root.getChildNodes().getLength(); i++) {
					
					System.out.println(root.getChildNodes().item(i).getNodeName());

					
					if (root.getChildNodes().item(i).getNodeName()
							.equals("img")) {
						for (int j = 0; j < fileList.toArray().length; j++) {
							if (fileList
									.get(j)
									.getName()
									.equals(root.getChildNodes().item(i)
											.getAttributes().item(2)
											.getNodeValue().substring(15))) {
								root.getChildNodes()
										.item(i)
										.getAttributes()
										.item(2)
										.setNodeValue(
												"data:image/gif;base64,"
														+ questiontext
																.getFile()
																.get(j)
																.getValue());
							}
						}
					}
				}

				TransformerFactory transFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transFactory.newTransformer();
				StringWriter buffer = new StringWriter();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
						"yes");
				transformer.transform(new DOMSource(root), new StreamResult(
						buffer));
				text = buffer.toString();

			} catch (org.xml.sax.SAXParseException e) {
				System.out.println(problem_string + "(catch block)");
				System.out.println("****Versuche manuellen Parser für Aufgabentsellungstext.****");
				e.printStackTrace();

				int start = 0;
				int ende = 0;
				String fileName = "";
				while (start != -1) {
					start = problem_string.indexOf("@@PLUGINFILE@@/",ende);
					ende = problem_string.indexOf("\" alt=\"");
					if (start == -1 || ende == -1) break;
					fileName = problem_string.substring(start+15, ende);
					System.out.println(fileName + " FileName");
					for (int j = 0; j < fileList.toArray().length; j++) {
						System.out.println(fileList.get(j).getName() + " FileList");
						if (fileList.get(j).getName().equals(fileName)) {
							problem_string = problem_string.replace("@@PLUGINFILE@@/" + fileName, "data:image/gif;base64,"
									+ questiontext
									.getFile()
									.get(j)
									.getValue());
						}
					}					
				}
				
				
				
				text = problem_string;
	
				
				
			}
		} else {
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
