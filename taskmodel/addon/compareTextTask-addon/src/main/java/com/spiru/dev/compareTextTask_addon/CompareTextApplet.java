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
	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	@Override
	public void init() {
		//this.getParameter("..."); // HTML: <parma name="" value=""></param>
		// http://docs.oracle.com/javase/tutorial/deployment/applet/invokingAppletMethodsFromJavaScript.html
		// http://stackoverflow.com/questions/7278626/javascript-to-java-applet-communication
		//String text = this.getParameter("initialText");
		//String xmldef = this.getParameter("xmlDef"); // is expected to contain Base64 representation of avaiableTasks part in Memento
		String sofar = this.getParameter("soFarSolution"); // will be "EMPTY" (literally!) unless student triggered Save Page
		String memento = this.getParameter("memento");
		if (memento == null && this.getWidth() < 300) { // is NULL, when Applet is not loaded from a Webbrowser, but from Eclipse
			//xmldef = DatatypeConverter.printBase64Binary("<availableTags><tag name=\"author\"><desc>Anfang/Ende Name der/des Verfasser_in</desc></tag><tag name=\"head\"><desc>Überschriftenbeginn/-ende</desc></tag><tag name=\"quot\"><desc>Zitatanfang/-ende</desc></tag><tag name=\"p\"><desc>Absatzanfang/-ende</desc></tag></availableTags>".getBytes());
			//text = "Absatz1<br/><br/>Absatz2\n\nAbsatz3";
			memento = DatatypeConverter.printBase64Binary("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Memento><addonConfig><availableTags><tag name=\"author\"><desc>Anfang/Ende Name der/des Verfasser_in</desc></tag><tag name=\"p\"><desc>Absatzanfang/-ende</desc></tag><tag name=\"head\"><desc>Überschriftenbeginn/-ende</desc></tag><tag name=\"quot\"><desc>Zitatanfang/-ende</desc></tag></availableTags></addonConfig><textComparisonSubTaskDef><initialText>AutoComplete Readme&#13;&lt;br/&gt;-------------------&#13;&lt;br/&gt;Please contact me if you are using AutoComplete in your project!  I like to&#13;&lt;br/&gt;know when people are finding it useful.  Please send mail to:&#13;&lt;br/&gt;robert -at- fifesoft dot com.&#13;&lt;br/&gt;&#13;&lt;br/&gt;&#13;&lt;br/&gt;* About AutoComplete&#13;&lt;br/&gt;&#13;&lt;br/&gt;  AutoComplete is a Swing library that facilitates adding auto-completion&#13;&lt;br/&gt;  (aka \"code completion, \"Intellisense\") to any JTextComponent.  Special&#13;&lt;br/&gt;  integration is added for RSyntaxTextArea (a programmer's text editor, see&#13;&lt;br/&gt;  http://fifesoft.com/rsyntaxtextarea/ for more information), since this feature&#13;&lt;br/&gt;  is commonly needed when editing source code.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Example Usage&#13;&lt;br/&gt;&#13;&lt;br/&gt;  See the AutoComplete example(s) on the RSyntaxTextArea examples page here:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://fifesoft.com/rsyntaxtextarea/examples/index.php&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  They provide a good example of basic usage of the library, showing how to&#13;&lt;br/&gt;  auto-complete a simple, fixed set of words (function names).&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  Also see the AutoCompleteDemo project, which lives here in SVN:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://svn.fifesoft.com/svn/RSyntaxTextArea/&#13;&lt;br/&gt;&#13;&lt;br/&gt;  It provides an example of loading completions from an XML file.  It&#13;&lt;br/&gt;  demonstrates a code editor with completion support for the C Standard library,&#13;&lt;br/&gt;  and demos the parameter assistance feature.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* License&#13;&lt;br/&gt;&#13;&lt;br/&gt;  AutoComplete is licensed under a modified BSD liense.  Please see the&#13;&lt;br/&gt;  included AutoComplete.License.txt file.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Feedback&#13;&lt;br/&gt;&#13;&lt;br/&gt;  I hope you find AutoComplete useful.  Bug reports, feature requests, and&#13;&lt;br/&gt;  just general questions are always welcome.  Ways you can submit feedback:&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://forum.fifesoft.com (preferred)&#13;&lt;br/&gt;         Has a forum for AutoComplete and related projects, where you can&#13;&lt;br/&gt;         ask questions and get feedback quickly.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * https://sourceforge.net/projects/rsyntaxtextarea/&#13;&lt;br/&gt;         RSyntaxTextArea and AutoComplete's home on SourceForge.  Don't use&#13;&lt;br/&gt;         the forums here (use the first link above), but you can use the&#13;&lt;br/&gt;         \"Tracker\" section here to post bug reports and feature requests.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Other Links&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://fifesoft.com/autocomplete&#13;&lt;br/&gt;         Project home page, which contains general information and example&#13;&lt;br/&gt;         source code.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://fifesoft.com/rsyntaxtextarea&#13;&lt;br/&gt;         The source code editor you're (probably) already using if you're&#13;&lt;br/&gt;         using this AutoComplete library.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://javadoc.fifesoft.com/rsyntaxtextarea/&#13;&lt;br/&gt;    * http://javadoc.fifesoft.com/autocomplete/&#13;&lt;br/&gt;         API documentation for the package.  Note that this *will* change as&#13;&lt;br/&gt;         the library matures.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Thanks&#13;&lt;br/&gt;&#13;&lt;br/&gt;  The left and right arrow icons in the Help \"tooltip\" window are from the&#13;&lt;br/&gt;  \"Silk\" icon set, under the Creative Commons 3.0 License.  This is a wonderful&#13;&lt;br/&gt;  icon set, found here:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://famfamfam.com/lab/icons/silk/&#13;&lt;br/&gt;&#13;&lt;br/&gt;  The \"bullet_black.png\" icon is an edited version of \"bullet_black.png\" in the&#13;&lt;br/&gt;  Silk icon set noted above.  It can be considered to be distributed under the&#13;&lt;br/&gt;  same Creative Commons 3.0 License.&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  \"osx_sizegrip.png\" is a reproduction of the size grip used on native OS X&#13;&lt;br/&gt;  windows.  It is distributed under the same modified BSD license as the meat&#13;&lt;br/&gt;  of the AutoComplete library.</initialText><sampleSolution>AutoComplete Readme&#13;&lt;br/&gt;-------------------&#13;&lt;br/&gt;Please contact me if you are using AutoComplete in your project!  I like to&#13;&lt;br/&gt;know when people are finding it useful.  Please send mail to:&#13;&lt;br/&gt;robert -at- fifesoft dot com.&#13;&lt;br/&gt;&#13;&lt;br/&gt;&#13;&lt;br/&gt;* About AutoComplete&#13;&lt;br/&gt;&#13;&lt;br/&gt;  AutoComplete is a Swing library that facilitates adding auto-completion&#13;&lt;br/&gt;  (aka \"code completion, \"Intellisense\") to any JTextComponent.  Special&#13;&lt;br/&gt;  integration is added for RSyntaxTextArea (a programmer's text editor, see&#13;&lt;br/&gt;  http://fifesoft.com/rsyntaxtextarea/ for more information), since this feature&#13;&lt;br/&gt;  is commonly needed when editing source code.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Example Usage&#13;&lt;br/&gt;&#13;&lt;br/&gt;  See the AutoComplete example(s) on the RSyntaxTextArea examples page here:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://fifesoft.com/rsyntaxtextarea/examples/index.php&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  They provide a good example of basic usage of the library, showing how to&#13;&lt;br/&gt;  auto-complete a simple, fixed set of words (function names).&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  Also see the AutoCompleteDemo project, which lives here in SVN:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://svn.fifesoft.com/svn/RSyntaxTextArea/&#13;&lt;br/&gt;&#13;&lt;br/&gt;  It provides an example of loading completions from an XML file.  It&#13;&lt;br/&gt;  demonstrates a code editor with completion support for the C Standard library,&#13;&lt;br/&gt;  and demos the parameter assistance feature.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* License&#13;&lt;br/&gt;&#13;&lt;br/&gt;  AutoComplete is licensed under a modified BSD liense.  Please see the&#13;&lt;br/&gt;  included AutoComplete.License.txt file.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Feedback&#13;&lt;br/&gt;&#13;&lt;br/&gt;  I hope you find AutoComplete useful.  Bug reports, feature requests, and&#13;&lt;br/&gt;  just general questions are always welcome.  Ways you can submit feedback:&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://forum.fifesoft.com (preferred)&#13;&lt;br/&gt;         Has a forum for AutoComplete and related projects, where you can&#13;&lt;br/&gt;         ask questions and get feedback quickly.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * https://sourceforge.net/projects/rsyntaxtextarea/&#13;&lt;br/&gt;         RSyntaxTextArea and AutoComplete's home on SourceForge.  Don't use&#13;&lt;br/&gt;         the forums here (use the first link above), but you can use the&#13;&lt;br/&gt;         \"Tracker\" section here to post bug reports and feature requests.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Other Links&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://fifesoft.com/autocomplete&#13;&lt;br/&gt;         Project home page, which contains general information and example&#13;&lt;br/&gt;         source code.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://fifesoft.com/rsyntaxtextarea&#13;&lt;br/&gt;         The source code editor you're (probably) already using if you're&#13;&lt;br/&gt;         using this AutoComplete library.&#13;&lt;br/&gt;&#13;&lt;br/&gt;    * http://javadoc.fifesoft.com/rsyntaxtextarea/&#13;&lt;br/&gt;    * http://javadoc.fifesoft.com/autocomplete/&#13;&lt;br/&gt;         API documentation for the package.  Note that this *will* change as&#13;&lt;br/&gt;         the library matures.&#13;&lt;br/&gt;&#13;&lt;br/&gt;* Thanks&#13;&lt;br/&gt;&#13;&lt;br/&gt;  The left and right arrow icons in the Help \"tooltip\" window are from the&#13;&lt;br/&gt;  \"Silk\" icon set, under the Creative Commons 3.0 License.  This is a wonderful&#13;&lt;br/&gt;  icon set, found here:&#13;&lt;br/&gt;  &#13;&lt;br/&gt;     http://famfamfam.com/lab/icons/silk/&#13;&lt;br/&gt;&#13;&lt;br/&gt;  The \"bullet_black.png\" icon is an edited version of \"bullet_black.png\" in the&#13;&lt;br/&gt;  Silk icon set noted above.  It can be considered to be distributed under the&#13;&lt;br/&gt;  same Creative Commons 3.0 License.&#13;&lt;br/&gt;  &#13;&lt;br/&gt;  \"osx_sizegrip.png\" is a reproduction of the size grip used on native OS X&#13;&lt;br/&gt;  windows.  It is distributed under the same modified BSD license as the meat&#13;&lt;br/&gt;  of the AutoComplete library.</sampleSolution></textComparisonSubTaskDef></Memento>".getBytes());
			this.setSize(600, 300);
		}
		System.out.println(memento);
		Element mementoTaskDef = XMLBase64.base64StringToElement(memento, null);
		Element initialText = (Element) mementoTaskDef.getElementsByTagName("initialText").item(0);
		Element availableTags = (Element) mementoTaskDef.getElementsByTagName("availableTags").item(0);
		String text = initialText.getFirstChild().getNodeValue();
		text = text.replaceAll("<br/>", "\n");
		boolean view_only = Boolean.parseBoolean(this.getParameter("viewOnly")); // correcor shouldn't be able to manipulate result
		//Element avaiableTags = XMLBase64.base64StringToElement(xmldef, null);
		jpanel = new CompareTextPanel(text, sofar, availableTags, view_only, this.getWidth(), this.getHeight());
		//jpanel.setSize(800, 400);
		add(jpanel);
        Timer timer = new Timer();	
        task = new Task(this);
        timer.schedule  ( task, 1000, 1500 );
	}
	public String getResult() {
		return jpanel.getRightTextAreaContent();
	}
	public boolean hasChanged() {
		return false;
		/*
		 * Anmerkung:
		 * Text wird bearbeitet -> unterscheidet sich also und liefert true zurück
		 * ----> bei speichern wird daran aber nichts geändert
		 * ------> es kommt jedes mal der aufruf, dass noch nicht alle änderungen gespeichert wurden
		 * ---> soll aber nur kommen, wenn etwas geändert wird und dann auf 
		 *      abgeben statt auf speichern geklickt wurde
		 * (Yves)      
		 */
		//return jpanel.getRightTextAreaContent() != jpanel.getLeftTextAreaContent();
	}
	@Override
	public void start() {
	}
	@Override
	public void stop() {
	}
	
	public void draw(){	        	        
        task.setTest();	              
    }
}

class Task extends TimerTask{
	private CompareTextApplet a;
    private boolean test = false;
    private int count = 0;
	public Task(CompareTextApplet a){
		this.a = a;
	}
    
    public void setTest(){
      this.test = true;
    }
	 public void run()  {      
        if(test || (count < 2 && count != 0)){
        //a.drawAll();
            a.paintAll(a.getGraphics());            
          //a.repaint();            
            test = false;     
            count++;
        }
        if (count >= 2) count = 0;
	  }	
}
