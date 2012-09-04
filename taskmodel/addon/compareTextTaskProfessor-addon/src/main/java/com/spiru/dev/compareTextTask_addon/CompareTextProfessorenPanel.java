package com.spiru.dev.compareTextTask_addon;

import java.awt.Insets;

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
		componentAvaiableTagsHeight = Height / 2 - 25;
		componentInitialTextHeight = Height / 2 - 25;
		paneWidth = Width;
		initTable();
		initButtons();
		initProfessorView(true);
	}

	protected void initButtons() {
		buttonMinus.setText("-");
		buttonMinus.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int s[] = tablePanel.getSelectedRows();
				for (int i = 0; i < s.length; i++) {
					if (tableModel.getRowCount() > 1) // would throw exception if not checked
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
		tableModel = new javax.swing.table.DefaultTableModel(
				new Object [][] {
						{"example", "Example Tag with Description, please replace this line."},
						{null, null}
				},
				new String [] { "Tag Name", "Description" }
				);
		tablePanel.setModel(tableModel);
		scrollPaneAvaiableTags.setViewportView(tablePanel);
	}

	protected void initProfessorView(boolean textfirst) {
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

	public String getAvaiableTags() {
		return "<some><tags>";
	}

	public String getSampleSolution() {
		return textAreaRight.getText();
	}
}
