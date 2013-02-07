package com.spiru.dev.compareTextTask_addon;

/*
 * YOU NEED TO DISABLE "Temporary Internet Files" in
 * Java Web Start Settings to avoid loading old JARs
 *
 * @url www.linuxquestions.org/questions/linux-software-2/firefox-java-applet-cache-551644/ 
 */

import java.applet.Applet;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.DatatypeConverter;

import org.w3c.dom.Element;

import com.spiru.dev.compareTextTask_addon.Utils.XMLBase64;

@SuppressWarnings("serial")
public class CompareTextApplet extends Applet {
	private CompareTextPanel jpanel;
	private Task task;
	private boolean hasChanged = false;	
	private String sofarStart = "";
	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	@Override
	public void init() {
		//this.getParameter("..."); // HTML: <parma name="" value=""></param>
		// http://docs.oracle.com/javase/tutorial/deployment/applet/invokingAppletMethodsFromJavaScript.html
		// http://stackoverflow.com/questions/7278626/javascript-to-java-applet-communication
		String sofar = this.getParameter("soFarSolution"); // will be "EMPTY" (literally!) unless student triggered Save Page								
		String memento = this.getParameter("memento"); // is expected to contain Base64 representation of Memento part in AddonTaskDef
		boolean view_only = Boolean.parseBoolean(this.getParameter("viewOnly")); // correcor shouldn't be able to manipulate result
		if (memento == null && this.getWidth() < 300) { // is NULL, when Applet is not loaded from a Webbrowser, but from Eclipse
			memento = DatatypeConverter.printBase64Binary("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Memento><addonConfig><availableTags><tag name=\"author\"><desc>Anfang/Ende Name der/des Verfasser_in</desc></tag><tag name=\"p\"><desc>Absatzanfang/-ende</desc></tag><tag name=\"head\"><desc>Überschriftenbeginn/-ende</desc></tag><tag name=\"quot\"><desc>Zitatanfang/-ende</desc></tag></availableTags></addonConfig><textComparisonSubTaskDef><initialText>AutoComplete Readme&#13;&lt;br/&gt;-------------------&#13;&lt;br/&gt;Please contact me if you are using AutoComplete in your project!  I like to&#13;&lt;br/&gt;know when people are finding it useful.  Please send mail to:&#13;&lt;br/&gt;robert -at- fifesoft dot com.&#13;&lt;br/&gt;&#13;&lt;br/&gt;&#13;&lt;br/&gt;* About AutoComplete&#13;&lt;br/&gt;&#13;&lt;br/&gt;  AutoComplete is a Swing library that facilitates adding auto-completion&#13;&lt;br/&gt;  (aka \"code completion, \"Intellisense\") to any JTextComponent.  Special&#13;&lt;br/&gt;  integration is added for RSyntaxTextArea (a programmer's text editor, see&#13;&lt;br/&gt;  http://fifesoft.com/rsyntaxtextarea/ for more information), since this feature&#13;&lt;br/&gt;  is commonly needed when editing source code.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Example Usage&#13;&lt;br/&gt;&#13;&lt;br/&gt;  See the AutoComplete example(s) on the RSyntaxTextArea examples page here:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://fifesoft.com/rsyntaxtextarea/examples/index.php&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  They provide a good example of basic usage of the library, showing how to&#13;&lt;br/&gt;  auto-complete a simple, fixed set of words (function names).&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  Also see the AutoCompleteDemo project, which lives here in SVN:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://svn.fifesoft.com/svn/RSyntaxTextArea/&#13;&lt;br/&gt;&#13;&lt;br/&gt;  It provides an example of loading completions from an XML file.  It&#13;&lt;br/&gt;  demonstrates a code editor with completion support for the C Standard library,&#13;&lt;br/&gt;  and demos the parameter assistance feature.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* License&#13;&lt;br/&gt;&#13;&lt;br/&gt;  AutoComplete is licensed under a modified BSD liense.  Please see the&#13;&lt;br/&gt;  included AutoComplete.License.txt file.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Feedback&#13;&lt;br/&gt;&#13;&lt;br/&gt;  I hope you find AutoComplete useful.  Bug reports, feature requests, and&#13;&lt;br/&gt;  just general questions are always welcome.  Ways you can submit feedback:&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://forum.fifesoft.com (preferred)&#13;&lt;br/&gt;         Has a forum for AutoComplete and related projects, where you can&#13;&lt;br/&gt;         ask questions and get feedback quickly.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * https://sourceforge.net/projects/rsyntaxtextarea/&#13;&lt;br/&gt;         RSyntaxTextArea and AutoComplete's home on SourceForge.  Don't use&#13;&lt;br/&gt;         the forums here (use the first link above), but you can use the&#13;&lt;br/&gt;         \"Tracker\" section here to post bug reports and feature requests.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Other Links&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://fifesoft.com/autocomplete&#13;&lt;br/&gt;         Project home page, which contains general information and example&#13;&lt;br/&gt;         source code.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://fifesoft.com/rsyntaxtextarea&#13;&lt;br/&gt;         The source code editor you're (probably) already using if you're&#13;&lt;br/&gt;         using this AutoComplete library.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://javadoc.fifesoft.com/rsyntaxtextarea/&#13;&lt;br/&gt;    * http://javadoc.fifesoft.com/autocomplete/&#13;&lt;br/&gt;         API documentation for the package.  Note that this *will* change as&#13;&lt;br/&gt;         the library matures.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Thanks&#13;&lt;br/&gt;&#13;&lt;br/&gt;  The left and right arrow icons in the Help \"tooltip\" window are from the&#13;&lt;br/&gt;  \"Silk\" icon set, under the Creative Commons 3.0 License.  This is a wonderful&#13;&lt;br/&gt;  icon set, found here:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://famfamfam.com/lab/icons/silk/&#13;&lt;br/&gt;&#13;&lt;br/&gt;  The \"bullet_black.png\" icon is an edited version of \"bullet_black.png\" in the&#13;&lt;br/&gt;  Silk icon set noted above.  It can be considered to be distributed under the&#13;&lt;br/&gt;  same Creative Commons 3.0 License.&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  \"osx_sizegrip.png\" is a reproduction of the size grip used on native OS X&#13;&lt;br/&gt;  windows.  It is distributed under the same modified BSD license as the meat&#13;&lt;br/&gt;  of the AutoComplete library.</initialText><sampleSolution>AutoComplete Readme&#13;&lt;br/&gt;-------------------&#13;&lt;br/&gt;Please contact &#13;&lt;a&gt;me if you are using AutoComplete in your project!  I like to&#13;&lt;br/&gt;know when people are finding it useful.  Please send mail to:&#13;&lt;br/&gt;robert -at- fifesoft dot com.&#13;&lt;br/&gt;&#13;&lt;br/&gt;&#13;&lt;br/&gt;* About AutoComplete&#13;&lt;br/&gt;&#13;&lt;br/&gt;  AutoComplete is a Swing library that facilitates adding auto-completion&#13;&lt;br/&gt;  (aka \"code completion, \"Intellisense\") to any JTextComponent.  Special&#13;&lt;br/&gt;  integration is added for RSyntaxTextArea (a programmer's text editor, see&#13;&lt;br/&gt;  http://fifesoft.com/rsyntaxtextarea/ for more information), since this feature&#13;&lt;br/&gt;  is commonly needed when editing source code.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Example Usage&#13;&lt;br/&gt;&#13;&lt;br/&gt;  See the AutoComplete example(s) on the RSyntaxTextArea examples page here:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://fifesoft.com/rsyntaxtextarea/examples/index.php&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  They provide a good example of basic usage of the library, showing how to&#13;&lt;br/&gt;  auto-complete a simple, fixed set of words (function names).&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  Also see the AutoCompleteDemo project, which lives here in SVN:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://svn.fifesoft.com/svn/RSyntaxTextArea/&#13;&lt;br/&gt;&#13;&lt;br/&gt;  It provides an example of loading completions from an XML file.  It&#13;&lt;br/&gt;  demonstrates a code editor with completion support for the C Standard library,&#13;&lt;br/&gt;  and demos the parameter assistance feature.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* License&#13;&lt;br/&gt;&#13;&lt;br/&gt;  AutoComplete is licensed under a modified BSD liense.  Please see the&#13;&lt;br/&gt;  included AutoComplete.License.txt file.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Feedback&#13;&lt;br/&gt;&#13;&lt;br/&gt;  I hope you find AutoComplete useful.  Bug reports, feature requests, and&#13;&lt;br/&gt;  just general questions are always welcome.  Ways you can submit feedback:&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://forum.fifesoft.com (preferred)&#13;&lt;br/&gt;         Has a forum for AutoComplete and related projects, where you can&#13;&lt;br/&gt;         ask questions and get feedback quickly.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * https://sourceforge.net/projects/rsyntaxtextarea/&#13;&lt;br/&gt;         RSyntaxTextArea and AutoComplete's home on SourceForge.  Don't use&#13;&lt;br/&gt;         the forums here (use the first link above), but you can use the&#13;&lt;br/&gt;         \"Tracker\" section here to post bug reports and feature requests.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Other Links&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://fifesoft.com/autocomplete&#13;&lt;br/&gt;         Project home page, which contains general information and example&#13;&lt;br/&gt;         source code.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://fifesoft.com/rsyntaxtextarea&#13;&lt;br/&gt;         The source code editor you're (probably) already using if you're&#13;&lt;br/&gt;         using this AutoComplete library.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://javadoc.fifesoft.com/rsyntaxtextarea/&#13;&lt;br/&gt;    * http://javadoc.fifesoft.com/autocomplete/&#13;&lt;br/&gt;         API documentation for the package.  Note that this *will* change as&#13;&lt;br/&gt;         the library matures.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Thanks&#13;&lt;br/&gt;&#13;&lt;br/&gt;  The left and right arrow icons in the Help \"tooltip\" window are from the&#13;&lt;br/&gt;  \"Silk\" icon set, under the Creative Commons 3.0 License.  This is a wonderful&#13;&lt;br/&gt;  icon set, found here:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://famfamfam.com/lab/icons/silk/&#13;&lt;br/&gt;&#13;&lt;br/&gt;  The \"bullet_black.png\" icon is an edited version of \"bullet_black.png\" in the&#13;&lt;br/&gt;  Silk icon set noted above.  It can be considered to be distributed under the&#13;&lt;br/&gt;  same Creative Commons 3.0 License.&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  \"osx_sizegrip.png\" is a reproduction of the size grip used on native OS X&#13;&lt;br/&gt;  windows.  It is distributed under the same modified BSD license as the meat&#13;&lt;br/&gt;  of the AutoComplete library.</sampleSolution></textComparisonSubTaskDef></Memento>".getBytes());
			sofar = null;//DatatypeConverter.printBase64Binary("Test TestTest\n\n TestTest TestTest TestTest Test<br/>Test Test".getBytes());
			this.setSize(600, 300);
			//view_only = true;
		}
		String leftTextStr = "";
		Element mementoTaskDef = XMLBase64.base64StringToElement(memento, null);
		Element availableTags = (Element) mementoTaskDef.getElementsByTagName("availableTags").item(0);
		if (sofar != null && !sofar.equals("EMPTY")){ // so-far solution will be sent as Base64 String
			sofar = new String(DatatypeConverter.parseBase64Binary(sofar));
			if (sofar != null && !sofar.isEmpty()){
				if (sofar.indexOf("#changed#") == 0){					
					this.hasChanged = true;
					sofar = sofar.replaceFirst("#changed#", "");					
				}
			}
		}
		if (view_only) { // Correction Mode -> use Sample Solution on the left
			Element sampleSolution = (Element) mementoTaskDef.getElementsByTagName("sampleSolution").item(0);
			leftTextStr = sampleSolution.getFirstChild().getNodeValue();
			leftTextStr = leftTextStr.replaceAll("<br/>", "\n");
		} else {
			Element initialText = (Element) mementoTaskDef.getElementsByTagName("initialText").item(0);
			leftTextStr = initialText.getFirstChild().getNodeValue();
			leftTextStr = leftTextStr.replaceAll("<br/>", "\n");
			if (sofar == null || sofar.equals("EMPTY")) {
				sofar = leftTextStr;				
			}
		}
		sofarStart = sofar; 
		jpanel = new CompareTextPanel(leftTextStr, sofar, availableTags,
				view_only, this.getWidth(), this.getHeight());
		add(jpanel);
		Timer timer = new Timer();
		task = new Task(this);
		timer.schedule(task, 1000, 1500);
	}
	
	public String getResult() {				
		String ret = "";		
		if (this.hasChanged || (jpanel.getRightTextAreaContent()!= null && !jpanel.getRightTextAreaContent().equals(sofarStart))){
			ret = "#changed#";			
		}		
		if (jpanel.getRightTextAreaContent() != null && !jpanel.getRightTextAreaContent().isEmpty()){
			ret += jpanel.getRightTextAreaContent();
		}				
		return DatatypeConverter.printBase64Binary(ret.getBytes());
	}
	
	public boolean hasChanged() {
		if (jpanel.getRightTextAreaContent() == null || sofarStart == null){
			return false;
		}
		if (sofarStart.equals(jpanel.getRightTextAreaContent())){
			return false;
		}
		return true;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {		
	}

	public void draw() {
		task.setTest();
	}
}

class Task extends TimerTask {
	private CompareTextApplet a;
	private boolean test = false;
	private int count = 0;

	public Task(CompareTextApplet a) {
		this.a = a;
	}

	public void setTest() {
		this.test = true;
	}

	@Override
	public void run() {
		if (test || (count < 2 && count != 0)) {
			// a.drawAll();
			a.paintAll(a.getGraphics());
			// a.repaint();
			test = false;
			count++;
		}
		if (count >= 2)
			count = 0;
	}
}
