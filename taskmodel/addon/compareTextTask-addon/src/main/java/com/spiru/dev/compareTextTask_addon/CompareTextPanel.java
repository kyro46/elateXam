package com.spiru.dev.compareTextTask_addon;

/**
 *
 * @author C.Wilhelm
 */
public class CompareTextPanel extends javax.swing.JPanel {

	/**
	 * Creates new form NewJPanel2
	 */
	public CompareTextPanel() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {

		jSplitPane1 = new javax.swing.JSplitPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea2 = new javax.swing.JTextArea();
		jButton1 = new javax.swing.JButton();
		jButton1.setText("Speichern");

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);
		jSplitPane1.setRightComponent(jScrollPane1);

		jTextArea2.setColumns(20);
		jTextArea2.setRows(5);
		jScrollPane2.setViewportView(jTextArea2);
		jSplitPane1.setLeftComponent(jScrollPane2);

		jSplitPane1.setDividerSize(7);
		jSplitPane1.setResizeWeight(.5d); // give both 50%
		//
		int splitpane_height = 300;
		int splitpane_width = 600;

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
					.addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, splitpane_width, Short.MAX_VALUE)
					.addGroup(layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addComponent(jButton1)))
				.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, splitpane_height, Short.MAX_VALUE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jButton1)
				.addContainerGap())
		);
	}
	private javax.swing.JButton jButton1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JSplitPane jSplitPane1;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextArea jTextArea2;
}
