package com.spiru.dev.timeTaskProfessor_addon.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class JPanelButtons extends JPanel{

	private MyMouseListener listener;
	
	public JPanelButtons(MyMouseListener listener){
		this.listener = listener;		
		JButton delete = new JButton("Delete Selection");
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				deleteConnection();	
			}
		});
		delete.addMouseListener(listener);
		this.add(delete);
		JButton deleteAll = new JButton("Delete All");
		deleteAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				deleteAllConnections();	
			}
		});
		this.add(deleteAll);
		deleteAll.addMouseListener(listener);
	}
	
	private void deleteConnection(){
		
	}
	
	private void deleteAllConnections(){
		
	}
}
