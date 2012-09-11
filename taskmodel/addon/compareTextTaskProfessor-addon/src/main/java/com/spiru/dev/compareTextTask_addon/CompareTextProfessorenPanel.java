package com.spiru.dev.compareTextTask_addon;

import java.awt.Insets;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	protected javax.swing.JButton buttonUpload;
	protected javax.swing.table.DefaultTableModel tableModel;
	protected javax.swing.JTable tablePanel;
	protected String sampleSolutionText;
	//protected JFileChooser fileChooser;
	protected int componentAvaiableTagsHeight;
	protected int componentInitialTextHeight;
	protected int paneWidth;

	public CompareTextProfessorenPanel(String initial_text, String solution, Element xmldefs, int Width, int Height) {
		super(initial_text, null, xmldefs, false, Width, Height);
		labelAvaiableTags = new javax.swing.JLabel("Avaiable Tags:");
		labelInitialText = new javax.swing.JLabel("Initial Text:");
		labelSampleSolution = new javax.swing.JLabel("Sample Solution:");
		scrollPaneAvaiableTags = new javax.swing.JScrollPane();
		tablePanel = new javax.swing.JTable();
		buttonMinus = new javax.swing.JButton();
		buttonPlus = new javax.swing.JButton();
		buttonUpload = new javax.swing.JButton();
		//fileChooser = new JFileChooser(System.getProperty("user.home"));
		componentAvaiableTagsHeight = Height / 2 - 30;
		componentInitialTextHeight = Height / 2 - 25;
		paneWidth = Width;
		sampleSolutionText = solution;
		initTable();
		initTagTable();
		initButtons();
		initProfessorView(true);
	}

	protected void initButtons() {
		buttonUpload.setText("Upload Text File");
		/*buttonUpload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int flag = fileChooser.showOpenDialog(null);
				if (flag == JFileChooser.APPROVE_OPTION)
					try {
						System.out.println("You chose to open this file: " + fileChooser.getSelectedFile().getName());
						String tmp = new Scanner(fileChooser.getSelectedFile()).useDelimiter("\\Z").next();
						textAreaLeft.setText(tmp);
						textAreaRight.setText(tmp);
					} catch (Exception e) {
						javax.swing.JOptionPane.showMessageDialog(buttonUpload, "Error: No valid Plain-Text File:\n"
								+ e.fillInStackTrace(), "Invalid File Error", javax.swing.JOptionPane.ERROR_MESSAGE);
					}
			}
		});*/
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
				tableModel.addRow(new Object [] {null, null});
			}
		});
	}

	protected void initTable() {
		//tableAvaiableTags.setAutoCreateRowSorter(true);
		/*tableModel = new javax.swing.table.DefaultTableModel(
				new Object [][] {
						{"example", "Example Tag with Description, please replace this line."},
						{null, null}
				},
				new String [] { "Tag Name", "Description" }
				);*/
		tableModel = new javax.swing.table.DefaultTableModel(new String [] { "Tag Name", "Description" }, 0);
		tablePanel.setModel(tableModel);
		scrollPaneAvaiableTags.setViewportView(tablePanel);
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

	public void appendAvaiableTags(Element addonConfig) {
		// Fix issue described here: http://stackoverflow.com/questions/1652942/can-a-jtable-save-data-whenever-a-cell-loses-focus
		if (tablePanel.isEditing())
			tablePanel.getCellEditor().stopCellEditing();
		// Write tags and Description into a new avaiableTags Element
		Document document = addonConfig.getOwnerDocument();
		Element avaiableTags = document.createElement("avaiableTags");
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
				description = "ERROR in getAvaiableTags()";
			Element Tag = document.createElement("tag");
			Element desc = document.createElement("desc");
			Tag.setAttribute("name", tagname);
			desc.setTextContent(description);
			Tag.appendChild(desc);
			avaiableTags.appendChild(Tag);
		}
		addonConfig.appendChild(avaiableTags);
	}

	protected void initTagTable() {
		// by now, initTagListAndHelp() was already called, so we have access to tagList
		for (Map.Entry<String, String> entry : tagList.entrySet()) {
			String tagname = entry.getKey();
			String description = entry.getValue();
			tableModel.addRow(new Object[] {tagname, description}); // final step!
		}
		tableModel.addRow(new Object[] {"", ""});
	}
}
