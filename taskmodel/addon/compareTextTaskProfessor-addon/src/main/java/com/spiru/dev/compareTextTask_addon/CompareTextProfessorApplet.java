package com.spiru.dev.compareTextTask_addon;

/*
 * YOU NEED TO DISABLE "Temporary Internet Files" in
 * Java Web Start Settings to avoid loading old JARs
 *
 * @url www.linuxquestions.org/questions/linux-software-2/firefox-java-applet-cache-551644/
 */

import java.applet.Applet;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.spiru.dev.compareTextTask_addon.Utils.XMLBase64;


@SuppressWarnings("serial")
public class CompareTextProfessorApplet extends Applet {
	private CompareTextProfessorenPanel jpanel;

	@Override
	public void init() {
		String mementostr = getParameter("memento");
		if (mementostr == null && this.getWidth() < 300) { // is NULL, when Applet is not loaded from a Webbrowser, but from Eclipse
			mementostr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Memento><addonConfig><availableTags><tag name=\"example\"><desc>Example Tag with Description, please replace this line.</desc></tag></availableTags></addonConfig>"
					+ "<textComparisonSubTaskDef><initialText>Lorem ipsum dolor sit amet.</initialText><sampleSolution>Lorem ipsum dolor sit amet.</sampleSolution></textComparisonSubTaskDef></Memento>";
			mementostr = DatatypeConverter.printBase64Binary(mementostr.getBytes());
			this.setSize(800, 400);
		}
		Element availableTags = null;
		String initial_text = "";
		String sample_solution = "";
		if (mementostr.length() > 0) { // if empty: assumption: Not editing existing-, but adding new Question -> nothing to do
			Element Memento = XMLBase64.base64StringToElement(mementostr, null); // from moodle, we will get a base64 string
			availableTags = (Element) Memento.getElementsByTagName("availableTags").item(0);
			//System.out.println(new String(XMLBase64.elementToByteArray(availableTags, null)));
			initial_text = Memento.getElementsByTagName("initialText").item(0).getTextContent();
			sample_solution = Memento.getElementsByTagName("sampleSolution").item(0).getTextContent();
			initial_text = initial_text.replaceAll("<br/>", "\n");
			sample_solution = sample_solution.replaceAll("<br/>", "\n");
		}
		jpanel = new CompareTextProfessorenPanel(initial_text, sample_solution, availableTags, this.getWidth(), this.getHeight());
		//jpanel.setSize(800, 450);
		add(jpanel);
	}

	public String getMemento() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element Memento = document.createElement("Memento");
			document.appendChild(Memento);
			// add addonConfig with availableTags
			Element addonConfig = document.createElement("addonConfig");
			Memento.appendChild(addonConfig);
			jpanel.appendAvailableTags(addonConfig);
			// add SubTaskDef with initialText and sampleSolution
			Element textComparisonSubTaskDef = document.createElement("textComparisonSubTaskDef");
			Element initialText = document.createElement("initialText");
			Element sampleSolution = document.createElement("sampleSolution");
			initialText.setTextContent(jpanel.getInitialText());
			sampleSolution.setTextContent(jpanel.getSampleSolution());
			textComparisonSubTaskDef.appendChild(initialText);
			textComparisonSubTaskDef.appendChild(sampleSolution);
			Memento.appendChild(textComparisonSubTaskDef);
			// return DOM as Base64 String
			//System.out.println(new String(XMLBase64.elementToByteArray(Memento, null)));
			return XMLBase64.elementToBase64String(Memento, null, false);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

	@Override
	public void start() {
	}
	@Override
	public void stop() {
	}
}
