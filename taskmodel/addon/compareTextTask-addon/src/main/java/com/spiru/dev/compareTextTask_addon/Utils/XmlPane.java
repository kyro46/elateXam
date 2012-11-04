package com.spiru.dev.compareTextTask_addon.Utils;

import javax.swing.text.StyledEditorKit;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.WrappedPlainView;

@SuppressWarnings("serial")
public class XmlPane extends javax.swing.JTextPane {

	public XmlPane() {
		super();
		this.setEditorKit(new XmlEditorKit());
	}

	/*
	 * Copyright 2006-2008 Kees de Kooter
	 * http://www.boplicity.nl/confluence/display/Java/Xml+syntax+highlighting+in+Swing+JTextPane
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License");
	 * you may not use this file except in compliance with the License.
	 * You may obtain a copy of the License at
	 *
	 *      http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS,
	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 * See the License for the specific language governing permissions and
	 * limitations under the License.
	 */
	public class XmlView extends WrappedPlainView {
		private HashMap<Pattern, Color> patternColors;
		private final String TAG_PATTERN = "(</?\\w+)\\s*>?";
		private final String TAG_END_PATTERN = "(/>)";
		public XmlView(Element element) {
			super(element);
			patternColors = new HashMap<Pattern, Color>();
			patternColors.put(Pattern.compile(TAG_PATTERN), Color.RED); // , new Color(127, 0, 85)
			patternColors.put(Pattern.compile(TAG_END_PATTERN), Color.RED);
			// Set tabsize to 4 (instead of the default 8)
			getDocument().putProperty(PlainDocument.tabSizeAttribute, 4);
		}
		@Override
		protected int drawUnselectedText(Graphics graphics, int x, int y, int p0, int p1) throws BadLocationException {
			Document doc = getDocument();
			String text = doc.getText(p0, p1 - p0);
			Segment segment = getLineBuffer();
			SortedMap<Integer, Integer> startMap = new TreeMap<Integer, Integer>();
			SortedMap<Integer, Color> colorMap = new TreeMap<Integer, Color>();
			// Match all regexes on this snippet, store positions
			for (Map.Entry<Pattern, Color> entry : patternColors.entrySet()) {
				Matcher matcher = entry.getKey().matcher(text);
				while (matcher.find()) {
					startMap.put(matcher.start(1), matcher.end());
					colorMap.put(matcher.start(1), entry.getValue());
				}
			}
			int i = 0;
			// Colour the parts
			for (Map.Entry<Integer, Integer> entry : startMap.entrySet()) {
				int start = entry.getKey();
				int end = entry.getValue();
				if (i < start) {
					graphics.setColor(Color.black);
					doc.getText(p0 + i, start - i, segment);
					x = Utilities.drawTabbedText(segment, x, y, graphics, this, i);
				}
				graphics.setColor(colorMap.get(start));
				i = end;
				doc.getText(p0 + start, i - start, segment);
				x = Utilities.drawTabbedText(segment, x, y, graphics, this, start);
			}
			// Paint possible remaining text black
			if (i < text.length()) {
				graphics.setColor(Color.black);
				doc.getText(p0 + i, text.length() - i, segment);
				x = Utilities.drawTabbedText(segment, x, y, graphics, this, i);
			}
			return x;
		}
	}
	public class XmlEditorKit extends StyledEditorKit {
		private ViewFactory xmlViewFactory;
		public XmlEditorKit() {
			xmlViewFactory = new XmlViewFactory();
		}
		@Override
		public ViewFactory getViewFactory() {
			return xmlViewFactory;
		}
		@Override
		public String getContentType() {
			return "text/xml";
		}
	}
	public class XmlViewFactory extends Object implements ViewFactory {
		public View create(Element element) {
			return new XmlView(element);
		}

	}
}
