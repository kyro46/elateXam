package com.spiru.dev.compareTextTask_addon;

import java.awt.Insets;
<<<<<<< HEAD
import java.io.File;
=======
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
>>>>>>> 7ba09ebdeb00caf1ed4004c1711bb25a1de2ddb6
import java.util.Iterator;
import java.util.Vector;

<<<<<<< HEAD
=======
import javax.swing.AbstractAction;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

>>>>>>> 7ba09ebdeb00caf1ed4004c1711bb25a1de2ddb6
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.spiru.dev.compareTextTask_addon.Utils.TableCellListener;

/**
 * TODO: raise (and handle) Exception if entered tag contains illegal characters
 *
 * @author C.Wilhelm
 */
@SuppressWarnings("serial")
public class CompareTextProfessorenPanel extends CompareTextPanel {
	protected javax.swing.JLabel labelAvaiableTags;
	protected javax.swing.JLabel labelInitialText;
	protected javax.swing.JLabel labelSampleSolution;
	protected javax.swing.JScrollPane scrollPaneAvaiableTags;
	protected javax.swing.JButton buttonMinus;
	protected javax.swing.JButton buttonPlus;
	protected javax.swing.table.DefaultTableModel tableModel;
	protected javax.swing.JTable tablePanel;
	protected String sampleSolutionText;
<<<<<<< HEAD
	protected String lastDirectory;
=======
>>>>>>> 7ba09ebdeb00caf1ed4004c1711bb25a1de2ddb6
	protected int componentAvaiableTagsHeight;
	protected int componentInitialTextHeight;
	protected int paneWidth;

	public CompareTextProfessorenPanel(String text, String xmldef, String solution, int Width, int Height) {
		super(text, xmldef, Width, Height);
		labelAvaiableTags = new javax.swing.JLabel("Avaiable Tags:");
		labelInitialText = new javax.swing.JLabel("Initial Text:");
		labelSampleSolution = new javax.swing.JLabel("Sample Solution:");
		scrollPaneAvaiableTags = new javax.swing.JScrollPane();
		tablePanel = new javax.swing.JTable();
		buttonMinus = new javax.swing.JButton();
		buttonPlus = new javax.swing.JButton();
<<<<<<< HEAD
		buttonUpload = new javax.swing.JButton();
=======
>>>>>>> 7ba09ebdeb00caf1ed4004c1711bb25a1de2ddb6
		componentAvaiableTagsHeight = Height / 2 - 30;
		componentInitialTextHeight = Height / 2 - 25;
		paneWidth = Width;
		sampleSolutionText = solution;
<<<<<<< HEAD
		lastDirectory = null;
		initTagTable();
=======
		initTable();
		setAvaiableTags(xmldef);
>>>>>>> 7ba09ebdeb00caf1ed4004c1711bb25a1de2ddb6
		initButtons();
		initProfessorView(true);
	}

	protected void initButtons() {
<<<<<<< HEAD
		buttonUpload.setText("Upload Text File");
		buttonUpload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String tmp = "";
				try {
					if (lastDirectory == null) lastDirectory = System.getProperty("user.home");
					javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser(lastDirectory);
					int flag = fileChooser.showOpenDialog(null);
					if (flag == javax.swing.JFileChooser.APPROVE_OPTION) {
						File tmpfile =  fileChooser.getSelectedFile();
						tmp = new Scanner(tmpfile).useDelimiter("\\Z").next();
						/*int len;
						char[] chr = new char[4096];
						final StringBuffer buffer = new StringBuffer();
						final FileReader reader = new FileReader(tmpfile);
						while ((len = reader.read(chr)) > 0)
							buffer.append(chr, 0, len);
						reader.close();
						tmp = buffer.toString();*/
						textAreaLeft.setText(tmp);
						textAreaRight.setText(tmp);
						// scroll to the top on both sides
						textAreaLeft.setCaretPosition(0);
						textAreaRight.setCaretPosition(0);
					}
				} catch (Exception e) {
					javax.swing.JOptionPane.showMessageDialog(buttonUpload, "Error: No valid Plain-Text File:\n"
							+ e.fillInStackTrace(), "Invalid File Error", javax.swing.JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
=======
>>>>>>> 7ba09ebdeb00caf1ed4004c1711bb25a1de2ddb6
		buttonMinus.setText("-");
		buttonMinus.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int s[] = tablePanel.getSelectedRows();
				for (int i = 0; i < s.length; i++) {
					if (tableModel.getRowCount() == 1) // would throw exception if not checked
						tableModel.addRow(new Object[] {null, null}); // replace with empty line
					tableModel.removeRow(s[i]);
				}
			}
		});
		buttonPlus.setText("+");
		buttonPlus.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				getAvaiableTags();
				tableModel.addRow(new Object [] {null, null});
			}
		});
	}

	protected void initTagTable() {
		tableModel = new javax.swing.table.DefaultTableModel(new String [] { "Tag Name", "Description" }, 0);
		tablePanel.setModel(tableModel);
		scrollPaneAvaiableTags.setViewportView(tablePanel);
<<<<<<< HEAD
		// by now, initTagListAndHelp() was already called, so we have access to tagList
		for (Map.Entry<String, String> entry : tagList.entrySet()) {
			String tagname = entry.getKey();
			String description = entry.getValue();
			tableModel.addRow(new Object[] {tagname, description}); // final step!
		}
		tableModel.addRow(new Object[] {"", ""});
=======
		/*tableModel.addTableModelListener(new TableModelListener() { // no use for this yet
			@Override public void tableChanged(TableModelEvent e) {
				//System.out.println(e);
			}
		});
		@SuppressWarnings("unused")
		TableCellListener tcl = new TableCellListener(tablePanel, new AbstractAction() {
			@Override public void actionPerformed(ActionEvent e) {
				TableCellListener tcl = (TableCellListener)e.getSource();
				System.out.println("Row   : " + tcl.getRow());
				System.out.println("Column: " + tcl.getColumn());
				System.out.println("Old   : " + tcl.getOldValue());
				System.out.println("New   : " + tcl.getNewValue());
			}
		});*/
>>>>>>> 7ba09ebdeb00caf1ed4004c1711bb25a1de2ddb6
	}

	protected void initProfessorView(boolean textfirst) {
		textAreaLeft.setText(initialText);
		textAreaRight.setText(sampleSolutionText);
		textAreaLeft.setEditable(true);
		toolBar.remove(toggleHelpButton);
		buttonMinus.setMargin(new Insets(-10,-2,-8,-2));
		buttonPlus.setMargin(new Insets(-10,-2,-8,-2));
		int lwidth = paneWidth / 2 - 5;

		// fix fontChooser size
		fontComboBox.setSize(260, 25);
		fontComboBox.setPreferredSize(fontComboBox.getSize());
		fontComboBox.setMaximumSize(fontComboBox.getSize());

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addComponent(labelAvaiableTags)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(buttonPlus, 25, 25, 25)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(buttonMinus, 25, 25, 25))
			.addComponent(scrollPaneAvaiableTags)
			.addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, paneWidth, Short.MAX_VALUE)
			.addGroup(layout.createSequentialGroup()
				.addComponent(labelInitialText, lwidth, lwidth, lwidth)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(labelSampleSolution, lwidth, lwidth, lwidth))
		);
		if (textfirst) {
			layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(labelInitialText)
						.addComponent(labelSampleSolution))
					.addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, componentInitialTextHeight, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(buttonMinus, 20, 20, 20)
							.addComponent(buttonPlus, 20, 20, 20))
						.addComponent(labelAvaiableTags, javax.swing.GroupLayout.Alignment.TRAILING))
					.addComponent(scrollPaneAvaiableTags, javax.swing.GroupLayout.PREFERRED_SIZE, componentAvaiableTagsHeight, javax.swing.GroupLayout.PREFERRED_SIZE))
			);
		} else {
			layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(buttonMinus, 20, 20, 20)
							.addComponent(buttonPlus, 20, 20, 20))
						.addComponent(labelAvaiableTags, javax.swing.GroupLayout.Alignment.TRAILING))
					.addComponent(scrollPaneAvaiableTags, javax.swing.GroupLayout.PREFERRED_SIZE, componentAvaiableTagsHeight, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(labelInitialText)
						.addComponent(labelSampleSolution))
					.addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, componentInitialTextHeight, Short.MAX_VALUE))
			);
		}
	}

	public String getInitialText() {
		return textAreaLeft.getText();
	}

	public String getSampleSolution() {
		return textAreaRight.getText();
	}

	public String getAvaiableTags() {
		String ret = "";
		Vector<?> rows = tableModel.getDataVector();
		Iterator<?> iter_rows = rows.iterator();
		// Fix issue described here: http://stackoverflow.com/questions/1652942/can-a-jtable-save-data-whenever-a-cell-loses-focus
		if (tablePanel.isEditing())
			tablePanel.getCellEditor().stopCellEditing();
		//int selected_row = tablePanel.getSelectedRow();
		//int selected_col = tablePanel.getSelectedColumn();
		//Object selected_cell = tablePanel.getValueAt(selected_row, selected_col);
		//System.out.println(tablePanel.getCellEditor(selected_row, selected_col).getCellEditorValue() + " currentCell");
		//System.out.println(selected_cell);
		// Go on
		while(iter_rows.hasNext()) {
			// ([tag,description],...)
			Vector<?> row = (Vector<?>) iter_rows.next();
			String keyword = (String) row.get(0);
			String description = (String) row.get(1);
			if (keyword == null)
				continue;
			if (description == null)
				description = "ERROR in getAvaiableTags()";
			ret += "<keyword>" + keyword + "</keyword><description>" + description + "</description>";
		}
		System.out.println(ret);
		return ret;
	}
<<<<<<< HEAD
=======

	protected void setAvaiableTags(String xmldef) {
		// if there are existing tags (when editing existing questions), xmldef must begin with "<keyword>"
		if (xmldef == null || !xmldef.startsWith("<keyword>")) { // !
			tableModel.addRow(new Object[] {"example", "Example Tag with Description, please replace this line."});
			tableModel.addRow(new Object[] {null, null});
			return; // the other stuff is not relevant in this case
		}
		String xml = "<?xml version=\"1.0\"?><keywords>" + xmldef + "</keywords>"; // "make it wellformed"
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setCoalescing(true); // Convert CDATA to Text nodes
		factory.setNamespaceAware(false); // No namespaces: this is default
		factory.setValidating(false); // Don't validate DTD: also default
		try {
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document document = parser.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
			NodeList keywords = document.getElementsByTagName("keyword");
			NodeList descriptions = document.getElementsByTagName("description");
			if(keywords.getLength() != descriptions.getLength())
				throw new IOException("Number of keywords is different from the Number of descriptions");
			for (int i = 0; i < keywords.getLength(); i++) {
				String keyword = keywords.item(i).getTextContent();
				String description = descriptions.item(i).getTextContent();
				tableModel.addRow(new Object[] {keyword, description}); // final step!
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
>>>>>>> 7ba09ebdeb00caf1ed4004c1711bb25a1de2ddb6
}
