package com.spiru.dev.compareTextTask_addon;

import java.awt.Insets;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * TODO: raise (and handle) Exception if entered tag contains illegal characters
 *
 * @author C.Wilhelm
 */
@SuppressWarnings("serial")
public class CompareTextProfessorenPanel extends CompareTextPanel {
	protected javax.swing.JLabel labelAvailableTags;
	protected javax.swing.JLabel labelInitialText;
	protected javax.swing.JLabel labelSampleSolution;
	protected javax.swing.JScrollPane scrollPaneAvailableTags;
	protected javax.swing.JButton buttonMinus;
	protected javax.swing.JButton buttonPlus;
	protected javax.swing.JButton buttonUpload;
	protected javax.swing.table.DefaultTableModel tableModel;
	protected javax.swing.JTable tablePanel;
	protected String sampleSolutionText;
	protected String lastDirectory;
	protected int componentAvailableTagsHeight;
	protected int componentInitialTextHeight;
	protected int paneWidth;

	public CompareTextProfessorenPanel(String initial_text, String solution, Element xmldefs, int Width, int Height) {
		super(initial_text, null, xmldefs, false, Width, Height);
		labelAvailableTags = new javax.swing.JLabel("Available Tags:");
		labelInitialText = new javax.swing.JLabel("Initial Text:");
		labelSampleSolution = new javax.swing.JLabel("Sample Solution:");
		scrollPaneAvailableTags = new javax.swing.JScrollPane();
		tablePanel = new javax.swing.JTable();
		buttonMinus = new javax.swing.JButton();
		buttonPlus = new javax.swing.JButton();
		buttonUpload = new javax.swing.JButton();
		componentAvailableTagsHeight = Height / 2 - 30;
		componentInitialTextHeight = Height / 2 - 25;
		paneWidth = Width;
		sampleSolutionText = solution;
		lastDirectory = null;
		initTagTable();
		initButtons();
		initProfessorView(true);
	}

	protected void initButtons() {
		buttonUpload.setText("Upload Text File");
		buttonUpload.addActionListener(new java.awt.event.ActionListener() {
			@Override
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
		buttonMinus.setText("-");
		buttonMinus.addActionListener(new java.awt.event.ActionListener() {
			@Override
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
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tableModel.addRow(new Object [] {null, null});
			}
		});
	}

	protected void initTagTable() {
		tableModel = new javax.swing.table.DefaultTableModel(new String [] { "Tag Name", "Description" }, 0);
		tablePanel.setModel(tableModel);
		scrollPaneAvailableTags.setViewportView(tablePanel);
		// by now, initTagListAndHelp() was already called, so we have access to tagList
		for (Map.Entry<String, String> entry : tagList.entrySet()) {
			String tagname = entry.getKey();
			String description = entry.getValue();
			tableModel.addRow(new Object[] {tagname, description}); // final step!
		}
		tableModel.addRow(new Object[] {"", ""});
	}

	protected void initProfessorView(boolean textfirst) {
		textAreaLeft.setText(initialText);
		textAreaRight.setText(sampleSolutionText);
		textAreaLeft.setEditable(true);
		toolBar.remove(toggleHelpButton);
		toolBar.add(buttonUpload, 0);
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
				.addComponent(labelAvailableTags)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(buttonPlus, 25, 25, 25)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(buttonMinus, 25, 25, 25))
			.addComponent(scrollPaneAvailableTags)
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
						.addComponent(labelAvailableTags, javax.swing.GroupLayout.Alignment.TRAILING))
					.addComponent(scrollPaneAvailableTags, javax.swing.GroupLayout.PREFERRED_SIZE, componentAvailableTagsHeight, javax.swing.GroupLayout.PREFERRED_SIZE))
			);
		} else {
			layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(buttonMinus, 20, 20, 20)
							.addComponent(buttonPlus, 20, 20, 20))
						.addComponent(labelAvailableTags, javax.swing.GroupLayout.Alignment.TRAILING))
					.addComponent(scrollPaneAvailableTags, javax.swing.GroupLayout.PREFERRED_SIZE, componentAvailableTagsHeight, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(labelInitialText)
						.addComponent(labelSampleSolution))
					.addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, componentInitialTextHeight, Short.MAX_VALUE))
			);
		}
	}

	public String getInitialText() {
		return textAreaLeft.getText().replaceAll("\n", "<br/>");
	}

	public String getSampleSolution() {
		return textAreaRight.getText().replaceAll("\n", "<br/>");
	}

	public void appendAvailableTags(Element addonConfig) {
		// Fix issue described here: http://stackoverflow.com/questions/1652942/can-a-jtable-save-data-whenever-a-cell-loses-focus
		if (tablePanel.isEditing())
			tablePanel.getCellEditor().stopCellEditing();
		// Write tags and Description into a new availableTags Element
		Document document = addonConfig.getOwnerDocument();
		Element availableTags = document.createElement("availableTags");
		// retrieve information from jTable
		Vector<?> rows = tableModel.getDataVector();
		Iterator<?> iter_rows = rows.iterator();
		while(iter_rows.hasNext()) {
			// ([tag,description],...)
			Vector<?> row = (Vector<?>) iter_rows.next();
			String tagname = ((String) row.get(0)).trim();
			String description = ((String) row.get(1)).trim();
			if (tagname == null || tagname == "")
				continue;
			if (description == null || description == "")
				description = "ERROR in getAvailableTags()";
			Element Tag = document.createElement("tag");
			Element desc = document.createElement("desc");
			Tag.setAttribute("name", tagname);
			desc.setTextContent(description);
			Tag.appendChild(desc);
			availableTags.appendChild(Tag);
		}
		addonConfig.appendChild(availableTags);
	}
}
