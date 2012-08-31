package com.spiru.dev.compareTextTask_addon;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoundedRangeModel;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import com.spiru.dev.compareTextTask_addon.Utils.CompareTextCompletionProvider;

/**
 *
 * @author C.Wilhelm
 */
public class CompareTextPanel extends JPanel {

	private static String sentenceEndingCharacters = ".!?\"'";
	private static String defaultFontSizeLabel = "Default Font Size";
	private static String defaultFontSize = "13";
	private BoundedRangeModel independentScrollbarModel;
	private CompareTextCompletionProvider completionp; // handles Help text, etc
	private String initialText;
	private JLabel helpPane;
	private JSplitPane splitPane;
	private JScrollPane scrollPaneLeft;
	private JScrollPane scrollPaneRight;
	private JTextArea textAreaLeft;
	private JTextArea textAreaRight;
	private int splitPlaneHeight;
	private int splitPlaneWidth;
	private int lastCaretPosRight;
	private JToolBar toolBar;
	private JToggleButton toggleHelpButton;
	private JToggleButton toggleSyncButton;
	private JComboBox fontComboBox;

	public CompareTextPanel(final String initialText, final String tagDefinitionString, final int Width, final int Height) {

		splitPane = new JSplitPane();
		scrollPaneLeft = new JScrollPane();
		scrollPaneRight = new JScrollPane();
		textAreaLeft = new RSyntaxTextArea(); // JTextArea();
		textAreaRight = new RSyntaxTextArea(); // JTextArea();

		toolBar = new JToolBar();
		fontComboBox = new JComboBox();
		toggleHelpButton = new JToggleButton("Help", false);
		toggleSyncButton = new JToggleButton("Sync Scrollbars", true);

		initToolbar();

		lastCaretPosRight = 0;
		completionp = new CompareTextCompletionProvider(tagDefinitionString);
		helpPane = new JLabel("<html>" + completionp.generateHelpHtml() + "</html>");
		independentScrollbarModel = null;
		splitPlaneWidth = Width;
		splitPlaneHeight = Height - 30; //toolBar.getSize().height;
		this.initialText = initialText;

		initMainWindow();
		//enableAutoCompletion();
		textAreaRight.setCaretPosition(0);
	}

	public String getRightTextAreaContent() {
		return textAreaRight.getText();
	}

	private int calculateSentenceAtPosition(final char[] txt, final int sentencePosBeg) {
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

	private void highlightSentence(final int sentenceNumber) {
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

	public void onCaretMove(final int dot, final int mark) {
		// check if still in the same sentence (in the right Pane)
		char[] text_between;
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
	}

	/**
	 * causes the two scrollbars to be in sync or,
	 * if they are in sync already, calling sync_scrollbars()
	 * another time causes them to be independent again
	 * (works back and forth)
	 * 
	 * @see com.spiru.dev.compareTextTask_addon.CompareTextPanel.initToolbar().new ActionListener() {...}.actionPerformed(ActionEvent)
	 */
	private void sync_scrollbars() {
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
	
	private void initToolbar() {
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

	private void initMainWindow() {
		textAreaLeft.setLineWrap(true);
		textAreaRight.setLineWrap(true);
		textAreaLeft.setText(initialText);
		textAreaRight.setText(initialText);

		textAreaLeft.setEditable(false); // left area contains original
		((RSyntaxTextArea)textAreaLeft).setHighlightCurrentLine(false);
		((RSyntaxTextArea)textAreaRight).setHighlightCurrentLine(false);
		((RSyntaxTextArea)textAreaLeft).setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		((RSyntaxTextArea)textAreaRight).setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);

		// Adjust Divider of SplitPlane
		splitPane.setDividerSize(5); // width of the divider
		splitPane.setEnabled(false); // won't let resize the areas
		splitPane.setResizeWeight(.5d); // give both 50%

		// Configure Scrollbars
		scrollPaneLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// Sync Scrollbars, hold old Scrollbar Model as a reference
		sync_scrollbars(); // requires to sync textarea sizes, too...
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

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, splitPlaneWidth, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, splitPlaneWidth, Short.MAX_VALUE))))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, splitPlaneHeight, Short.MAX_VALUE))
		);
	}

	private void enableAutoCompletion() {
		AutoCompletion ac = new AutoCompletion(completionp);
		ac.install(textAreaRight);
		ac.setAutoActivationEnabled(true);
		ac.setAutoCompleteEnabled(true);
		//ac.setParameterAssistanceEnabled(true); // might come as a requirement
		ac.setShowDescWindow(true); // show help text alongside completions
	}
}
