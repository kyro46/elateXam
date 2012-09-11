package com.spiru.dev.compareTextTask_addon.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * tools to Convert DOM Elements to Base64 Strings back and forth
 *
 * @author C.Wilhelm
 */

public class XMLBase64 {
	// LOADING FROM BASE64 METHODS
	public static Element base64StringToElement(String base64Text, String wantedTagName) {
		byte[] xml = DatatypeConverter.parseBase64Binary(base64Text);
		return byteArrayToElement(xml, wantedTagName);
	}
	public static Element byteArrayToElement(byte[] xml, String wantedTagName) {
		Element wantedElement = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document document = parser.parse(new InputSource(new ByteArrayInputStream(xml)));
			if (wantedTagName != null)
				wantedElement = (Element) document.getElementsByTagName(wantedTagName).item(0);
			else wantedElement = (Element) document.getFirstChild();
			return wantedElement;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	// SAVING ELEMENTS TO BASE64 METHODS
	public static String elementToBase64String(Element element, String relevantTagName, boolean omit_xml_decl) {
		byte[] tmp = elementToByteArray(element, relevantTagName, omit_xml_decl);
		return DatatypeConverter.printBase64Binary(tmp);
	}
	public static String elementToBase64String(Element element, String relevantTagName) {
		return elementToBase64String(element, relevantTagName, false);
	}
	public static byte[] elementToByteArray(Element element, String relevantTagName, boolean omit_xml_decl) {
		Element relevantElement = null;
		if (relevantTagName != null)
			relevantElement = (Element) element.getElementsByTagName(relevantTagName).item(0);
		else relevantElement = element;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			if (omit_xml_decl) // omit <?xml...?>
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			DOMSource source = new DOMSource(relevantElement);
			ByteArrayOutputStream bytewriter = new ByteArrayOutputStream();
			transformer.transform(source, new StreamResult(bytewriter));
			return bytewriter.toByteArray();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static byte[] elementToByteArray(Element element, String relevantTagName) {
		return elementToByteArray(element, relevantTagName, false);
	}
}
