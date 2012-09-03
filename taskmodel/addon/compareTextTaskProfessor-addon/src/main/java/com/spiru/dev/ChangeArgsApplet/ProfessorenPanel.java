package com.spiru.dev.ChangeArgsApplet;

/**
 * TODO: raise (and handle) Exception if entered tag contains illegal characters
 *
 * @author rrae
 */
@SuppressWarnings("serial")
public class ProfessorenPanel extends javax.swing.JPanel {

	private javax.swing.JLabel labelInitialText;
	private javax.swing.JLabel labelAvaiableTags;
	private javax.swing.JScrollPane scrollPaneAvaiableTags;
	private javax.swing.JScrollPane scrollPaneInitialText;
	private javax.swing.JTextArea textAreaInitialText;
	private javax.swing.JButton buttonPlus;
	private javax.swing.JButton buttonMinus;
	private javax.swing.table.DefaultTableModel tableModel;
	private javax.swing.JTable tablePanel;
	private int topPaneHeight;
	private int bottomPaneHeight;
	private int paneWidth;

	public ProfessorenPanel(String text, String xmldef, int Width, int Height) {
		labelInitialText = new javax.swing.JLabel("Initial Text:");
		labelAvaiableTags = new javax.swing.JLabel("Avaiable Tags:");
		scrollPaneInitialText = new javax.swing.JScrollPane();
		textAreaInitialText = new javax.swing.JTextArea(text);
		scrollPaneAvaiableTags = new javax.swing.JScrollPane();
		tablePanel = new javax.swing.JTable();
		buttonPlus = new javax.swing.JButton();
		buttonMinus = new javax.swing.JButton();
		topPaneHeight = Height / 2 - 25;
		bottomPaneHeight = Height / 2 - 25;
		paneWidth = Width;
		initTable();
		initButtons();
		initComponents();
	}

	private void initButtons() {
		buttonPlus.setText("-");
		buttonPlus.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int s[] = tablePanel.getSelectedRows();
				for (int i = 0; i < s.length; i++) {
					if (tableModel.getRowCount() > 1) // would throw exception if not checked
						tableModel.removeRow(s[i]);
				}
			}
		});
		buttonMinus.setText("+");
		buttonMinus.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tableModel.addRow(new Object [] {null, null});
			}
		});
	}

	private void initTable() {
		//tableAvaiableTags.setAutoCreateRowSorter(true);
		tableModel = new javax.swing.table.DefaultTableModel(
				new Object [][] {
						{"example", "Example Tag with Description, please replace this line."},
						{null, null}
				},
				new String [] { "Tag Name", "Description" }
		);
		tablePanel.setModel(tableModel);
	}

	private void initComponents() {
		textAreaInitialText.setColumns(20);
		textAreaInitialText.setRows(5);
		textAreaInitialText.setLineWrap(true);
		scrollPaneInitialText.setViewportView(textAreaInitialText);
		scrollPaneAvaiableTags.setViewportView(tablePanel);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(scrollPaneAvaiableTags, javax.swing.GroupLayout.DEFAULT_SIZE, paneWidth, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addComponent(labelInitialText)
						.addGap(0, 0, Short.MAX_VALUE))
						.addComponent(scrollPaneInitialText, javax.swing.GroupLayout.Alignment.TRAILING)
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
						.addComponent(labelInitialText)
						.addComponent(scrollPaneInitialText, javax.swing.GroupLayout.DEFAULT_SIZE, topPaneHeight, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(labelAvaiableTags)
								.addComponent(buttonPlus)
								.addComponent(buttonMinus))
								.addComponent(scrollPaneAvaiableTags, javax.swing.GroupLayout.DEFAULT_SIZE, bottomPaneHeight, Short.MAX_VALUE))
				);
	}

	public String getInitialText() {
		return textAreaInitialText.getText();
	}

	public String getAvaiableTags() {
		return "<some><tags>";
	}
}
