package com.spiru.dev.compareTextTask_addon;

import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.autocomplete.*;

import com.spiru.dev.compareTextTask_addon.Utils.CompareTextCompletionProvider;
import com.spiru.dev.compareTextTask_addon.Utils.SwitchSentenceCaretListener;

/**
 *
 * @author C.Wilhelm
 */
public class CompareTextPanel extends javax.swing.JPanel {

	private static String sentenceEndingCharacters = ".!?\"'";
	private javax.swing.JButton helpButton;
	private javax.swing.JScrollPane jScrollPaneLeft;
	private javax.swing.JScrollPane jScrollPaneRight;
	private javax.swing.JSplitPane jSplitPane;
	private RSyntaxTextArea textAreaLeft;
	private RSyntaxTextArea textAreaRight;
	private int splitPlaneHeight;
	private int splitPlaneWidth;
	private String initialText;
	private String buttonText;
	private int lastCaretPosRight;
	private int lastSentenceNumberLeft;

	/**
	 * Creates new form NewJPanel2
	 */
	public CompareTextPanel(String initialText) {
		setSplitPlaneHeight(300);
		setSplitPlaneWidth(600);
		setInitialText(initialText);
		setButtonText("Tags");
		lastCaretPosRight = 0;
		lastSentenceNumberLeft = 0;
		initComponents();
	}
	private int calculatePositionOfSentence(RSyntaxTextArea textArea, final int sentenceNumber) {
		//textAreaRight.setText(new Integer(textArea.getHighlighter().getHighlights().length).toString());
		char[] right_text = textArea.getText().toCharArray();
		int currentSentence = 0, sentencePosBeg = 0, sentencePosEnd = 0;
		for(int i = 0;i < right_text.length; i++) {
			//if (right_text[i] == '\n') {
			if (sentenceEndingCharacters.indexOf(right_text[i]) != -1) {
				sentencePosBeg = sentencePosEnd+1;
				//sentencePosEnd = i+1;
				currentSentence++;
				if (currentSentence == sentenceNumber)
					return sentencePosBeg;
			}
		}
		/*try {
			textArea.getHighlighter().addHighlight(sentencePosBeg, sentencePosEnd, new DefaultHighlightPainter(Color.YELLOW));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}*/
		return -1;
	}
	private int calculateSentenceOfPosition(RSyntaxTextArea textArea, final int sentencePosBeg) {
		char[] right_text = textArea.getText().toCharArray();
		int currentSentence = 0;
		for(int i = 0;i < right_text.length; i++) {
			if (sentenceEndingCharacters.indexOf(right_text[i]) != -1) {
				currentSentence++;
				if (i >= sentencePosBeg)
					return currentSentence;
			}
		}
		return -1;
	}

	public void onCaretMove(final int dot, final int mark) {
		// check if still in the same sentence (in the right Pane)
		char[] text_between;
		if (dot > lastCaretPosRight) {
			text_between = textAreaRight.getText().substring(lastCaretPosRight, dot).toCharArray();
		}
		else {
			text_between = textAreaRight.getText().substring(dot, lastCaretPosRight).toCharArray();
		}
		//System.out.println(text_between);
		for(int i = 0;i < text_between.length; i++) {
			if (sentenceEndingCharacters.indexOf(text_between[i]) != -1) {
				//System.out.println(text_between);
				
				calculatePositionOfSentence(textAreaLeft, 9);
				break;
			}
		}
		lastCaretPosRight = dot;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {

		jSplitPane = new javax.swing.JSplitPane();
		jScrollPaneLeft = new javax.swing.JScrollPane();
		jScrollPaneRight = new javax.swing.JScrollPane();
		textAreaLeft = new RSyntaxTextArea();
		textAreaRight = new RSyntaxTextArea();
		helpButton = new javax.swing.JButton(buttonText);

		textAreaLeft.setLineWrap(true);
		textAreaRight.setLineWrap(true);
		textAreaLeft.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		textAreaRight.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		textAreaLeft.setText(initialText);
		textAreaRight.setText(initialText);

		textAreaLeft.setEditable(false); // left area contains original
		textAreaLeft.setHighlightCurrentLine(false);
		textAreaRight.setHighlightCurrentLine(false);

		jScrollPaneLeft.setViewportView(textAreaLeft);
		jScrollPaneRight.setViewportView(textAreaRight);
		jSplitPane.setLeftComponent(jScrollPaneLeft);
		jSplitPane.setRightComponent(jScrollPaneRight);
		CompletionProvider provider = new CompareTextCompletionProvider();
		AutoCompletion ac = new AutoCompletion(provider);
		ac.install(textAreaRight);
		ac.setAutoActivationEnabled(true);
		ac.setAutoCompleteEnabled(true);
		//ac.setParameterAssistanceEnabled(true);
		

		// Adjust Divider of SplitPlane
		jSplitPane.setDividerSize(5); // width of the divider
		jSplitPane.setEnabled(false); // won't let resize the areas
		jSplitPane.setResizeWeight(.5d); // give both 50%

		// Configure Scrollbars
		//jScrollPaneLeft.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//jScrollPaneRight.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Listen for Scrollbar Events to be able to synchronize Scrolling
		//AdjustmentListener listener = new ScrollBarAdjustmentListener();
		//jScrollPaneLeft.getHorizontalScrollBar().addAdjustmentListener(listener);
		//jScrollPaneRight.getHorizontalScrollBar().addAdjustmentListener(listener);
		jScrollPaneLeft.getVerticalScrollBar().setModel(jScrollPaneRight.getVerticalScrollBar().getModel()); // shorter

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, splitPlaneWidth, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(helpButton)))
										.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, splitPlaneHeight, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(helpButton)
						.addContainerGap())
				);
		//highlightSentence(textAreaLeft, 2);
		textAreaRight.addCaretListener(new SwitchSentenceCaretListener(this));
	}

	public int getSplitPlaneHeight() {
		return splitPlaneHeight;
	}
	public void setSplitPlaneHeight(int splitPlaneHeight) {
		this.splitPlaneHeight = splitPlaneHeight;
	}
	public int getSplitPlaneWidth() {
		return splitPlaneWidth;
	}
	public void setSplitPlaneWidth(int splitPlaneWidth) {
		this.splitPlaneWidth = splitPlaneWidth;
	}
	public String getButtonText() {
		return buttonText;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	public String getInitialText() {
		return initialText;
	}
	public void setInitialText(String initialText) {
		this.initialText = initialText;
	}
}
