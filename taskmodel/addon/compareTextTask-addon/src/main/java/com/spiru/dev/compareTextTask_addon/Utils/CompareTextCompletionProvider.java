package com.spiru.dev.compareTextTask_addon.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * CompletionProvider for AutoCompletion in RSyntaxTextArea
 * based on HtmlCompletionProvider
 * 
 * @author C.Wilhelm
 *
 */

/*
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE api SYSTEM "CompletionXml.dtd">
<api language="HTML">
<keywords>
	<keyword name="var" type="tag">
		<desc>Defines a variable</desc>
	</keyword>
	<keyword name="video" type="tag">
		<desc>Defines a video</desc>
	</keyword>
</keywords>
</api>
String s = "<?xml version='1.0' encoding='UTF-8' ?>\n"
				+ "<!DOCTYPE api SYSTEM 'CompletionXml.dtd'>\n"
				+ "<api language='HTML'>\n"
				+ "<keywords>\n"
				+ "	<keyword name='var' type='tag'>\n"
				+ "		<desc>Defines a variable</desc>\n"
				+ "	</keyword>\n"
				+ "	<keyword name='video' type='tag'>\n"
				+ "		<desc>Defines a video</desc>\n"
				+ "	</keyword>\n"
				+ "</keywords>\n"
				+ "</api>";
 */

public class CompareTextCompletionProvider extends HtmlCompletionProvider {

	public CompareTextCompletionProvider() {
		// call Parent constructor
		super();
		// change some settings
		//this.
	}

	@Override
	protected void initCompletions() {
		// TODO: alternative way to load that XML, preferably as String
		String s = "<?xml version='1.0' encoding='UTF-8' ?><!DOCTYPE api SYSTEM 'CompletionXml.dtd'><api language='HTML'><keywords><keyword name='var' type='tag'><desc>Defines a variable</desc></keyword><keyword name='video' type='tag'><desc>Defines a video</desc></keyword></keywords></api>";
		try {
			loadFromXML("/home/rrae/src/SHK2012/Dokumentation/XMLPlayground/html.xml");
			//InputStream is = new ByteArrayInputStream(s.getBytes("UTF-8"));
			//loadFromXML(is);
			//is.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
