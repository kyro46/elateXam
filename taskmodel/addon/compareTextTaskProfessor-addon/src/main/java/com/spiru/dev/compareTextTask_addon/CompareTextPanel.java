package com.spiru.dev.compareTextTask_addon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;

import javax.swing.BoundedRangeModel;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.spiru.dev.compareTextTask_addon.Utils.XmlPane;

/**
 *
 * @author C.Wilhelm
 */
@SuppressWarnings("serial")
public class CompareTextPanel extends JPanel {

	protected static String sentenceEndingCharacters = ".!?\"'";
	protected static String defaultFontSizeLabel = "Default Font Size";
	protected static String defaultFontSize = "14";
	protected BoundedRangeModel independentScrollbarModel;
	protected String initialText;
	protected String sofarResult;
	protected boolean viewOnly;
	protected HashMap<String,String> tagList;
	protected JLabel helpPane;
	protected JSplitPane splitPane;
	protected JScrollPane scrollPaneLeft;
	protected JScrollPane scrollPaneRight;
	protected XmlPane textAreaLeft;
	protected XmlPane textAreaRight;
	protected int splitPaneHeight;
	protected int splitPaneWidth;
	protected int lastCaretPosRight;
	protected JToolBar toolBar;
	protected JToggleButton toggleHelpButton;
	protected JToggleButton toggleSyncButton;
	protected JComboBox fontComboBox;

	public CompareTextPanel(final String initial_text, final String sofar, final Element xmldefs, boolean view_only, final int Width, final int Height) {

		splitPane = new JSplitPane();
		scrollPaneLeft = new JScrollPane();
		scrollPaneRight = new JScrollPane();
		textAreaLeft = new XmlPane(); // new RSyntaxTextArea(); // JTextArea
		textAreaRight = new XmlPane(); // new RSyntaxTextArea(); // JTextArea

		// font
		//Font font = new JTextArea().getFont();
		Font font = new Font("Verdana", Font.PLAIN, 14);
		System.out.println(font);
		textAreaLeft.setFont(font);
		textAreaRight.setFont(font);
		

		toolBar = new JToolBar();
		fontComboBox = new JComboBox();
		toggleHelpButton = new JToggleButton("Help", false);
		toggleSyncButton = new JToggleButton("Sync Scrollbars", false);

		tagList = new HashMap<String,String>(); // seperate place to store tag info
		String helpHtmlStr = initTagListAndHelp(xmldefs);
		helpHtmlStr += "<br><p width=260>Sie können im rechten Textfeld Änderungen vornehmen und Textstellen mit den entsprechenden Tags umschließen.</p>";
		helpPane = new JLabel("<html>" + helpHtmlStr + "</html>");

		initToolbar();

		lastCaretPosRight = 0;
		independentScrollbarModel = null;
		splitPaneWidth = Width;
		splitPaneHeight = Height - 35; //toolBar.getSize().height;
		initialText = initial_text;
		sofarResult = sofar;
		viewOnly = view_only;

		initScrollPanes();
		//enableAutoCompletion();
		//System.out.println(this.getClass().getName());
		if (! this.getClass().getName().contains("CompareTextProfessorenPanel")) {
			initStudentView();
		}
	}

	public String getRightTextAreaContent() {
		return textAreaRight.getText();
	}

	public String getLeftTextAreaContent() {
		return textAreaLeft.getText();
	}

	protected int calculateSentenceAtPosition(final char[] txt, final int sentencePosBeg) {
		int currentSentence = 1;
		for(int i = 0;i < txt.length; i++) {
			if (sentenceEndingCharacters.indexOf(txt[i]) != -1) {
				if (i >= sentencePosBeg)
					return currentSentence;
				currentSentence++;
			}
		}
		return -1;
	}

	protected void highlightSentence(final int sentenceNumber) {
		char[] right_text = textAreaLeft.getText().toCharArray();
		int currentSentence = 0, sentencePosBeg = 0, sentencePosEnd = 0;
		for(int i = 0;i < right_text.length; i++) {
			if (sentenceEndingCharacters.indexOf(right_text[i]) != -1) {
				sentencePosBeg = sentencePosEnd;
				sentencePosEnd = i+1;
				currentSentence++;
				if (currentSentence == sentenceNumber)
					break;
			}
		}
		try {
			// new DefaultHighlightPainter(Color.YELLOW)
			textAreaLeft.getHighlighter().addHighlight(sentencePosBeg, sentencePosEnd, DefaultHighlighter.DefaultPainter);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	protected void highlightXmlTags() {
		Highlighter highlighter = textAreaRight.getHighlighter();
		highlighter.removeAllHighlights();
		String txt = textAreaRight.getText();
		int tagBeg = -1, tagEnd = -1;
		DefaultHighlightPainter paint = new DefaultHighlightPainter(Color.GREEN);
		for(int i = 0; i < txt.length(); i++) {
			char c = txt.charAt(i);
			if(c=='<') tagBeg = i;
			else if(c=='>') {
				tagEnd = i+1;
				if(tagBeg != -1) try {
					highlighter.addHighlight(tagBeg, tagEnd, paint);
				} catch (BadLocationException e) {
					System.out.println(e);
				}
				// reset tagBeg/tagEnd
				tagBeg = -1;
				tagEnd = -1;
			}
		}
	}

	public void onCaretMove(final int dot, final int mark) {
		// check if still in the same sentence (in the right Pane)
		char[] text_between;
		try {
			if (dot > lastCaretPosRight) {
				text_between = textAreaRight.getText().substring(lastCaretPosRight, dot).toCharArray();
			} else {
				text_between = textAreaRight.getText().substring(dot, lastCaretPosRight).toCharArray();
			}
			//System.out.println(text_between);
			for(int i = 0;i < text_between.length; i++) {
				if (sentenceEndingCharacters.indexOf(text_between[i]) != -1) {
					//System.out.println(text_between);
					char[] right_text = textAreaRight.getText().toCharArray();
					int sentenceNumber = calculateSentenceAtPosition(right_text, dot);
					textAreaLeft.getHighlighter().removeAllHighlights();
					highlightSentence(sentenceNumber);
					break;
				}
			}
			lastCaretPosRight = dot;
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * causes the two scrollbars to be in sync or,
	 * if they are in sync already, calling sync_scrollbars()
	 * another time causes them to be independent again
	 * (works back and forth)
	 *
	 * @see com.spiru.dev.compareTextTask_addon.CompareTextPanel.initToolbar().new ActionListener() {...}.actionPerformed(ActionEvent)
	 */
	protected void sync_scrollbars() {
		if (independentScrollbarModel != null) { // already in sync
			scrollPaneLeft.getVerticalScrollBar().setModel(independentScrollbarModel);
			independentScrollbarModel = null;
			// fix scrollbar position (would lag behind after un-syncing)
			int rightval = scrollPaneRight.getVerticalScrollBar().getValue();
			scrollPaneLeft.getVerticalScrollBar().setValue(rightval);
		} else {
			independentScrollbarModel = scrollPaneLeft.getVerticalScrollBar().getModel(); // keep reference
			scrollPaneLeft.getVerticalScrollBar().setModel(scrollPaneRight.getVerticalScrollBar().getModel());
			scrollPaneLeft.getViewport().setViewPosition(scrollPaneRight.getViewport().getViewPosition());
		}
	}

	protected void initToolbar() {
		toolBar.setFloatable(false);
		toolBar.add(toggleHelpButton);
		toolBar.add(toggleSyncButton);
		toolBar.add(Box.createHorizontalGlue());
		fontComboBox.setModel(new DefaultComboBoxModel(new String[] { defaultFontSizeLabel, "8", "9", "10", "11",
				"12", "13", "14", "15", "16", "18", "20", "24", "28", "30", "36", "48", "64", "72" }));
		fontComboBox.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String itemStr = (String)cb.getSelectedItem();
				if (itemStr == defaultFontSizeLabel)
					itemStr = defaultFontSize;
				float newfontsize = Float.parseFloat(itemStr);
				textAreaLeft.setFont(textAreaLeft.getFont().deriveFont(newfontsize));
				textAreaRight.setFont(textAreaRight.getFont().deriveFont(newfontsize));
			}
		});
		toolBar.add(fontComboBox);
		toolBar.setEnabled(false); // set not movable
		toggleHelpButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (scrollPaneLeft.getViewport().getView() != helpPane) {
					if(toggleSyncButton.isSelected())
						sync_scrollbars();
					scrollPaneLeft.setViewportView(helpPane);
					scrollPaneLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				} else {
					scrollPaneLeft.setViewportView(textAreaLeft);
					if(toggleSyncButton.isSelected())
						sync_scrollbars();
					scrollPaneLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				}
			}
		});
		toggleSyncButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				// ignore if Help pane is on the left
				if (scrollPaneLeft.getViewport().getView() != helpPane)
					sync_scrollbars();
				else // revert state of the button, as if it was never clicked
					toggleSyncButton.setSelected(!toggleSyncButton.isSelected());
			}
		});
	}

	protected String initTagListAndHelp(Element availableTags) {
		if (availableTags == null) return null;
		String ret = "<table border=1 width=260>\n";
		ret += "<th>Tag</th><th>Beschreibung</th>\n";
		NodeList tags = availableTags.getElementsByTagName("tag");
		for (int i = 0; i < tags.getLength(); i++) {
			Element tag = (Element) tags.item(i);
			Element desc = (Element) tag.getElementsByTagName("desc").item(0);
			String tagname = tag.getAttribute("name");
			String description = desc.getTextContent();
			tagList.put(tagname, description);
			ret += "<tr><td>" + tagname + "</td><td>" + description + "</td></tr>\n";
		}
		ret += "</table>";
		return ret;
	}

	/*protected void enableAutoCompletion() {
		CompareTextCompletionProvider completionp = new CompareTextCompletionProvider(tagList);
		AutoCompletion ac = new AutoCompletion(completionp);
		ac.install(textAreaRight);
		ac.setAutoActivationEnabled(true);
		ac.setAutoCompleteEnabled(true);
		ac.setParameterAssistanceEnabled(false); // might come as a requirement
		ac.setShowDescWindow(true); // show help text alongside completions
	}*/

	protected void initScrollPanes() {
		// Adjust Divider of SplitPlane
		splitPane.setDividerSize(5); // width of the divider
		splitPane.setEnabled(false); // won't let resize the areas
		splitPane.setResizeWeight(.5d); // give both 50%

		// Configure Scrollbars
		scrollPaneLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textAreaRight.addComponentListener(new ComponentListener() {
			@Override public void componentResized(ComponentEvent e) {
				Dimension d = textAreaRight.getSize();
				textAreaLeft.setSize(d);
				textAreaLeft.setPreferredSize(d);
				textAreaLeft.setMinimumSize(d);
			}
			@Override public void componentHidden(ComponentEvent e) {}
			@Override public void componentMoved(ComponentEvent e) {}
			@Override public void componentShown(ComponentEvent e) {}
		});

		// React on Cursor movement in the right textArea: Highlight Sentence on the Left side
		textAreaRight.addCaretListener(new CaretListener() {
			@Override public void caretUpdate(CaretEvent e) {
				onCaretMove(e.getDot(), e.getMark());
			}
		});

		scrollPaneLeft.setViewportView(textAreaLeft);
		scrollPaneRight.setViewportView(textAreaRight);
		splitPane.setLeftComponent(scrollPaneLeft);
		splitPane.setRightComponent(scrollPaneRight);

		// consider putting some of this into initStudentView()
		//((RSyntaxTextArea)textAreaLeft).setHighlightCurrentLine(false);
		//((RSyntaxTextArea)textAreaRight).setHighlightCurrentLine(false);
		//((RSyntaxTextArea)textAreaLeft).setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		//((RSyntaxTextArea)textAreaRight).setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		//textAreaLeft.setLineWrap(true);
		//textAreaRight.setLineWrap(true);
	}

	protected void initStudentView() {
		// Settings not relevant for the Professor View
		textAreaLeft.setEditable(false); // left area contains original
		textAreaRight.setEditable(!viewOnly); // correctors won't be able to edit
		toggleSyncButton.setSelected(!toggleSyncButton.isSelected());
		sync_scrollbars(); // Sync Scrollbars by default

		textAreaLeft.setText(initialText);
		if ( sofarResult == null || sofarResult.equals("EMPTY") ) // see SubTasklet_CompareTextTaskImpl.getResult()
			textAreaRight.setText(initialText);
		else textAreaRight.setText(sofarResult);

		if (viewOnly) {
			splitPane.remove(scrollPaneLeft);
			splitPane.remove(scrollPaneRight);
			JLabel labelLeft = new JLabel("Sample Solution:");
			JSplitPane horizontalSplitPaneLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, labelLeft, scrollPaneLeft);
			horizontalSplitPaneLeft.setDividerSize(0);
			horizontalSplitPaneLeft.setEnabled(false);
			JLabel labelRight = new JLabel("Student Solution:");
			JSplitPane horizontalSplitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, labelRight, scrollPaneRight);
			horizontalSplitPaneRight.setDividerSize(0);
			horizontalSplitPaneRight.setEnabled(false);
			splitPane.setLeftComponent(horizontalSplitPaneLeft);
			splitPane.setRightComponent(horizontalSplitPaneRight);
		}

		this.setLayout(new BorderLayout());
		this.add(toolBar, BorderLayout.PAGE_START);
		this.add(splitPane, BorderLayout.CENTER);
		toolBar.setPreferredSize(new Dimension(splitPaneWidth, 25));
		splitPane.setPreferredSize(new Dimension(splitPaneWidth, splitPaneHeight));
	}
}
