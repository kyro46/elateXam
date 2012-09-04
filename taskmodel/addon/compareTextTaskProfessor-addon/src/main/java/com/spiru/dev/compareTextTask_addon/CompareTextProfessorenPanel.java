package com.spiru.dev.compareTextTask_addon;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * TODO: raise (and handle) Exception if entered tag contains illegal characters
 *
 * @author rrae
 */
@SuppressWarnings("serial")
public class CompareTextProfessorenPanel extends CompareTextPanel {
	protected javax.swing.JLabel labelAvaiableTags;
	protected javax.swing.JScrollPane scrollPaneAvaiableTags;
	protected javax.swing.JButton buttonMinus;
	protected javax.swing.JButton buttonPlus;
	protected javax.swing.table.DefaultTableModel tableModel;
	protected javax.swing.JTable tablePanel;
	protected int topPaneHeight;
	protected int bottomPaneHeight;
	protected int paneWidth;
	private GridBagConstraints c;
	private GridBagLayout gbl;

	public CompareTextProfessorenPanel(String text, String xmldef, String solution, int Width, int Height) {
		super(text, xmldef, Width, Height);
		//labelInitialText = new javax.swing.JLabel("Initial Text:");
		labelAvaiableTags = new javax.swing.JLabel("Avaiable Tags:");
		//labelSampleSolution = new javax.swing.JLabel("Sample Solution:");
		scrollPaneAvaiableTags = new javax.swing.JScrollPane();
		tablePanel = new javax.swing.JTable();
		buttonMinus = new javax.swing.JButton();
		buttonPlus = new javax.swing.JButton();
		topPaneHeight = Height / 2 - 25;
		bottomPaneHeight = Height / 2 - 25;
		paneWidth = Width;
		initTable();
		initButtons();
		initProfessorView();
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

	protected void initProfessorView() {
		textAreaLeft.setEditable(true);
		toolBar.remove(toggleHelpButton);

		/*
		c = new GridBagConstraints();
		gbl = new GridBagLayout();
		this.setLayout(gbl);

		addComponent(labelAvaiableTags, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		addComponent(buttonPlus, 0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.EAST);
		addComponent(buttonMinus, 0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		addComponent(tablePanel, 1, 0, 1, 3, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		addComponent(toolBar, 2, 0, 1, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		addComponent(splitPane, 3, 0, 1, 3, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		*/
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(scrollPaneAvaiableTags, javax.swing.GroupLayout.DEFAULT_SIZE, paneWidth, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup())
						.addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, paneWidth, Short.MAX_VALUE)
						.addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, paneWidth, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
								.addComponent(labelAvaiableTags)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(buttonMinus)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(buttonPlus))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, topPaneHeight, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(labelAvaiableTags)
								.addComponent(buttonPlus)
								.addComponent(buttonMinus))
								.addComponent(scrollPaneAvaiableTags, javax.swing.GroupLayout.DEFAULT_SIZE, bottomPaneHeight, Short.MAX_VALUE))
		);
	}
	/*private void addComponent(Component comp,
			int row, int column,
			int rowspan, int colspan,
			int fill, int anchor) {
		c.gridy = row;
		c.gridx = column;
		c.gridwidth = colspan;
		c.gridheight = rowspan;
		c.fill = fill; // GridBagConstraints.NONE .HORIZONTAL .VERTICAL .BOTH
		c.anchor = anchor;
		if (fill == GridBagConstraints.BOTH) {
			c.weightx = 1.0f;
			c.weighty = 1.0f;
		} else {
			c.weightx = 0.0f;
			c.weighty = 0.0f;
		}
		gbl.setConstraints(comp, c);
		this.add(comp);
	}*/

	public String getInitialText() {
		return "";//textAreaInitialText.getText();
	}

	public String getAvaiableTags() {
		return "<some><tags>";
	}

	public String getSampleSolution() {
		return "";//textAreaSampleSolution.getText();
	}
}
